package pe.com.market.apps.store.business.domain.services;

import pe.com.market.apps.store.business.api.dto.request.PersonalRequest;
import pe.com.market.apps.store.business.api.dto.response.PersonalResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonalService {
    Flux<PersonalResponse> findAll();

    Flux<PersonalResponse> search(String value);

    Mono<PersonalResponse> findById(String personalId);

    Mono<String> create(PersonalRequest personalRequest);

    Mono<Boolean> delete(String personalId);
}
