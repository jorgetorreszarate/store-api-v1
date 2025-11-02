package pe.com.market.apps.store.business.domain.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.market.apps.store.business.api.dto.response.CompanyResponse;
import pe.com.market.apps.store.business.data.repository.CompanyRepository;
import pe.com.market.apps.store.business.domain.mappers.CompanyMapper;
import pe.com.market.apps.store.business.domain.services.CompanyService;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Flux<CompanyResponse> findAll() {
        return companyRepository.findAll()
                .map(companyMapper::toResponse);
    }
}
