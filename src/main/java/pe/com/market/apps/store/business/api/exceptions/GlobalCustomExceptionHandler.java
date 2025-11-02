package pe.com.market.apps.store.business.api.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@Order(Integer.MIN_VALUE)
public class GlobalCustomExceptionHandler extends AbstractErrorWebExceptionHandler {

	public GlobalCustomExceptionHandler(final ErrorAttributes errorAttributes,
																			final WebProperties.Resources resources,
																			final ApplicationContext applicationContext,
																			final ServerCodecConfigurer configurer) {
		super(errorAttributes, resources, applicationContext);
		setMessageReaders(configurer.getReaders());
		setMessageWriters(configurer.getWriters());
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		ErrorAttributeOptions options = ErrorAttributeOptions.of(ErrorAttributeOptions.Include.MESSAGE);
		Map<String, Object> errorPropertiesMap = getErrorAttributes(request, options);
		Throwable throwable = getError(request);
		HttpStatus httpStatus = determineHttpStatus(throwable);
		errorPropertiesMap.put("status", httpStatus.value());
		errorPropertiesMap.remove("error");

		if (throwable instanceof CustomValidationException cve) {
			Map<String, String> errors = new HashMap<>();
			if (cve.getErrors() != null) {
				cve.getErrors().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
				errorPropertiesMap.put("message", errors);
			}
		}

		return ServerResponse
				.status(httpStatus)
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters
						.fromPublisher(Mono.just(errorPropertiesMap), Map.class));
	}

	private HttpStatus determineHttpStatus(Throwable throwable) {
		if (throwable instanceof ObjectValidationException) {
			return HttpStatus.BAD_REQUEST;
		} else {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}
}
