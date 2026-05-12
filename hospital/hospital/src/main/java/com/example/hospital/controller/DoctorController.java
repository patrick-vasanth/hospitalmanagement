package com.example.hospital.controller;

import com.example.hospital.model.User;
import com.example.hospital.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin("*")
public class DoctorController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getDoctors() {

        return userRepository.findByRole("DOCTOR");
    }
}