package pe.com.market.apps.store.business.domain.services;

import pe.com.market.apps.store.business.api.dto.response.UserTypeResponse;
import reactor.core.publisher.Flux;

public interface UserTypeService {
    Flux<UserTypeResponse> findAll();
}
