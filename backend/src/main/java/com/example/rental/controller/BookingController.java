package com.example.rental.controller;

import com.example.rental.domain.Booking;
import com.example.rental.dto.BookingRequest;
import com.example.rental.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/availability")
    public Map<String, Boolean> checkAvailability(@RequestParam Long propertyId,
                                                  @RequestParam String startDate,
                                                  @RequestParam String endDate) {
        boolean ok = bookingService.isAvailable(
                propertyId,
                java.time.LocalDate.parse(startDate),
                java.time.LocalDate.parse(endDate)
        );
        return Map.of("available", ok);
    }

    @PostMapping
    public Booking create(@RequestBody BookingRequest request) {
        return bookingService.create(
                request.getPropertyId(),
                request.getGuestId(),
                request.getStartDate(),
                request.getEndDate()
        );
    }
}
