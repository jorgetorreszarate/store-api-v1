package pe.com.market.apps.store.business.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserPasswordRequest(
        @NotNull(message = "Campo obligatorio")
        String userId,

        @NotNull(message = "Campo obligatorio")
        @Size(min = 3, max = 250, message = "Minimo 3 y Maximo 250")
        String password
) {
}
