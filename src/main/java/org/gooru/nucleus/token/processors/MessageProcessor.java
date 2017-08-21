package org.gooru.nucleus.token.processors;

import io.vertx.core.Future;

/**
 * Created by ashish on 4/1/16.
 */
public interface MessageProcessor {
    void process(Future<Object> future);
}
