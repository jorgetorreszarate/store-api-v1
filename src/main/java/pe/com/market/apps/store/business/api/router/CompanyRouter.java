package pe.com.market.apps.store.business.api.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import pe.com.market.apps.store.business.api.handler.CompanyHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class CompanyRouter {
    @Bean
    RouterFunction<ServerResponse> companyRoute(CompanyHandler companyHandler) {
        return RouterFunctions
                .route(GET("/api/v1/companies").and(accept(APPLICATION_JSON)), companyHandler::findAll);
    }
}
