package pe.com.market.apps.store.business.api.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.business.api.dto.request.UserRequest;
import pe.com.market.apps.store.business.api.dto.response.UserResponse;
import pe.com.market.apps.store.business.api.dto.validator.ObjectValidator;
import pe.com.market.apps.store.business.domain.services.UserService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
@AllArgsConstructor
public class UserHandler {
    private final UserService userService;
    private final ObjectValidator validator;

    public Mono<ServerResponse> findByPersonalId(ServerRequest request) {
        return Mono.just(request.pathVariable("personalId"))
                .flatMap(personalId ->
                        ok().body(userService.findByPersonalId(personalId), UserResponse.class))
                .switchIfEmpty(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return ok().body(
                fromPublisher(
                        request.bodyToMono(UserRequest.class)
                                .doOnNext(validator::validate)
                                .flatMap(userService::create),
                        UserResponse.class
                )
        );
    }
}
