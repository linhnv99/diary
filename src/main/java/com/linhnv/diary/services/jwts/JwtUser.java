package com.linhnv.diary.services.jwts;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.models.bos.UserJwt;
import com.linhnv.diary.models.entities.User;
import com.linhnv.diary.utils.Global;
import com.linhnv.diary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Service
public class JwtUser extends JwtService {

    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    @Value("${app.redis.defaultDB}")
    private int defaultDB;

    // Hour
    @Value("${app.jwt.ttl}")
    private Duration ttlJwt;

    @Autowired
    public JwtUser(@Value("app.jwt.secret") String secretKey) {
        this.algorithm = Algorithm.HMAC256(secretKey);
        this.jwtVerifier = JWT.require(this.algorithm).build();
    }

    @Override
    public DecodedJWT authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = getJWTToken(request);

        if (Utils.isNullOrEmpty(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("missing access token");
            return null;
        }

        DecodedJWT decodedJWT = validateJWT(token);

        if (decodedJWT == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("invalid or expired access token");
            return null;
        }

        request.setAttribute(Global.USER_ATTR, decodedJWT);

        return decodedJWT;
    }

    @Override
    public Object getClaims(HttpServletRequest request) {
        DecodedJWT decodedJWT;

        try {
            decodedJWT = (DecodedJWT) request.getAttribute(Global.USER_ATTR);
        } catch (Exception e) {
            return null;
        }

        return decodedJWT != null ? UserJwt.from(decodedJWT) : null;
    }

    @Override
    public DecodedJWT validateJWT(String token) {

        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (Exception e) {
            return null;
        }

        try (Jedis jedis = jedisPool.getResource()) {
            String redisKey = toToken(UserJwt.from(decodedJWT).getId(), token);

            boolean keyExists = jedis.exists(redisKey);

            return keyExists ? decodedJWT : null;
        }
    }

    @Override
    public String findToken(String userId) {
        try (Jedis jedis = jedisPool.getResource()) {
            Collection<String> tokens = jedis.keys(userId + Global.SEPARATOR + "*");

            if (tokens.isEmpty()) {
                return "";
            }

            return tokens.iterator().next().replaceFirst(userId + Global.SEPARATOR, "");
        }
    }

    public String generateJWT(UserJwt userJwt) {
        return JWT.create()
                .withIssuedAt(Date.from(Instant.now()))
                .withClaim("id", userJwt.getId())
                .withClaim("username", userJwt.getUsername())
                .withClaim("role", userJwt.getRole())
                .sign(this.algorithm);
    }


    public String findNGenerateToken(User user, String role) {
        String token = findToken(user.getId());

        if (token.isEmpty()) {
            token = generateJWT(UserJwt.from(user, role));
        }

        return setToken(user, token, role);
    }

    private String setToken(User user, String token, String role) {

        if (Global.ROLE_USER.equals(role))
            setToken(user.getId(), token, defaultDB, (int) ttlJwt.getSeconds());
        else
            setToken(user.getId(), token, 2, (int) ttlJwt.getSeconds());

        return token;
    }

    /**
     * Set token to redis
     *
     * @param userId
     * @param token
     * @param db
     * @param seconds
     */
    private void setToken(String userId, String token, int db, int seconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.select(db);
            String key = toToken(userId, token);
            jedis.set(key, userId);
            jedis.expire(key, seconds);
        }
    }

    /**
     * @param request
     * @return
     */
    protected String getJWTToken(HttpServletRequest request) {
        String authorizationVal = request.getHeader(Global.AUTHORIZATION);
        if (authorizationVal == null
                || authorizationVal.length() <= Global.BEARER.length() + 1
                || !authorizationVal.startsWith(Global.BEARER))
            return "";

        return authorizationVal.substring(Global.BEARER.length() + 1);
    }


}
