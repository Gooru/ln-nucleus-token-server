package org.gooru.nucleus.token.responses.auth;

import io.vertx.core.json.JsonObject;

public interface AuthResponseContextHolder extends AuthResponseHolder {
    JsonObject getSession();
}
