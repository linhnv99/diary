package com.linhnv.diary.models.bos;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.linhnv.diary.models.entities.User;

public class UserJwt {
    private String id;
    private String username;
    private String role;
    private DecodedJWT decodedJWT;

    public UserJwt() {
    }

    public UserJwt(String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public static UserJwt from(DecodedJWT decodedJWT) {
        return new UserJwt(
                decodedJWT.getClaim("id").asString(),
                decodedJWT.getClaim("username").asString(),
                decodedJWT.getClaim("role").asString()
        );
    }

    public static UserJwt from(User user, String role) {
        return new UserJwt(
                user.getId(),
                user.getRole(),
                role
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public DecodedJWT getDecodedJWT() {
        return decodedJWT;
    }

    public void setDecodedJWT(DecodedJWT decodedJWT) {
        this.decodedJWT = decodedJWT;
    }
}
