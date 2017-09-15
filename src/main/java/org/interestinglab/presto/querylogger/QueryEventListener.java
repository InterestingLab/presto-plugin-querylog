package org.interestinglab.presto.querylogger;

import com.facebook.presto.spi.eventlistener.EventListener;
import com.facebook.presto.spi.eventlistener.QueryCompletedEvent;
import com.facebook.presto.spi.eventlistener.QueryCreatedEvent;
import com.facebook.presto.spi.eventlistener.QueryFailureInfo;
import com.facebook.presto.spi.eventlistener.SplitCompletedEvent;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.airlift.log.Logger;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QueryEventListener implements EventListener {

    private static final Logger log = Logger.get(QueryEventListener.class);

    private static final String KAFKA_CONFIG_PREFIX = "kafka.producer.";

    private Map<String, String> conf;

    /**
     * @param configuration configuration for Query EventListener.
     * */
    public QueryEventListener(Map<String, String> configuration) {

        // Thread.currentThread().setContextClassLoader(null); // TODO

        this.conf = configuration;

        for (Map.Entry<String, String> e : this.conf.entrySet()) {

            log.info("Loading Config: %s = %s", e.getKey(), e.getValue());
        }

        Map<String, String> kafkaProducerConfig = new HashMap<>();

        for (Map.Entry<String, String> e : this.conf.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();

            if (key.startsWith(KAFKA_CONFIG_PREFIX)) {
                String name = key.substring(KAFKA_CONFIG_PREFIX.length());
                kafkaProducerConfig.put(name, value);
            }
        }

        KafkaProducerFactory.setKafkaProducerConfig(kafkaProducerConfig);
    }

    @Override
    public void queryCompleted(QueryCompletedEvent queryCompletedEvent) {

        // TODO : get server address + server port ?

        // stats -> json -> kafka

        QueryResult qr = new QueryResult();

        qr.setQueryId(queryCompletedEvent.getMetadata().getQueryId());
        qr.setState(queryCompletedEvent.getMetadata().getQueryState());
        qr.setQueuedTimeMs(queryCompletedEvent.getStatistics().getQueuedTime().toMillis());
        qr.setSubmissionTimeMs(queryCompletedEvent.getCreateTime().toEpochMilli());
        qr.setCompletionTimeMs(queryCompletedEvent.getEndTime().toEpochMilli());
        qr.setElapsedTimeMs(qr.getCompletionTimeMs() - qr.getSubmissionTimeMs());

        qr.setCatalog(queryCompletedEvent.getContext().getCatalog().orElse(""));
        qr.setSchema(queryCompletedEvent.getContext().getSchema().orElse(""));
        qr.setServerAddress(queryCompletedEvent.getContext().getServerAddress());
        qr.setRemoteClientAddress(queryCompletedEvent.getContext().getRemoteClientAddress().orElse(""));

        qr.setSql(queryCompletedEvent.getMetadata().getQuery());
        qr.setCpuTime(queryCompletedEvent.getStatistics().getCpuTime().toMillis()/1000);
        qr.setPeakMemoryBytes(queryCompletedEvent.getStatistics().getPeakMemoryBytes());
        qr.setTotalBytes(queryCompletedEvent.getStatistics().getTotalBytes());
        qr.setTotalRows(queryCompletedEvent.getStatistics().getTotalRows());
        qr.setCompletedSplits(queryCompletedEvent.getStatistics().getCompletedSplits());

        if (queryCompletedEvent.getFailureInfo().isPresent()) {
            QueryFailureInfo failureInfo = queryCompletedEvent.getFailureInfo().get();

            qr.setErrorCode(failureInfo.getErrorCode().getCode());
            qr.setErrorCodeName(failureInfo.getErrorCode().getName());
            qr.setErrorMessage(failureInfo.getFailureMessage().orElse(""));
            qr.setErrorHost(failureInfo.getFailureHost().orElse(""));
            qr.setErrorJson(failureInfo.getFailuresJson());
            qr.setErrorTask(failureInfo.getFailureTask().orElse(""));
            qr.setErrorType(failureInfo.getFailureType().orElse(""));
        }

        ObjectMapper mapper = new ObjectMapper();

        String queryResultJson = null;
        try {

            queryResultJson = mapper.writeValueAsString(qr);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (queryResultJson == null) {
            return;
        }

        KafkaProducer producer = KafkaProducerFactory.getKafkaProducer();
        try {
            ProducerRecord record = new ProducerRecord(this.conf.get("kafka.topic"), queryResultJson);
            producer.send(record);
            log.info("Query completed. QueryResult: %s", queryResultJson);

        } catch (Exception e) {

            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void queryCreated(QueryCreatedEvent queryCreatedEvent) {
        // do nothing
    }

    @Override
    public void splitCompleted(SplitCompletedEvent splitCompletedEvent) {
        // do nothing
    }
}
