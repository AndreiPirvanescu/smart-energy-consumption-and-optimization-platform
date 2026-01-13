package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.CreateUserRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.UserResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/users/{userId}/buildings
    @GetMapping("/{userId}/buildings")
    public List<BuildingResponseDTO> getBuildings(@PathVariable Long userId) {
        return userService.getBuildingsByUser(userId);
    }

    // POST /api/users
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @Valid @RequestBody CreateUserRequestDTO dto) {

        UserResponseDTO response = userService.createUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
