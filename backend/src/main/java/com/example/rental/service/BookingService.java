package com.example.rental.service;

import com.example.rental.domain.Booking;
import com.example.rental.domain.Property;
import com.example.rental.domain.User;
import com.example.rental.repository.BookingRepository;
import com.example.rental.repository.PropertyRepository;
import com.example.rental.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          PropertyRepository propertyRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    /**
     * Проверка доступности: пересечение интервалов [start, end).
     * Два интервала пересекаются, если start1 < end2 && start2 < end1.
     */
    public boolean isAvailable(Long propertyId, LocalDate start, LocalDate end) {
        if (start == null || end == null || !start.isBefore(end)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        List<Booking> active = bookingRepository.findActiveByProperty(propertyId);
        for (Booking b : active) {
            if (start.isBefore(b.getEndDate()) && b.getStartDate().isBefore(end)) {
                return false;
            }
        }
        return true;
    }

    @Transactional
    public Booking create(Long propertyId, Long guestId, LocalDate start, LocalDate end) {
        if (!isAvailable(propertyId, start, end)) {
            throw new IllegalStateException("Dates are not available");
        }
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        User guest = userRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Booking booking = new Booking(property, guest, start, end);
        return bookingRepository.save(booking);
    }
}
