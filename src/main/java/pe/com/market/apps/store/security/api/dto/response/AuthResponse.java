package pe.com.market.apps.store.security.api.dto.response;

import lombok.Builder;

@Builder
public record AuthResponse(
        String access_token
) {
}
