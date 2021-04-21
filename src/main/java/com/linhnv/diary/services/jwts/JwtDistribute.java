package com.linhnv.diary.services.jwts;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.utils.Global;
import com.linhnv.diary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtDistribute {

    @Autowired
    private JwtUser jwtUser;

    public DecodedJWT authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JwtService jwtService = getJwtObject(request);
        if (jwtService == null)
            return null;

        return jwtService.authenticate(request, response);
    }


    /**
     * Get JwtObject from request
     *
     * @param request
     * @return
     */
    private JwtService getJwtObject(HttpServletRequest request) {

        String token = request.getHeader(Global.AUTHORIZATION);

        if (Utils.isNullOrEmpty(token))
            return null;

        String[] tokenValue = token.split("\\.");
        if (tokenValue.length < 2)
            return null;

        return jwtUser;
    }

}
