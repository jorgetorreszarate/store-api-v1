package pe.com.market.apps.store.business.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.business.api.handler.PersonalHanlder;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class PersonalRouter {
    @Bean
    RouterFunction<ServerResponse> personalRoute(PersonalHanlder personalHandler) {
        return RouterFunctions
                .route(GET("/api/v1/personal").and(accept(APPLICATION_JSON)), personalHandler::findAll)
                .andRoute(POST("/api/v1/personal").and(accept(APPLICATION_JSON)), personalHandler::create)
                /*.andRoute(PUT("/api/v1/personal").and(accept(APPLICATION_JSON)), personalHandler::update)
                .andRoute(PATCH("/api/v1/personal").and(accept(APPLICATION_JSON)), personalHandler::updatePassword)
                .andRoute(DELETE("/api/v1/{id}/personal").and(accept(APPLICATION_JSON)), personalHandler::delete)*/;
    }
}
