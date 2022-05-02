package com.linhnv.diary.services;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.LoginRequest;
import com.linhnv.diary.models.responses.LoginResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<SystemResponse<LoginResponse>> login(LoginRequest loginRequest);
}
