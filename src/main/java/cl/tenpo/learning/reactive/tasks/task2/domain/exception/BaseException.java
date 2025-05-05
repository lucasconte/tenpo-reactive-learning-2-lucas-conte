package cl.tenpo.learning.reactive.tasks.task2.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
  private final String type;
  private final HttpStatus status;

  public BaseException(final String type, final String message, final HttpStatus status) {
    super(message);
    this.status = status;
    this.type = type;
  }
}
