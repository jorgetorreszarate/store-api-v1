package pe.com.market.apps.store.security.api.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record AuthRequest(
		@NotEmpty(message = "Campo obligatorio")
		String username,
		@NotEmpty(message = "Campo obligatorio")
		String password
) {
}
