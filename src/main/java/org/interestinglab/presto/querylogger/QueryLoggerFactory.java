package org.interestinglab.presto.querylogger;

import com.facebook.presto.spi.eventlistener.EventListener;
import com.facebook.presto.spi.eventlistener.EventListenerFactory;

import java.util.Map;

import static java.util.Objects.requireNonNull;


public class QueryLoggerFactory implements EventListenerFactory {

    @Override
    public String getName() {
        return "query-logger";
    }

    @Override
    public EventListener create(Map<String, String> map) {

        requireNonNull(map, String.format("Configuration for %s is null", getName()));

        return new QueryEventListener(map);
    }
}
