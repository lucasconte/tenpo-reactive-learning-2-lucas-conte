package cl.tenpo.learning.reactive.tasks.task2.adapter;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.AuthorizedUser;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.rest.inbound.request.AuthorizedUserRequest;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.entity.AuthorizedUserEntity;

public final class AuthorizedUserAdapter {
    private AuthorizedUserAdapter() {
    }

    public static AuthorizedUser toModel(final AuthorizedUserRequest request) {
        return AuthorizedUser.builder()
                .email(request.email())
                .build();
    }

    public static AuthorizedUser toModel(final AuthorizedUserEntity entity) {
        return AuthorizedUser.builder()
                .id(entity.id())
                .email(entity.email())
                .createdAt(entity.createdAt())
                .build();
    }

    public static AuthorizedUserEntity toEntity(final AuthorizedUser model) {
        return AuthorizedUserEntity.builder()
                .id(model.id())
                .email(model.email())
                .createdAt(model.createdAt())
                .build();
    }
}
