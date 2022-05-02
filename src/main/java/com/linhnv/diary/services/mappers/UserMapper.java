package com.linhnv.diary.services.mappers;

import com.linhnv.diary.models.entities.User;
import com.linhnv.diary.models.responses.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public LoginResponse map(User user, String token) {

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(user.getUsername());
        loginResponse.setRole(user.getRole());
        loginResponse.setToken(token);

        return loginResponse;
    }

}
