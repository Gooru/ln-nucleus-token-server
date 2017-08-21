package org.gooru.nucleus.token.responses.auth;

public interface AuthResponseHolder {
    boolean isAuthorized();

    boolean isAnonymous();

    String getUser();

}