package cl.tenpo.learning.reactive.tasks.task2.infrastructure.repository.dao;

import cl.tenpo.learning.reactive.tasks.task2.infrastructure.entity.ApiCallHistoryEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ApiCallHistoryDao extends ReactiveMongoRepository<ApiCallHistoryEntity, Long> {}
