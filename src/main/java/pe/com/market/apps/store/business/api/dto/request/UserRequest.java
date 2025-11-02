package pe.com.market.apps.store.business.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRequest(
        @NotNull(message = "Campo obligatorio")
        @Size(min = 3, max = 100, message = "Minimo 3 y Maximo 250")
        String userId,

        @NotNull(message = "Campo obligatorio")
        String personalId,

        @NotBlank(message = "Campo obligatorio")
        @Size(min = 3, max = 250, message = "Minimo 3 y Maximo 250")
        String password,

        @NotNull(message = "Campo obligatorio")
        int userTypeId,

        boolean flagActive
) {
}
