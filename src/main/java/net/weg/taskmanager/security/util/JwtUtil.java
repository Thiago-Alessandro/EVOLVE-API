package net.weg.taskmanager.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

//@AllArgsConstructor
public class JwtUtil {
//    private SecretKey key;
//    public JwtUtil() {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        String password = encoder.encode("senha123");
//        this.key = Keys.hmacShaKeyFor(password.getBytes());
//    }
    public String createToken(UserDetails userDetails) {
        Algorithm algorithm = Algorithm.HMAC256("password123");
        return JWT.create().withIssuer("EVOLVE")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + 300000))
                .withSubject(userDetails.getUsername())
                .sign(algorithm);
    }

    public String getUsername(String token) {
        return JWT.decode(token).getSubject();
    }

//    public String gerarToken(UserDetails userDetails) {
//        SecretKey key = Keys.hmacShaKeyFor("senha123".getBytes(StandardCharsets.UTF_8));
//        return Jwts.builder()
//                .issuer("WEG")
//                .issuedAt(new Date())
//                .expiration(new Date(new Date().getTime() + 300000))
//                .signWith(this.key, Jwts.SIG.HS256)
//                .subject(userDetails.getUsername())
//                .compact();
//    }

//    public Jws<Claims> validarToken(String token) {
//        return getParser().parseSignedClaims(token);
//    }

//    private JwtParser getParser() {
//        return Jwts.parser()
//                .verifyWith(this.key)
//                .build();
//               .setSigningKey("senha123").build();
//    }

//    public String getUserName(String token) {
//        return validarToken(token)
//                .getPayload()
//                .getSubject();
//    }
}
