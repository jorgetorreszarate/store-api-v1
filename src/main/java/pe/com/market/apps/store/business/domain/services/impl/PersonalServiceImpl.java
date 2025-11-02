package pe.com.market.apps.store.business.domain.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pe.com.market.apps.store.business.api.dto.request.PersonalRequest;
import pe.com.market.apps.store.business.api.dto.response.PersonalResponse;
import pe.com.market.apps.store.business.data.model.document.PersonalDocument;
import pe.com.market.apps.store.business.data.repository.CompanyRepository;
import pe.com.market.apps.store.business.data.repository.PersonalIdentityRepository;
import pe.com.market.apps.store.business.data.repository.PersonalRepository;
import pe.com.market.apps.store.business.domain.mappers.PersonalMapper;
import pe.com.market.apps.store.business.domain.services.PersonalService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PersonalServiceImpl implements PersonalService {
    private final PersonalRepository personalRepository;
    private final CompanyRepository companyRepository;
    private final PersonalIdentityRepository personalIdentityRepository;
    private final PersonalMapper personalMapper;

    @Override
    public Flux<PersonalResponse> findAll() {
        return personalRepository.findAll()
                .flatMap(this::mapToResponse);
    }

    @Override
    public Flux<PersonalResponse> search(String value) {
        return personalRepository.search(value)
                .flatMap(this::mapToResponse);
    }

    @Override
    public Mono<PersonalResponse> findById(String personalId) {
        return personalRepository.findById(personalId)
                .flatMap(this::mapToResponse);
    }

    @Override
    public Mono<String> create(PersonalRequest personalRequest) {
        return Mono.just(personalRequest)
                .map(personalMapper::toDocument)
                .doOnNext(personal -> {
                    personal.setFlagActive(true);
                    personal.setDateAt(LocalDateTime.now());
                })
                .flatMap(personalRepository::save)
                .flatMap(personal -> Mono.just(personal.getId()));
    }

    @Override
    public Mono<Boolean> delete(String personalId) {
        return personalRepository.findById(personalId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El personal no existe")))
                .flatMap(personalRepository::delete)
                .thenReturn(true);
    }

    private Mono<PersonalResponse> mapToResponse(PersonalDocument personal) {
        return Mono.zip(
                        companyRepository.findById(personal.getCompanyId())
                                .switchIfEmpty(Mono.error(new Exception("### Empresa no encontrada"))),
                        personalIdentityRepository.findById(personal.getDocumentTypeId())
                )
                .map(tuple ->
                        personalMapper.toResponseFull(personal, tuple.getT1(), tuple.getT2()));
    }
}
