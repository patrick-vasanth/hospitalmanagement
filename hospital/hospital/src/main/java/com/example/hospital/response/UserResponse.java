package com.example.hospital.response;

public class UserResponse {

    private Long id;

    private String email;

    private String role;

    private String name;

    public UserResponse(Long id, String email, String role, String name) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
}