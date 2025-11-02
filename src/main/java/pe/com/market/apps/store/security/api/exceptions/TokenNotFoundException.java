package pe.com.market.apps.store.security.api.exceptions;

public class TokenNotFoundException extends RuntimeException {

	public TokenNotFoundException(String message) {
		super(message);
	}
}
