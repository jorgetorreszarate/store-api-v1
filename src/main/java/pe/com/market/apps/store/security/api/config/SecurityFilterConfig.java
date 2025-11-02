package pe.com.market.apps.store.security.api.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import pe.com.market.apps.store.security.api.filter.JwtFilter;
import pe.com.market.apps.store.security.api.filter.SecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@AllArgsConstructor
public class SecurityFilterConfig {

	private final SecurityContextRepository securityContextRepository;

	@Bean
	public SecurityWebFilterChain filterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(
						authorizeExchangeSpec ->
								authorizeExchangeSpec
										.pathMatchers("/auth/**").permitAll()
										.anyExchange().authenticated()
				)
				.addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
				.securityContextRepository(securityContextRepository)
				.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
				.formLogin(ServerHttpSecurity.FormLoginSpec::disable)
				.logout(ServerHttpSecurity.LogoutSpec::disable)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
