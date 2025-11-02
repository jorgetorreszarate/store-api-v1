package pe.com.market.apps.store.business.api.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.business.api.dto.request.PersonalRequest;
import pe.com.market.apps.store.business.api.dto.response.PersonalResponse;
import pe.com.market.apps.store.business.api.dto.validator.ObjectValidator;
import pe.com.market.apps.store.business.domain.services.PersonalService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@AllArgsConstructor
public class PersonalHanlder {
    private final PersonalService personalService;
    private final ObjectValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return request.queryParam("value")
                .map(personalService::search)
                .orElse(personalService.search("")) // O manejar caso sin parámetro
                .collectList()
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ok().bodyValue(list);
                });
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return ok().body(
                request.bodyToMono(PersonalRequest.class)
                        .doOnNext(validator::validate)
                        .flatMap(personalService::create),
                PersonalResponse.class
        );
    }
}
