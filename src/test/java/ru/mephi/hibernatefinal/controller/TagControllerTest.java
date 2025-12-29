package ru.mephi.hibernatefinal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.mephi.hibernatefinal.dto.response.TagResponseDto;
import ru.mephi.hibernatefinal.entity.Tag;
import ru.mephi.hibernatefinal.service.TagService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private TagService tagService;

    @Test
    public void testCreateTag() throws Exception {
        TagResponseDto resp = new TagResponseDto(1, "Java");
        when(tagService.create(any(Tag.class))).thenReturn(resp);

        Tag input = new Tag();
        input.setName("Java");

        mvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    public void testGetTag() throws Exception {
        TagResponseDto resp = new TagResponseDto(2, "Hibernate");
        when(tagService.get(2)).thenReturn(resp);

        mvc.perform(get("/api/tags/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Hibernate"));
    }
}