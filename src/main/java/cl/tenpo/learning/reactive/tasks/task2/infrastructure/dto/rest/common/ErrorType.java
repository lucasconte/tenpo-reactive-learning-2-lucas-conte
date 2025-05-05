package cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorType {
    unknown("Unknown error"),
    external_api_error("Error from external api"),
    invalid_request_body("Request have validation errors: %s"),
    empty_request_body("Request body is empty"),
    get_percentage_external("Error getting percentage from external api"),
    authorized_user_already_exist("Authorized user %s already exist"),
    authorized_user_not_found("Authorized user %s was not found"),
    unauthorized_user("User %s is not authorized")
    ;

    private final String message;
}
