package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.request.UserRequestDto;
import ru.mephi.hibernatefinal.dto.response.UserResponseDto;
import ru.mephi.hibernatefinal.entity.Role;
import ru.mephi.hibernatefinal.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        UserResponseDto resp = new UserResponseDto(1, "Test", "t@example.com", Role.STUDENT);
        when(userService.createUser(any(UserRequestDto.class))).thenReturn(resp);

        UserRequestDto in = new UserRequestDto("Test", "t@example.com", Role.STUDENT);

        mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(in)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("t@example.com"));
    }

    @Test
    public void testGetUser() throws Exception {
        UserResponseDto resp = new UserResponseDto(2, "U2", "u2@example.com", Role.TEACHER);
        when(userService.getUser(2)).thenReturn(resp);

        mvc.perform(get("/api/users/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.role").value("TEACHER"));
    }
}