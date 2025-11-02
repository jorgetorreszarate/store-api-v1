package pe.com.market.apps.store.business.domain.services;

import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponse> create(UserRequest userDocument);
    Flux<UserResponse> findByPersonalId(String personalId);
}
