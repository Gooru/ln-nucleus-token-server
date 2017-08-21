package org.gooru.nucleus.token.responses.auth;

import org.gooru.nucleus.token.constants.MessageConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;

class AuthMessageBusJsonResponseContextHolder implements AuthResponseContextHolder {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthMessageBusJsonResponseContextHolder.class); 
    private final Message<Object> message;
    private boolean isAuthorized = false;
    
    public AuthMessageBusJsonResponseContextHolder(Message<Object> message) {
        this.message = message;
        if (message != null) {
            if (!(message.body() instanceof JsonObject)) {
                LOGGER.error("Message body is NOT JsonObject");
                throw new IllegalArgumentException("Message body should be initialized with JsonObject");
            }
            
            String result = message.headers().get(MessageConstants.MSG_OP_STATUS);
            if (result != null && result.equalsIgnoreCase(MessageConstants.MSG_OP_STATUS_SUCCESS)) {
                isAuthorized = true;
            }
        }
    }

    @Override
    public boolean isAuthorized() {
        return isAuthorized;
    }

    @Override
    public JsonObject getSession() {
        if (!isAuthorized) {
            return null;
        }
        return (JsonObject) message.body();
    }

    @Override
    public boolean isAnonymous() {
        JsonObject jsonObject = (JsonObject) message.body();
        String userId = jsonObject.getString(MessageConstants.MSG_USER_ID);
        return !(userId != null && !userId.isEmpty() && !userId.equalsIgnoreCase(MessageConstants.MSG_USER_ANONYMOUS));
    }

    @Override
    public String getUser() {
        JsonObject jsonObject = (JsonObject) message.body();
        return jsonObject.getString(MessageConstants.MSG_USER_ID);
    }

}
