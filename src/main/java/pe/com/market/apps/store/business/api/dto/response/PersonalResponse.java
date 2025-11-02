package pe.com.market.apps.store.business.api.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonalResponse(
        String personalId,
        String companyId,
        String companyName,
        int documentTypeId,
        String documentType,
        String documentNumber,
        String fatherLastName,
        String motherLastName,
        String name,
        LocalDate birthDate,
        String genre,
        String cellphone,
        String email,
        String address,
        Boolean flagActive
) {
}
