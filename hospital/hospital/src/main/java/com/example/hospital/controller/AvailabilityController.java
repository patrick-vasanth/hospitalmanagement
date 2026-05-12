package com.example.hospital.controller;

import com.example.hospital.model.Availability;
import com.example.hospital.model.User;
import com.example.hospital.repository.AvailabilityRepository;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@CrossOrigin("*")
public class AvailabilityController {

    @Autowired
    private AvailabilityRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;


    @GetMapping
    public List<Availability> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Availability add(
            @RequestBody Availability availability,
             @RequestHeader("Authorization") String authHeader
    ) {
        // Remove "Bearer "
        String token = authHeader.substring(7);

        // Extract email from JWT
        String email = jwtService.extractEmail(token);

        // Find doctor from DB
        User doctor = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Set doctor details automatically
        availability.setDoctorId(doctor.getId());
        availability.setDoctorName(doctor.getName());

        return repository.save(availability);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
