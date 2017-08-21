package org.gooru.nucleus.token.routes.utils;

import org.gooru.nucleus.token.constants.MessageConstants;
import org.gooru.nucleus.token.constants.RouteConstants;

import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.ext.web.RoutingContext;

/**
 * @author szgooru Created On: 18-Aug-2017
 */
public final class DeliveryOptionsBuilder {

    private DeliveryOptionsBuilder() {
        throw new AssertionError();
    }

    public static DeliveryOptions buildWithApiVersion(RoutingContext context) {
        final String apiVersion = context.request().getParam(RouteConstants.API_VERSION);
        VersionValidatorUtility.validateVersion(apiVersion);
        return new DeliveryOptions().addHeader(MessageConstants.MSG_API_VERSION, apiVersion);
    }
}
