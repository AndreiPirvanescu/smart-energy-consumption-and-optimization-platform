package com.example.smart_energy_consumption_and_optimization_platform.service;

import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.CreateUserRequestDTO;
import com.example.smart_energy_consumption_and_optimization_platform.dto.UserResponseDTO;
import com.example.smart_energy_consumption_and_optimization_platform.model.Building;
import com.example.smart_energy_consumption_and_optimization_platform.model.User;
import com.example.smart_energy_consumption_and_optimization_platform.repository.BuildingRepository;
import com.example.smart_energy_consumption_and_optimization_platform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BuildingRepository buildingRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_success() {
        CreateUserRequestDTO dto = CreateUserRequestDTO.builder()
                .name("Alice")
                .email("alice@example.com")
                .build();

        User savedUser = User.builder()
                .id(1L)
                .name("Alice")
                .email("alice@example.com")
                .build();

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO response = userService.createUser(dto);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Alice", response.getName());
        assertEquals("alice@example.com", response.getEmail());

        verify(userRepository).findByEmail("alice@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_emailAlreadyExists_throwsException() {
        CreateUserRequestDTO dto = CreateUserRequestDTO.builder()
                .name("Bob")
                .email("bob@example.com")
                .build();

        User existingUser = User.builder()
                .id(2L)
                .name("Bob")
                .email("bob@example.com")
                .build();

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(existingUser));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.createUser(dto));

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository).findByEmail("bob@example.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getBuildingsByUser_returnsDTOList() {
        Long userId = 1L;

        Building building1 = Building.builder()
                .id(10L)
                .name("Building A")
                .address("123 Main St")
                .owner(User.builder().id(userId).build())
                .build();

        Building building2 = Building.builder()
                .id(11L)
                .name("Building B")
                .address("456 Elm St")
                .owner(User.builder().id(userId).build())
                .build();

        when(buildingRepository.findByOwnerId(userId))
                .thenReturn(List.of(building1, building2));

        List<BuildingResponseDTO> buildings = userService.getBuildingsByUser(userId);

        assertNotNull(buildings);
        assertEquals(2, buildings.size());

        assertEquals("Building A", buildings.get(0).getName());
        assertEquals("123 Main St", buildings.get(0).getAddress());

        assertEquals("Building B", buildings.get(1).getName());
        assertEquals("456 Elm St", buildings.get(1).getAddress());

        verify(buildingRepository).findByOwnerId(userId);
    }
}
