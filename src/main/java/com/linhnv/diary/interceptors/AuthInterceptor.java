package com.linhnv.diary.interceptors;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.models.bos.API;
import com.linhnv.diary.services.jwts.JwtDistribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtDistribute jwtDistribute;

    public final API[] skipAuthAPIs = new API[]{
            API.with("^/accounts/login$"),
            API.with("^/accounts/logout$"),
            API.with("^/accounts/registers$")
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(isSkipAuthAPI(request)) return true;

        DecodedJWT decodedJWT = jwtDistribute.authenticate(request, response);
        if (decodedJWT == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("invalid or expired access token");
            return false;
        }

        return true;
    }

    private boolean isSkipAuthAPI(HttpServletRequest request) {
        for (API skipAuthAPI : this.skipAuthAPIs) {
            if (skipAuthAPI.isSkipRequest(request)) return true;
        }
        return false;
    }
}
