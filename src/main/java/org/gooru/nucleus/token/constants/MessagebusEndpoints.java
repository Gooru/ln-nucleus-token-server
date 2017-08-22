package org.gooru.nucleus.token.constants;

public final class MessagebusEndpoints {
    public static final String MBEP_TOKEN_HANDLER = "org.gooru.nucleus.auth.message.bus.token.handler";

    private MessagebusEndpoints() {
        throw new AssertionError("Private Constructor");
    }
}
