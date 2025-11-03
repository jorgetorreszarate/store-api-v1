package pe.com.market.apps.store.business.api.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.business.api.dto.validator.ObjectValidator;
import pe.com.market.apps.store.business.domain.services.CompanyService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@AllArgsConstructor
public class CompanyHandler {
    private final CompanyService companyService;
    private final ObjectValidator validator;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return companyService.findAll()
                .collectList()
                .flatMap(list -> {
                    if (list.isEmpty()) {
                        return ServerResponse.noContent().build();
                    }
                    return ok().bodyValue(list);
                });
    }
}
