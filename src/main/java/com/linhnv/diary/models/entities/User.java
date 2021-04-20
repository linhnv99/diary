package com.linhnv.diary.models.entities;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbsEntity{

    @Column(columnDefinition = "NVARCHAR(100)")
    private String name;

    @Column(columnDefinition = "VARCHAR(64)")
    private String username;

    @Column(columnDefinition = "VARCHAR(64)")
    private String hashPassword;

    @Column(columnDefinition = "NVARCHAR(25)")
    private String phone;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String email;

    @Column(name = "status", columnDefinition = "VARCHAR(20)")
    // 0 : Deleted
    // 1 : Active
    // 2 : Waiting for active
    // 3 : Lock
    private String status;

    @Column(name = "role", columnDefinition = "VARCHAR(15)")
    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setPassword(String password, PasswordEncoder passwordEncoder) {
        this.hashPassword = passwordEncoder.encode(password);
    }

    public boolean comparePassword(String passwordIn, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(passwordIn, this.hashPassword);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
