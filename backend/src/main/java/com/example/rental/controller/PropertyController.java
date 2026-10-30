package com.example.rental.controller;

import com.example.rental.domain.Property;
import com.example.rental.dto.PropertyRequest;
import com.example.rental.service.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping
    public Property create(@RequestBody PropertyRequest request) {
        return propertyService.create(request);
    }

    @GetMapping
    public Page<Property> list(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        return propertyService.list(page, size);
    }

    @GetMapping("/{id}")
    public Property getById(@PathVariable Long id) {
        return propertyService.getById(id);
    }
}
