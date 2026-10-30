package com.example.rental.service;

import com.example.rental.domain.Property;
import com.example.rental.domain.User;
import com.example.rental.dto.PropertyRequest;
import com.example.rental.repository.PropertyRepository;
import com.example.rental.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyService(PropertyRepository propertyRepository,
                           UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    public Property create(PropertyRequest request) {
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));
        Property property = new Property(
                request.getTitle(),
                request.getCity(),
                request.getAddress(),
                request.getRooms(),
                request.getPricePerNight(),
                owner
        );
        return propertyRepository.save(property);
    }

    public Page<Property> list(int page, int size) {
        return propertyRepository.findAll(PageRequest.of(page, size));
    }

    public Property getById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
    }
}
