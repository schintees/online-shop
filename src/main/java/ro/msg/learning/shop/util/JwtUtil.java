package ro.msg.learning.shop.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

public class JwtUtil {
    private static final long EXPIRATION_TIME_MS = 3600000; // ONE HOUR

    private JwtUtil() {

    }

    public static String generateToken(Authentication auth, RSAPrivateKey privateKey) {
        String userName = ((User) auth.getPrincipal()).getUsername();
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public static JwtDecoder getJwtDecoder(RSAPublicKey rsaPublicKey) {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build();
    }

}
