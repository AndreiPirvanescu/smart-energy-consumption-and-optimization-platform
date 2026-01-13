//package com.example.smart_energy_consumption_and_optimization_platform.controller;
//
//import com.example.smart_energy_consumption_and_optimization_platform.dto.BuildingResponseDTO;
//import com.example.smart_energy_consumption_and_optimization_platform.dto.CreateUserRequestDTO;
//import com.example.smart_energy_consumption_and_optimization_platform.dto.UserResponseDTO;
//import com.example.smart_energy_consumption_and_optimization_platform.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import tools.jackson.databind.ObjectMapper;
//
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper; // for converting DTOs to JSON
//
//    @MockBean
//    private UserService userService;
//
//    // -------- POST /api/users Success --------
//    @Test
//    void createUser_success() throws Exception {
//        CreateUserRequestDTO dto = CreateUserRequestDTO.builder()
//                .name("Alice")
//                .email("alice@example.com")
//                .build();
//
//        UserResponseDTO responseDTO = UserResponseDTO.builder()
//                .id(1L)
//                .name("Alice")
//                .email("alice@example.com")
//                .build();
//
//        when(userService.createUser(any(CreateUserRequestDTO.class)))
//                .thenReturn(responseDTO);
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Alice"))
//                .andExpect(jsonPath("$.email").value("alice@example.com"));
//    }
//
//    // -------- POST /api/users Validation Failure --------
//    @Test
//    void createUser_invalidEmail_shouldReturnBadRequest() throws Exception {
//        CreateUserRequestDTO dto = CreateUserRequestDTO.builder()
//                .name("") // blank name
//                .email("invalid-email") // invalid email
//                .build();
//
//        mockMvc.perform(post("/api/users")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").exists());
//    }
//
//    // -------- GET /api/users/{userId}/buildings --------
//    @Test
//    void getBuildings_returnsList() throws Exception {
//        Long userId = 1L;
//
//        List<BuildingResponseDTO> buildings = List.of(
//                BuildingResponseDTO.builder().name("Building A").address("123 Main St").build(),
//                BuildingResponseDTO.builder().name("Building B").address("456 Elm St").build()
//        );
//
//        when(userService.getBuildingsByUser(userId)).thenReturn(buildings);
//
//        mockMvc.perform(get("/api/users/{userId}/buildings", userId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[0].name").value("Building A"))
//                .andExpect(jsonPath("$[0].address").value("123 Main St"))
//                .andExpect(jsonPath("$[1].name").value("Building B"))
//                .andExpect(jsonPath("$[1].address").value("456 Elm St"));
//    }
//}
