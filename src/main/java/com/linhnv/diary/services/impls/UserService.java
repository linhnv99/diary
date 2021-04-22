package com.linhnv.diary.services.impls;

import com.linhnv.diary.models.bos.AccountStatus;
import com.linhnv.diary.models.bos.Response;
import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.entities.User;
import com.linhnv.diary.models.requests.LoginRequest;
import com.linhnv.diary.models.responses.LoginResponse;
import com.linhnv.diary.repositories.UserRepository;
import com.linhnv.diary.services.IUserService;
import com.linhnv.diary.services.jwts.JwtUser;
import com.linhnv.diary.services.mappers.UserMapper;
import com.linhnv.diary.services.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserValidator validator;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private JwtUser jwtUser;

    @Override
    public ResponseEntity<SystemResponse<LoginResponse>> login(LoginRequest loginRequest) {

        User user = repository.findByUsernameAndStatus(loginRequest.getUsername(), AccountStatus.ACTIVE.name());

        ResponseEntity<SystemResponse<LoginResponse>> validate = validator.validate(user, loginRequest);

        if (!validate.getStatusCode().is2xxSuccessful())
            return validate;

        String token = jwtUser.findNGenerateToken(user, user.getRole());

        LoginResponse loginResponse = mapper.map(user, token);

        return Response.ok(loginResponse);
    }
}
