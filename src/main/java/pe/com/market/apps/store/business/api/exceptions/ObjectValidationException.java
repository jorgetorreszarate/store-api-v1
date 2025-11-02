package pe.com.market.apps.store.business.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ObjectValidationException extends RuntimeException {

	public ObjectValidationException(String message) {
		super(message);
	}
}
