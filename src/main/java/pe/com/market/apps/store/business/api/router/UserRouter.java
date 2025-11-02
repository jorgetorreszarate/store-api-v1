package pe.com.market.apps.store.business.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.business.api.handler.UserHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class UserRouter {

    @Bean
    RouterFunction<ServerResponse> userRoute(UserHandler userHandler) {
        return RouterFunctions
                .route(GET("/api/v1/users/by-personal/{personalId}").and(accept(APPLICATION_JSON)), userHandler::findByPersonalId)
                .andRoute(POST("/api/v1/users").and(accept(APPLICATION_JSON)), userHandler::create);
    }
}
