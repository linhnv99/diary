package com.linhnv.diary.interceptors;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.models.bos.API;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    public final API[] skipAuthAPIs = new API[]{
            API.with("^/users/login$"),
            API.with("^/users/logout$"),
            API.with("^/users/registers$")
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(isSkipAuthAPI(request)) return true;


        return true;
    }

    private boolean isSkipAuthAPI(HttpServletRequest request) {
        for (API skipAuthAPI : this.skipAuthAPIs) {
            if (skipAuthAPI.isSkipRequest(request)) return true;
        }
        return false;
    }
}
