package cl.tenpo.learning.reactive.tasks.task2.utils;

import cl.tenpo.learning.reactive.tasks.task2.domain.exception.BaseException;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

public final class ErrorHandler {
    private ErrorHandler() {
    }

    public static BaseException invalidRequestBody(final Errors errors) {
        final String errorMessage = errors.getAllErrors().toString();
        return new BaseException(ErrorType.invalid_request_body.name(),
                String.format(ErrorType.invalid_request_body.getMessage(), errorMessage),
                HttpStatus.BAD_REQUEST);
    }

    public static BaseException authorizedUserAlreadyExist(final String email) {
        return new BaseException(ErrorType.authorized_user_already_exist.name(),
                String.format(ErrorType.authorized_user_already_exist.getMessage(), email),
                HttpStatus.BAD_REQUEST);
    }

    public static BaseException authorizedUserNotFound(final String email) {
        return new BaseException(ErrorType.authorized_user_not_found.name(),
                String.format(ErrorType.authorized_user_not_found.getMessage(), email),
                HttpStatus.NOT_FOUND);
    }

    public static BaseException badRequest(final ErrorType type) {
        return new BaseException(type.name(), type.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public static BaseException serverException(final ErrorType type) {
        return new BaseException(type.name(), type.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static BaseException unauthorizedUser(final String email) {
        return new BaseException(ErrorType.unauthorized_user.name(),
                String.format(ErrorType.unauthorized_user.getMessage(), email),
                HttpStatus.UNAUTHORIZED);
    }
}
