package com.example.rental.repository;

import com.example.rental.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.property.id = :propertyId and b.status <> 'CANCELLED'")
    List<Booking> findActiveByProperty(@Param("propertyId") Long propertyId);
}
