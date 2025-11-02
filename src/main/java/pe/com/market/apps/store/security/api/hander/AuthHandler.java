package pe.com.market.apps.store.security.api.hander;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.security.api.dto.request.AuthRequest;
import pe.com.market.apps.store.security.api.dto.response.AuthResponse;
import pe.com.market.apps.store.security.service.AuthService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

	private final AuthService authService;

	public Mono<ServerResponse> login(ServerRequest request) {
		return request.bodyToMono(AuthRequest.class)
				.flatMap(authRequest ->
						ServerResponse.ok().body(authService.login(authRequest), AuthResponse.class));
	}
}
