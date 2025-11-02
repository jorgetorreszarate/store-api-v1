package pe.com.market.apps.store.business.domain.services;

import pe.com.market.apps.store.business.api.dto.response.CompanyResponse;
import reactor.core.publisher.Flux;

public interface CompanyService {
    Flux<CompanyResponse> findAll();
}
