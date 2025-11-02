package pe.com.market.apps.store.security.api.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pe.com.market.apps.store.business.api.dto.response.UserSingleResponse;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class JwtProviderUtils {

    @Value("${custom.jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${custom.jwt.expire-duration}")
    private long EXPIRE_DURATION;

    public String generateToken(UserSingleResponse user) {
        //List<String> roles = List.of(user.userType());
        Map<String, Object> claims = Map.of(
                "role", user.userType(),
                "companyId", user.companyId(),
                "company", user.company(),
                "personalId", user.personalId(),
                "name", user.name(),
                "fatherLastName", user.fatherLastName(),
                "motherLastName", user.motherLastName(),
                "fullName", user.fullName(),
                "email", Optional.ofNullable(user.email()).orElse("")
        );

        return Jwts.builder()
                .subject(user.userId())
                //.claim("roles", roles)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (24 * EXPIRE_DURATION * 60 * 1_000)))
                .signWith(getKey(SECRET_KEY))
                .compact();
    }

    public Claims getClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey(SECRET_KEY))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getKey(String secret) {
        byte[] secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
