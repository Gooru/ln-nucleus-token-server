/**
 *
 */
package org.gooru.nucleus.token.routes.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gooru.nucleus.token.constants.HttpConstants;
import org.gooru.nucleus.token.exceptions.HttpResponseWrapperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author szgooru Created On: 02-Jan-2017
 */
public final class VersionValidatorUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionValidatorUtility.class);
    private static final String API_VERSION_DEPRECATED = "API version is deprecated";
    private static final String API_VERSION_NOT_SUPPORTED = "API version is not supported";
    private static final List<String> supportedVersions = Arrays.asList("v1", "v2");
    private static final List<String> deprecatedVersions = new ArrayList<>();

    private VersionValidatorUtility() {
        throw new AssertionError();
    }

    public static void validateVersion(String version) {
        LOGGER.info("Version in API call is : {}", version);
        if (supportedVersions.contains(version)) {
        } else if (deprecatedVersions.contains(version)) {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.GONE, API_VERSION_DEPRECATED);
        } else {
            throw new HttpResponseWrapperException(HttpConstants.HttpStatus.NOT_IMPLEMENTED, API_VERSION_NOT_SUPPORTED);
        }
    }
}
