package com.example.hospital.response;

public class UserResponse {

    private String email;
    private String role; // optional (DOCTOR / PATIENT
    private String name;

    public UserResponse(String email, String role,String name) {
        this.email = email;
        this.role = role;
        this.name=name;
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
