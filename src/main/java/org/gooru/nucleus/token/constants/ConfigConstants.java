package org.gooru.nucleus.token.constants;

public final class ConfigConstants {
    public static final String HTTP_PORT = "http.port";
    public static final String METRICS_PERIODICITY = "metrics.periodicity.seconds";
    public static final String MBUS_TIMEOUT = "message.bus.send.timeout.seconds";
    public static final String MAX_REQ_BODY_SIZE = "request.body.size.max.mb";
    
    private ConfigConstants() {
        throw new AssertionError();
    }
}
