package org.gooru.nucleus.token.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.gooru.nucleus.token.bootstrap.deployment.VerticleRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

/**
 * @author szgooru Created On: 18-Aug-2017
 */
public class TokenServerVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServerVerticle.class);
    
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Future<Void> deployFuture = Future.future();
        deployVerticles(deployFuture);
        
        List<Future> futures = new ArrayList<>();
        futures.add(deployFuture);
        
        CompositeFuture.all(futures).setHandler(result -> {
            if (result.succeeded()) {
                LOGGER.info("All verticles are deployed and application started successfully");
                startFuture.complete();
            } else {
                LOGGER.error("Failure in deployment or application startup", result.cause());
                startFuture.fail(result.cause());

                // Not much options now, no point in continuing
                Runtime.getRuntime().halt(1);
            }
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        // NOOP
    }
    
    private void deployVerticles(Future<Void> future) {
        VerticleRegistry registry = new VerticleRegistry();
        List<Future> futures = new ArrayList<>();
        for (String verticleName : registry) {
            Future<String> deployFuture = Future.future();
            futures.add(deployFuture);

            JsonObject config = config().getJsonObject(verticleName);
            if (config.isEmpty()) {
                vertx.deployVerticle(verticleName, deployFuture.completer());
            } else {
                DeploymentOptions options = new DeploymentOptions(config);
                vertx.deployVerticle(verticleName, options, deployFuture.completer());
            }
        }
        CompositeFuture.all(futures).setHandler(result -> {
            if (result.succeeded()) {
                LOGGER.info("All verticles deployd successfully");
                future.complete();
            } else {
                LOGGER.warn("Deployment failed", result.cause());
                future.fail(result.cause());
            }
        });
    }

}
