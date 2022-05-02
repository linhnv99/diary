package com.linhnv.diary.interceptors;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.models.bos.API;
import com.linhnv.diary.models.bos.RoleEnum;
import com.linhnv.diary.models.bos.UserJwt;
import com.linhnv.diary.services.jwts.JwtDistribute;
import com.linhnv.diary.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
            API.with("^/accounts/registers$"),
            API.with("^/feelings$", HttpMethod.GET)
    };

    public final API[] adminAuthAPIs = new API[]{
            API.with("^/topics/defaults/changers$", HttpMethod.PUT),
            API.with("^/feelings$", HttpMethod.POST),
            API.with("^/feelings$", HttpMethod.DELETE)
    };


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isSkipAuthAPI(request)) return true;

        DecodedJWT decodedJWT = jwtDistribute.authenticate(request, response);
        if (decodedJWT == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("invalid or expired access token");
            return false;
        }

        // Check role
        String role = getRole(request);
        if (isAdminAPI(request) && !RoleEnum.ADMIN.name().equals(role)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().println("not permission");
            return false;
        }

        return true;
    }

    private String getRole(HttpServletRequest request) {

        DecodedJWT decodedJWT;

        try {
            decodedJWT = (DecodedJWT) request.getAttribute(Global.USER_ATTR);
        } catch (Exception e) {
            return "";
        }

        return UserJwt.from(decodedJWT).getRole();
    }

    private boolean isAdminAPI(HttpServletRequest request) {
        for (API adminAuthAPI : this.adminAuthAPIs) {
            if (adminAuthAPI.isValidRequest(request)) return true;
        }
        return false;
    }

    private boolean isSkipAuthAPI(HttpServletRequest request) {
        for (API skipAuthAPI : this.skipAuthAPIs) {
            if (skipAuthAPI.isValidRequest(request)) return true;
        }
        return false;
    }
}
