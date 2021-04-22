package com.linhnv.diary.services.validators;

import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.entities.User;
import com.linhnv.diary.models.requests.LoginRequest;
import com.linhnv.diary.models.responses.LoginResponse;
import com.linhnv.diary.utils.StringResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<SystemResponse<LoginResponse>> validate(User user, LoginRequest request) {

        if (user == null)
            return Response.badRequest(StringResponse.INVALID_ACCOUNT);

        boolean isCorrect = user.comparePassword(request.getPassword(), passwordEncoder);

        if (!isCorrect)
            return Response.badRequest(StringResponse.INVALID_PASSWORD);

        return Response.ok();
    }
}
