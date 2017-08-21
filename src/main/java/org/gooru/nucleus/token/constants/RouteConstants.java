package org.gooru.nucleus.token.constants;

/**
 * @author szgooru Created On: 19-Jun-2017
 */
public final class RouteConstants {

    public static final String API_VERSION = "version";
    private static final String API_BASE_ROUTE = "/api/nucleus-token-server/";
    private static final char SLASH = '/';
    private static final char COLON = ':';

    private static final String OP_TOKEN = "token";

    // /api/nucleus-token-server/v1/token
    public static final String API_TOKEN_ROUTE = API_BASE_ROUTE + COLON + API_VERSION + SLASH + OP_TOKEN;

    public static final long DEFAULT_TIMEOUT = 30000L;

    private RouteConstants() {
        throw new AssertionError();
    }
}
