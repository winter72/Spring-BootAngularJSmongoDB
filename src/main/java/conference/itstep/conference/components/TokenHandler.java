package conference.itstep.conference.components;

import conference.itstep.conference.models_entitys.Users;
import io.jsonwebtoken.*;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;
import com.google.common.io.BaseEncoding;

@Component
public class TokenHandler {
    private final SecretKey secretKey;
    private long validityInMilliseconds = 3600000;

    public TokenHandler() {
        byte[] decodeKey = BaseEncoding.base64().decode("FgggT546mnbvlJVC");
        secretKey = new SecretKeySpec(decodeKey, 0, decodeKey.length, "HS512") {
        };
    }

    public Optional<String> extractUserId(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional
                    .ofNullable(body.getId())
                    .map(String::new);
        } catch (RuntimeException ex) {
            return Optional.empty();
        }
    }

    public String createToken(Users user) {
      // Claims claims = Jwts.claims().setSubject(user.getUsername());
       Claims claims=Jwts.claims().setId(user.getId());
        claims.put("roles", user.getAuthorities());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
                return true;

        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Expired or invalid JWT token");
        }
        return false;
    }

}
