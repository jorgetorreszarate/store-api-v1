package pe.com.market.apps.store.business.api.dto.response;

import lombok.Builder;

@Builder
public record UserTypeResponse(
        int id,
        String detail
) {
}
