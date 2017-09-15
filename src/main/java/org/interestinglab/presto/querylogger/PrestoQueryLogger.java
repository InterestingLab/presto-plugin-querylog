package org.interestinglab.presto.querylogger;

import com.facebook.presto.spi.Plugin;
import com.facebook.presto.spi.eventlistener.EventListenerFactory;
import com.google.common.collect.ImmutableList;


public class PrestoQueryLogger implements Plugin {

    @Override
    public Iterable<EventListenerFactory> getEventListenerFactories() {
        return ImmutableList.of(new QueryLoggerFactory());
    }
}
