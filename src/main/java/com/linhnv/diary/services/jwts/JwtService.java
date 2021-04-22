package com.linhnv.diary.services.jwts;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public abstract class JwtService {
    @Autowired
    protected JedisPool jedisPool;

    public abstract DecodedJWT authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    public abstract Object getClaims(HttpServletRequest request);

    public abstract String findToken(String userId);

    public abstract DecodedJWT validateJWT(String token);

    public String toToken(String userId, String token) {
        return userId + Global.SEPARATOR + token;
    }
}
