package com.example.rental.service;

import com.example.rental.domain.Booking;
import com.example.rental.repository.BookingRepository;
import com.example.rental.repository.PropertyRepository;
import com.example.rental.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceTest {

    private BookingRepository bookingRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingRepository = mock(BookingRepository.class);
        bookingService = new BookingService(bookingRepository,
                mock(PropertyRepository.class), mock(UserRepository.class));
    }

    @Test
    void availableWhenNoBookings() {
        when(bookingRepository.findActiveByProperty(1L)).thenReturn(List.of());
        assertTrue(bookingService.isAvailable(1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 15)));
    }

    @Test
    void notAvailableWhenOverlaps() {
        Booking existing = new Booking();
        existing.setStartDate(LocalDate.of(2026, 1, 12));
        existing.setEndDate(LocalDate.of(2026, 1, 18));
        when(bookingRepository.findActiveByProperty(1L)).thenReturn(List.of(existing));

        assertFalse(bookingService.isAvailable(1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 15)));
    }

    @Test
    void availableWhenAdjacent() {
        Booking existing = new Booking();
        existing.setStartDate(LocalDate.of(2026, 1, 15));
        existing.setEndDate(LocalDate.of(2026, 1, 20));
        when(bookingRepository.findActiveByProperty(1L)).thenReturn(List.of(existing));

        assertTrue(bookingService.isAvailable(1L,
                LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 15)));
    }

    @Test
    void rejectInvalidRange() {
        assertThrows(IllegalArgumentException.class,
                () -> bookingService.isAvailable(1L,
                        LocalDate.of(2026, 1, 15), LocalDate.of(2026, 1, 10)));
    }
}
