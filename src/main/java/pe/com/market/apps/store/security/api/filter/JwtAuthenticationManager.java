package pe.com.market.apps.store.security.api.filter;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import pe.com.market.apps.store.security.api.exceptions.TokenNotFoundException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

	private final JwtProviderUtils jwtProviderUtils;

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.just(authentication)
				.map(auth -> jwtProviderUtils.getClaims(auth.getCredentials().toString()))
				.log()
				.onErrorResume(e -> Mono.error(new TokenNotFoundException("Token Incorrecto.")))
				.map(claims -> new UsernamePasswordAuthenticationToken(
						claims.getSubject(),
						null,
						//((List<?>) claims.get("roles"))
						//		.stream()
						//		.map(Object::toString)
                                Stream.of(claims.get("role"))
                                .map(Object::toString)
								.map(SimpleGrantedAuthority::new)
								.toList()
				));
	}
}
