package pe.com.market.apps.store.security.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.security.api.hander.AuthHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AuthRouter {

	@Bean
	public RouterFunction<ServerResponse> authRoute(AuthHandler authHandler) {
		return RouterFunctions
				.route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login);
	}
}
