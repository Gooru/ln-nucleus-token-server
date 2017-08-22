package org.gooru.nucleus.token.routes;

import java.util.ResourceBundle;

import org.gooru.nucleus.token.constants.CommonConstants;
import org.gooru.nucleus.token.constants.HttpConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Route to handle the failures
 * Created by ashish on 26/4/16.
 */
public class RouteFailureConfigurator implements RouteConfigurator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RouteFailureConfigurator.class);
    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle(CommonConstants.RESOURCE_BUNDLE);
    private static final String CAUGHT_UNREGISTERED_EXCEPTION_WILL_SEND_HTTP_500 =
        "Caught unregistered exception, will send HTTP.500";

    @Override
    public void configureRoutes(Vertx vertx, Router router, JsonObject config) {

        router.put().failureHandler(this::handleFailures);

        router.post().failureHandler(this::handleFailures);
    }

    private void handleFailures(RoutingContext frc) {
        Throwable currentThrowable = frc.failure();
        if (currentThrowable instanceof io.vertx.core.json.DecodeException) {
            LOGGER.error("caught registered exception", currentThrowable);
            frc.response().setStatusCode(HttpConstants.HttpStatus.BAD_REQUEST.getCode())
                .end(MESSAGES.getString("invalid.json.payload"));
        } else {
            LOGGER.error(CAUGHT_UNREGISTERED_EXCEPTION_WILL_SEND_HTTP_500, currentThrowable);
            frc.response().setStatusCode(HttpConstants.HttpStatus.ERROR.getCode())
                .end(MESSAGES.getString("internal.error"));
        }
    }
}
