package cl.tenpo.learning.reactive.tasks.task2.infrastructure.api.handler.extractor;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.common.ErrorType;
import cl.tenpo.learning.reactive.tasks.task2.utils.ErrorHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class RequestBodyExtractor {
  private final Validator validator;

  public <T> Mono<T> extractAndValidate(final ServerRequest request,
                                        final Class<T> bodyClass) {
    return request.bodyToMono(bodyClass)
            .flatMap(body -> validate(body, bodyClass))
            .switchIfEmpty(Mono.error(() -> ErrorHandler.badRequest(ErrorType.empty_request_body)));
  }

  private <T> Mono<T> validate(final T body, final Class<T> bodyClass) {
    final Errors errors = new BeanPropertyBindingResult(body, bodyClass.getName());
    this.validator.validate(body, errors);
    return Mono.just(errors)
        .filter(errs -> errs.getAllErrors().isEmpty())
        .switchIfEmpty(Mono.error(() -> ErrorHandler.invalidRequestBody(errors)))
        .thenReturn(body);
  }
}
