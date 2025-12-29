package ru.mephi.hibernatefinal.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.mephi.hibernatefinal.dto.request.UserRequestDto;
import ru.mephi.hibernatefinal.dto.response.UserResponseDto;
import ru.mephi.hibernatefinal.entity.Role;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceIntegrationTest {

    @Autowired
    private ru.mephi.hibernatefinal.service.UserService userService;

    @Test
    public void testCreateAndGetUser() {
        UserRequestDto dto = new UserRequestDto("Test User", "test.user@example.com", Role.STUDENT);
        UserResponseDto created = userService.createUser(dto);
        Assertions.assertNotNull(created.getId());
        Assertions.assertEquals("Test User", created.getName());

        UserResponseDto fetched = userService.getUser(created.getId());
        Assertions.assertNotNull(fetched);
        Assertions.assertEquals(created.getEmail(), fetched.getEmail());
    }
}