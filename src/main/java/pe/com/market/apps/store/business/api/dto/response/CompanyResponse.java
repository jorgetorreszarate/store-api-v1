package pe.com.market.apps.store.business.api.dto.response;

import lombok.Builder;

@Builder
public record CompanyResponse(
        String companyId,
        int documentTypeId,
        String documentNumber,
        String companyName,
        String tradeName,
        boolean flagActive
) {
}
