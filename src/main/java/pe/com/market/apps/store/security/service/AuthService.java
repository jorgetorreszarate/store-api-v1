package pe.com.market.apps.store.security.service;

import pe.com.market.apps.store.security.api.dto.request.AuthRequest;
import pe.com.market.apps.store.security.api.dto.response.AuthResponse;
import reactor.core.publisher.Mono;

public interface AuthService {

	Mono<AuthResponse> login(AuthRequest request);
}
