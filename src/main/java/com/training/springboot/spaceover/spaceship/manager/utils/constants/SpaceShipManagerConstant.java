package com.training.springboot.spaceover.spaceship.manager.utils.constants;

public class SpaceShipManagerConstant {

    /**
     * Operations
     */
    public static final String GET_SPACESHIPS_SERVICE_OPERATION = "getSpaceships";
    public static final String GET_SPACESHIP_SERVICE_OPERATION = "getSpaceship";
    public static final String CREATE_SPACESHIP_SERVICE_OPERATION = "createSpaceship";
    public static final String PATCH_SPACESHIP_SERVICE_OPERATION = "patchSpaceship";
    public static final String PUT_SPACESHIP_SERVICE_OPERATION = "putSpaceship";
    public static final String DELETE_SPACESHIP_SERVICE_OPERATION = "deleteSpaceship";
    public static final String UNDEFINED_SERVICE_OPERATION = "Undefined";

    /**
     * MDC Keys
     */
    public static final String SERVICE_OPERATION = "operation";
    public static final String TRACE_ID = "trace-id";

    /**
     * Header Names
     */
    public static final String TRACE_ID_HEADER = "X-Trace-Id";
    public static final String SERVICE_OPERATION_HEADER = "X-Service-Operation";
    public static final String LINK_HEADER = "Link";
    public static final String PAGE_NUMBER_HEADER = "X-Page-Number";
    public static final String PAGE_SIZE_HEADER = "X-Page-Size";
    public static final String TOTAL_ELEMENTS_HEADER = "X-Total-Elements";
    public static final String TOTAL_PAGES_HEADER = "X-Total-Pages";

    /**
     * URIs
     */
    public static final String SPACESHIPS_URI = "/spaceships";
    public static final String ID_URI = "/{id}";

    /**
     * Messages
     */
    public static final String GET_SPACESHIPS_MSG = "Getting spaceships";
    public static final String GET_SPACESHIPS_COUNT_MSG = "Got {} spaceships out of {}";
    public static final String GET_SPACESHIP_MSG = "Getting spaceship";
    public static final String GET_SPACESHIP_RESULT_MSG = "Got spaceship id::{}";
    public static final String CREATE_SPACESHIP_MSG = "Creating spaceship";
    public static final String CREATE_SPACESHIP_RESULT_MSG = "Created spaceship id::{}";
    public static final String UPDATE_SPACESHIP_MSG = "Updating spaceship id::{}";
    public static final String UPDATE_SPACESHIP_RESULT_MSG = "Updated spaceship id::{}";
    public static final String DELETE_SPACESHIP_MSG = "Deleting spaceship id::{}";
    public static final String DELETE_SPACESHIP_RESULT_MSG = "Deleted spaceship id::{}";
    public static final String RESTOCK_SPACESHIP_MSG = "Restocking spaceship id::{}";
    public static final String RESTOCK_SPACESHIP_RESULT_MSG = "Restocked spaceship id::{} by amount {}";
    public static final String DISPATCH_SPACESHIP_MSG = "Dispatching spaceship id::{}";
    public static final String DISPATCH_SPACESHIP_RESULT_MSG = "Dispatched spaceship id::{} by amount {}";
    public static final String LOGGING_HANDLER_INBOUND_MSG = "Received HTTP {} Request to {} at {}";
    public static final String LOGGING_HANDLER_OUTBOUND_MSG = "Responded with Status {} at {}";
    public static final String LOGGING_HANDLER_PROCESS_TIME_MSG = "Total processing time {} ms";
    public static final String INVALID_MARKET_FIELD_MSG = "market field should match ISO 3166-1 alpha-2 specification";
    public static final String INVALID_EMPTY_OR_BLANK_STRING_MSG = "cannot be empty or blank";
    public static final String ENTITY_NOT_FOUND_MSG = "Entity %s id::{%s} not found.";

    /**
     * Fields
     */
    public static final String NAME_FIELD = "name";
    public static final String STATUS_FIELD = "status";
    public static final String TYPE_FIELD = "type";

    /**
     * OpenAPI
     */
    public static final String GET_SPACESHIPS_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the retrieval of multiples persisted space ship resource entries.";
    public static final String GET_SPACESHIP_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the persistence of a single space ship resource entry.";
    public static final String CREATE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the persistence of a single space ship resource entry.";
    public static final String PATCH_SPACESHIP_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the partial update of a single space ship persisted resource entry.";
    public static final String PUT_SPACESHIP_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the full update of a single space ship persisted resource entry.";
    public static final String DELETE_SPACESHIP_SERVICE_OPERATION_DESCRIPTION = "Synchronous operation that allows the removal of a single space ship persisted resource entry.";

    /**
     * Miscellaneous
     */
    public static final String FRONT_SLASH_DELIMITER = "/";
    public static final String COLON_WHITE_SPACE_DELIMITER = ", ";
    public static final String WHITE_SPACE_DELIMITER = " ";
    public static final String SEMI_COLON_DELIMITER = ";";
    public static final String SPACESHIP_API_DESCRIPTION = "A public Restful Api that allows to manage the various spaceship resources.";
    public static final String ISO_3166_1_ALPHA_2_REGEX = "^[A-Z]{2}$";
    public static final String EMPTY_OR_BLANK_STRING_REGEX = "^(?!\\s*$).+";
    public static final String APPLICATION_JSON_PATCH = "application/json-patch+json";
    public static final String SPACESHIPS = "spaceships";
    public static final String SPACESHIP = "spaceship";

}
