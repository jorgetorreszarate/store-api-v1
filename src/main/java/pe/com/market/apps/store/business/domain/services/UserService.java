package pe.com.market.apps.store.business.domain.services;

import org.springframework.data.mongodb.repository.Query;
import pe.com.market.apps.store.business.api.dto.request.UserPasswordRequest;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.api.dto.response.UserSingleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<UserResponse> findByPersonalId(String personalId);

    //Mono<UserSingleResponse> single(String userId);

    Mono<UserResponse> create(UserRequest userDocument);

    Mono<UserResponse> update(UserRequest userDocument);

    Mono<Boolean> updatePassword(UserPasswordRequest userPasswordRequest);

    Mono<Boolean> delete(String userId);
}
