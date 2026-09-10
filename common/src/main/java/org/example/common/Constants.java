package org.example.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class Constants {
    public static final String CONSUMER_GROUP = "consumer-service";
    public static final String REPLY_GROUP = "rest-service-replies";

    public static final String CREATE_TOPIC = "create";
    public static final String UPDATE_TOPIC = "update";
    public static final String DELETE_TOPIC = "delete";
    public static final String READ_TOPIC = "read";
    public static final String READ_REPLIES_TOPIC = "read-replies";

    public static final String TRUSTED_PACKAGES = "java.lang,org.example.common.models";
    public static final String TYPE_MAPPINGS = "message:org.example.common.models.Message";
}
