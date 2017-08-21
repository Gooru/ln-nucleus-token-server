package org.gooru.nucleus.token.routes;

import org.gooru.nucleus.token.constants.ConfigConstants;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Global routes which are important for system to function.
 * Currently it registers body hadler which can provide us the POST or PUT body data
 * Created by ashish on 26/4/16.
 */
final class RouteGlobalConfigurator implements RouteConfigurator {

    @Override
    public void configureRoutes(Vertx vertx, Router router, JsonObject config) {

        final long maxSizeInMb = config.getLong(ConfigConstants.MAX_REQ_BODY_SIZE, 5L);

        BodyHandler bodyHandler = BodyHandler.create().setBodyLimit(maxSizeInMb * 1024 * 1024);

        router.route().handler(bodyHandler);

    }

}
