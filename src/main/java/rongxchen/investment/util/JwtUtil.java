package rongxchen.investment.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import rongxchen.investment.exceptions.HttpException;
import rongxchen.investment.exceptions.UnauthorizedException;
import rongxchen.investment.models.JwtParamMap;

import java.time.Instant;

public class JwtUtil {

    private JwtUtil() {}

    private static final int MILLIS_PER_MINUTE = 60;

    private static final String SEC_KEY = EncUtil.calculateSHA256("investment");

    public static String generateToken(JwtParamMap paramMap, int minutes) {
        Instant exp = Instant.ofEpochSecond(Instant.now().getEpochSecond() + (long) MILLIS_PER_MINUTE * minutes);
        return JWT.create()
                .withClaim("appId", paramMap.getAppId())
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256(SEC_KEY));
    }

    public static JwtParamMap decodeToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SEC_KEY)).build();
            verifier.verify(token);
            DecodedJWT decode = JWT.decode(token);
            Instant expiresAt = decode.getExpiresAtAsInstant();
            if (expiresAt.isBefore(Instant.ofEpochSecond(Instant.now().getEpochSecond()))) {
                throw new HttpException(HttpStatus.UNAUTHORIZED, "token expired");
            }
            JwtParamMap paramMap = new JwtParamMap();
            decode.getClaims().forEach((k, v) -> {
                if ("appId".equals(k)) {
                    paramMap.setAppId(v.toString());
                } else {
                    paramMap.addParam(k, v.toString());
                }
            });
            return paramMap;
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException("token expired");
        }
    }

}
