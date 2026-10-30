package com.technologyzer.notificationapi.constants;

public interface ErrorConstants {

    String TEMPLATE_ALREADY_EXISTS_ERROR = "Template already exists with given name";

    String TEMPLATE_NOT_EXISTS_WITH_ID_ERROR = "Template doesn't exists with given id";
    String INVALID_TENANT_ID_FORMAT = "Invalid tenant id format";
    String TEMPLATE_ID_IS_REQUIRED = "Template Id Is Required";
    String PUT_CACHING_ERROR = "Error while Caching Data";
    String CACHE_PARSING_ERROR = "Error while Parsing Cache Data";
    String TEMPLATE_VARIABLE_ERROR = "Template Variable Is Required! And Max Size Should Be 20";
    String SEND_NOTIFICATION_TEMPLATE_VARIABLE_ERROR = "Dynamic Template Variable Is Required!";

    String NAME_VARIABLE_ERROR = "Max Template Name Length Should Be 100";
    String MESSAGE_VARIABLE_ERROR = "Max Message Template Length Should Be 10000";
    String NOTIFICATION_TYPE_VARIABLE_ERROR = "Notification TYpe Is Mandatory";
}
