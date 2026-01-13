package com.example.smart_energy_consumption_and_optimization_platform.controller;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.CreateUserRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.UserResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void testGetBuildings() throws Exception {
        BuildingResponseDTO building1 = BuildingResponseDTO.builder()
                .name("Casa 1")
                .address("Strada Mieilor")
                .build();
        BuildingResponseDTO building2 = BuildingResponseDTO.builder()
                .name("Casa 2")
                .address("Strada Palanca")
                .build();

        Mockito.when(userService.getBuildingsByUser(anyLong()))
                .thenReturn(List.of(building1, building2));

        mockMvc.perform(get("/api/users/1/buildings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Casa 1"))
                .andExpect(jsonPath("$[0].address").value("Strada Mieilor"))
                .andExpect(jsonPath("$[1].name").value("Casa 2"))
                .andExpect(jsonPath("$[1].address").value("Strada Palanca"));
    }

    @Test
    void testCreateUser() throws Exception {
        CreateUserRequestDTO request = new CreateUserRequestDTO("Ion Costel", "ion@yahoo.com");
        UserResponseDTO response = new UserResponseDTO(1L, "Ion Costel", "ion@yahoo.com");

        Mockito.when(userService.createUser(any(CreateUserRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ion Costel"))
                .andExpect(jsonPath("$.email").value("ion@yahoo.com"));
    }
}

