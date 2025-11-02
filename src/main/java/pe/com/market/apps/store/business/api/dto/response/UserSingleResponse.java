package pe.com.market.apps.store.business.api.dto.response;

import lombok.Builder;

@Builder
public record UserSingleResponse(
        String id,
        String companyId,
        String company,
        String userId,
        String personalId,
        String name,
        String fatherLastName,
        String motherLastName,
        String fullName,
        String email,
        int userTypeId,
        String userType,
        String password,
        boolean flagActive
) {
}
