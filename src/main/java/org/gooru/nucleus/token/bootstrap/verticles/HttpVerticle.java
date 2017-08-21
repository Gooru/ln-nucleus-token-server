package org.gooru.nucleus.token.bootstrap.verticles;

import org.gooru.nucleus.token.constants.ConfigConstants;
import org.gooru.nucleus.token.routes.RouteConfiguration;
import org.gooru.nucleus.token.routes.RouteConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * @author szgooru Created On: 18-Aug-2017
 */
public class HttpVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpVerticle.class);

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        LOGGER.info("starting http verticle");
        final HttpServer httpServer = vertx.createHttpServer();

        // Register the routes
        final Router router = Router.router(vertx);
        configureRoutes(router);

        // If the port is not present in configuration then we end up
        // throwing as we are casting it to int. This is what we want.
        final int port = config().getInteger(ConfigConstants.HTTP_PORT);
        LOGGER.info("HTTP server starting on port {}", port);
        httpServer.requestHandler(router::accept).listen(port, result -> {
            if (result.succeeded()) {
                LOGGER.info("HTTP server started succssfully");
                startFuture.complete();
            } else {
                LOGGER.error("unable to start HTTP server", result.cause());
                startFuture.fail(result.cause());
            }
        });

    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        // Currently a no op
        stopFuture.complete();
    }

    private void configureRoutes(final Router router) {
        RouteConfiguration rc = new RouteConfiguration();
        for (RouteConfigurator configurator : rc) {
            configurator.configureRoutes(vertx, router, config());
        }
    }
}
