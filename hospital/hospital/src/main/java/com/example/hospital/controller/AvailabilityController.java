package com.example.hospital.controller;

import com.example.hospital.model.Availability;
import com.example.hospital.model.User;
import com.example.hospital.repository.AvailabilityRepository;
import com.example.hospital.repository.UserRepository;
import com.example.hospital.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        // Today's date
        LocalDate today = LocalDate.now();

        // Get all slots
        List<Availability> allSlots = repository.findAll();

        // Delete past slots
        for (Availability slot : allSlots) {

            LocalDate slotDate = LocalDate.parse(slot.getDate());

            if (slotDate.isBefore(today)) {
                repository.deleteById(slot.getId());
            }
        }

        // Return updated list
        return repository.findAll();

    }

    @PostMapping
    public Availability add(
            @RequestBody Availability availability,
             @RequestHeader("Authorization") String authHeader
    ) {
        System.out.println("AUTH HEADER = " + authHeader);

        String token = authHeader.substring(7);

        System.out.println("TOKEN = " + token);

        String email = jwtService.extractEmail(token);

        System.out.println("EMAIL = " + email);

        User doctor = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        availability.setDoctorId(doctor.getId());

        availability.setDoctorName(doctor.getName());

        return repository.save(availability);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
