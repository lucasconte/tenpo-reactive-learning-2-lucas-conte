package cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository;

import cl.tenpo.learning.reactive.tasks.task2.adapter.ApiCallHistoryAdapter;
import cl.tenpo.learning.reactive.tasks.task2.domain.model.ApiCallHistory;
import cl.tenpo.learning.reactive.tasks.task2.domain.port.ApiCallHistoryPort;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.dao.ApiCallHistoryDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class ApiCallHistoryMongoRepository implements ApiCallHistoryPort {
  private final ApiCallHistoryDao dao;

  @Override
  public Mono<ApiCallHistory> create(final ApiCallHistory apiCallHistory) {
    return Mono.just(apiCallHistory)
            .map(ApiCallHistoryAdapter::toEntity)
            .flatMap(dao::save)
            .map(ApiCallHistoryAdapter::toModel);
  }

  @Override
  public Flux<ApiCallHistory> findAll() {
    return dao.findAll()
            .map(ApiCallHistoryAdapter::toModel);
  }
}
