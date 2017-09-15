package org.interestinglab.presto.querylogger;

import io.airlift.log.Logger;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Map;

public class KafkaProducerFactory {

    private static final Logger log = Logger.get(KafkaProducerFactory.class);

    private static KafkaProducer producer = null;
    private static Map<String, String> producerConfig = null;

    public static synchronized KafkaProducer getKafkaProducer() {

        // TODO : what if producer cannot be used and is not null, for example: closed ?
        if (producer == null) {

            // setContextClassLoader fixed a bug :
            // https://issues.apache.org/jira/browse/KAFKA-3218
            Thread.currentThread().setContextClassLoader(null);

            producer = new KafkaProducer(producerConfig);

            log.info("Created New Kafka Producer: " + producer);
        }

        return producer;
    }

    public static void setKafkaProducerConfig(Map<String, String> config) {
        producerConfig = config;

        for (Map.Entry<String, String> entry: producerConfig.entrySet()) {

            log.info(String.format("Kafka Producer Config: %s=%s", entry.getKey(), entry.getValue()));
        }
    }
}
