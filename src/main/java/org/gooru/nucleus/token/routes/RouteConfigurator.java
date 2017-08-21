package org.gooru.nucleus.token.routes;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

/**
 * Contract for the entity which can configure the routes on HTTP server
 * Created by ashish on 26/4/16.
 */
public interface RouteConfigurator {
    void configureRoutes(Vertx vertx, Router router, JsonObject config);
}
