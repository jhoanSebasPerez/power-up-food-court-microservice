package com.pragma.powerup.foodcourtmicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SWAGGER_TITLE_MESSAGE = "Food court pragma power up";

    public static final String SWAGGER_DESCRIPTION_MESSAGE = "Food court microservice";

    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";

    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";

    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";

    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";

    public static final String RESPONSE_MESSAGE_KEY = "message";

    public static final String ERROR_MESSAGE_KEY = "error";

    /**
     * Restaurant constants
     */
    public static final String RESTAURANT_CREATED_MESSAGE = "restaurant created successful";

    /**
     * Dish constants
     */
    public static final String DISH_CREATED_MESSAGE = "dish created successful";

    public static final String DISH_UPDATED_MESSAGE = "the dish has been updated";

    /**
     * Order constants
     */
    public static final String ORDER_CREATED_MESSAGE = "order created successful";

    public static final String CLIENT_HAS_ORDERS_IN_PROCESS =
            "The customer has pending orders, therefore cannot place a new order.";

    public static final String ORDER_CHANGE_IN_PROCESS = "The orders have changed status to IN PREPARATION";

}
