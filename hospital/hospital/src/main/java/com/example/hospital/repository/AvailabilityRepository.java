package com.example.hospital.repository;

import com.example.hospital.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository
    extends JpaRepository<Availability, Long>{
}
