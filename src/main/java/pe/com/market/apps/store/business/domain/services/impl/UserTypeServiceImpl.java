package pe.com.market.apps.store.business.domain.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.market.apps.store.business.api.dto.response.UserTypeResponse;
import pe.com.market.apps.store.business.data.repository.UserTypeRepository;
import pe.com.market.apps.store.business.domain.mappers.UserTypeMapper;
import pe.com.market.apps.store.business.domain.services.UserTypeService;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@Service
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;

    @Override
    public Flux<UserTypeResponse> findAll() {
        return userTypeRepository.findAll()
                .map(userTypeMapper::toResponse);
    }
}
