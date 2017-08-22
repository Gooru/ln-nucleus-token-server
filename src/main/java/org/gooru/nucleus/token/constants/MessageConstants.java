package org.gooru.nucleus.token.constants;

public final class MessageConstants {

    public static final String MSG_HEADER_OP = "mb.operation";
    public static final String MSG_API_VERSION = "api.version";
    public static final String MSG_HEADER_TOKEN = "access.token";
    public static final String MSG_OP_ACCESS_TOKEN_DETAILS = "access.token.details";
    public static final String MSG_OP_STATUS = "mb.operation.status";
    public static final String MSG_OP_STATUS_SUCCESS = "success";
    public static final String MSG_OP_STATUS_ERROR = "error";
    public static final String MSG_USER_ANONYMOUS = "anonymous";
    public static final String MSG_USER_ID = "user_id";

    public static final String CONFIG_SESSION_TIMEOUT_KEY = "sessionTimeoutInSeconds";
    public static final String CONFIG_REDIS_CONFIGURATION_KEY = "redisConfig";

    private MessageConstants() {
        throw new AssertionError("Private constructor");
    }
}
