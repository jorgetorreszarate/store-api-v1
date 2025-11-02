package pe.com.market.apps.store.business.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonalRequest(
        @NotNull(message = "Campo obligatorio")
        String companyId,
        @NotNull(message = "Campo obligatorio")
        int documentTypeId,
        @NotNull(message = "Campo obligatorio")
        String documentNumber,
        @NotNull(message = "Campo obligatorio")
        String fatherLastName,
        @NotNull(message = "Campo obligatorio")
        String motherLastName,
        @NotNull(message = "Campo obligatorio")
        String name,
        LocalDate birthDate,
        String genre,
        String cellphone,
        String email,
        String address,
        String personalRegisterId,
        Boolean flagActive
) {
}
