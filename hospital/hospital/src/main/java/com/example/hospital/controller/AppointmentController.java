package com.example.hospital.controller;

import com.example.hospital.model.Appointment;
import com.example.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/appointments")
@CrossOrigin("*")
public class AppointmentController {
    @Autowired
    private AppointmentRepository appointmentRepository;

    // ========================================
    // GET ALL
    // ========================================

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // ========================================
    // CREATE
    // ========================================

    @PostMapping
    public Appointment createAppointment(
            @RequestBody Appointment appointment
    ) {
        System.out.println(appointment.getStartTime());
        System.out.println(appointment.getEndTime());
        return appointmentRepository.save(appointment);
    }

    // ========================================
    // UPDATE
    // ========================================

    @PutMapping("/{id}")
    public Appointment updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment updatedAppointment
    ) {

        Appointment appointment = appointmentRepository
                .findById(id)
                .orElseThrow();

        appointment.setPatientName(updatedAppointment.getPatientName());
        appointment.setDoctorName(updatedAppointment.getDoctorName());

        appointment.setDoctorId(updatedAppointment.getDoctorId());
        appointment.setPatientId(updatedAppointment.getPatientId());

        appointment.setStartTime(updatedAppointment.getStartTime());
        appointment.setEndTime(updatedAppointment.getEndTime());

        return appointmentRepository.save(appointment);
    }

    // ========================================
    // DELETE
    // ========================================

    @DeleteMapping("/{id}")
    public String deleteAppointment(@PathVariable Long id) {

        appointmentRepository.deleteById(id);

        return "Appointment deleted";
    }
}
