package org.gooru.nucleus.token.routes;

import java.nio.charset.StandardCharsets;

import org.gooru.nucleus.token.constants.ConfigConstants;
import org.gooru.nucleus.token.constants.HttpConstants;
import org.gooru.nucleus.token.constants.MessageConstants;
import org.gooru.nucleus.token.constants.MessagebusEndpoints;
import org.gooru.nucleus.token.constants.RouteConstants;
import org.gooru.nucleus.token.responses.auth.AuthResponseContextHolder;
import org.gooru.nucleus.token.responses.auth.AuthResponseContextHolderBuilder;
import org.gooru.nucleus.token.routes.utils.DeliveryOptionsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * @author szgooru Created On: 19-Jun-2017
 */
public class RouteTokenConfigurator implements RouteConfigurator {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteTokenConfigurator.class);

    private EventBus eb = null;
    private long mbusTimeout;

    @Override
    public void configureRoutes(Vertx vertx, Router router, JsonObject config) {
        eb = vertx.eventBus();
        mbusTimeout = config.getLong(ConfigConstants.MBUS_TIMEOUT, RouteConstants.DEFAULT_TIMEOUT);

        router.get(RouteConstants.API_TOKEN_ROUTE).handler(this::verifyAccessToken);
    }
    
    private void verifyAccessToken(RoutingContext routingContext) {
        DeliveryOptions options = DeliveryOptionsBuilder.buildWithApiVersion(routingContext).setSendTimeout(mbusTimeout * 1000)
            .addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_ACCESS_TOKEN_DETAILS);
        HttpServerRequest request = routingContext.request();
        String authorization = request.getHeader(HttpConstants.HEADER_AUTH);
        String token = authorization.substring(HttpConstants.TOKEN.length()).trim();
        options.addHeader(MessageConstants.MSG_HEADER_TOKEN, token);
        eb.send(MessagebusEndpoints.MBEP_TOKEN_HANDLER, null, options, reply -> {
            if (reply.succeeded()) {
                LOGGER.info("received response from token server:{}", reply.result().body().toString());
                AuthResponseContextHolder responseHolder =
                    new AuthResponseContextHolderBuilder(reply.result()).build();
                if (responseHolder.isAuthorized()) {
                    final HttpServerResponse response = routingContext.response();
                    JsonObject responseJson = responseHolder.getSession();
                    if (responseJson != null && !responseJson.isEmpty()) {
                    response.putHeader(HttpConstants.HEADER_CONTENT_LENGTH,
                        Integer.toString(responseJson.toString().getBytes(StandardCharsets.UTF_8).length));
                    response.putHeader(HttpConstants.HEADER_CONTENT_TYPE, HttpConstants.CONTENT_TYPE_APPLICATION_JSON);
                    response.end(responseJson.toString());
                    } else {
                        routingContext.response().setStatusCode(HttpConstants.HttpStatus.UNAUTHORIZED.getCode())
                        .setStatusMessage(HttpConstants.HttpStatus.UNAUTHORIZED.getMessage()).end();
                    }
                    
                } else {
                    routingContext.response().setStatusCode(HttpConstants.HttpStatus.UNAUTHORIZED.getCode())
                        .setStatusMessage(HttpConstants.HttpStatus.UNAUTHORIZED.getMessage()).end();
                }
            } else {
                LOGGER.error("Not able to send message", reply.cause());
                routingContext.response().setStatusCode(HttpConstants.HttpStatus.ERROR.getCode()).end();
            }
        });
    }
}
