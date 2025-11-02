package pe.com.market.apps.store.security.api.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		ServerHttpRequest req = exchange.getRequest();
		String path = req.getPath().value();

		// /auth/login
		if (path.contains("auth")) {
			return chain.filter(exchange);
		}

		// /incidencias
		// Header:
		// * Key: Authorization
		// * Value: Bearer {access_token}
		String auth = req.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (!StringUtils.hasText(auth)) {
			return Mono.error(new Throwable("El Token es obligatorio."));
		}

		if (!auth.startsWith("Bearer ")) {
			return Mono.error(new Throwable("El Token de Autorizacion es invalido."));
		}

		String token = auth.replace("Bearer ", "");
		exchange.getAttributes().put("access_token", token);

		return chain.filter(exchange);
	}
}
