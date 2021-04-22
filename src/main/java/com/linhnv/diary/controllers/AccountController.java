package com.linhnv.diary.controllers;

import com.linhnv.diary.models.bos.SystemResponse;
import com.linhnv.diary.models.requests.LoginRequest;
import com.linhnv.diary.models.responses.LoginResponse;
import com.linhnv.diary.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private IUserService service;

    @PostMapping("/login")
    public ResponseEntity<SystemResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return service.login(loginRequest);
    }

}
