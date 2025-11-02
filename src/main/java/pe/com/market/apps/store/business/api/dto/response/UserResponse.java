package pe.com.market.apps.store.business.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        String userId,
        String personalId,
        String fullName,
        int userTypeId,
        String userType,
        boolean flagActive
) {
}
