package jinia.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void 새로운_TODO_생성요청_성공_테스트() throws Exception {
        // given
        String name = "Todo 열심히 하자";
        Boolean completed = null;
        TodoRequestDto todoRequestDto = new TodoRequestDto(name, completed);
        String contents = objectMapper.writeValueAsString(todoRequestDto);
        // when
        ResultActions perform = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contents));
        // then
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value(name))
                .andExpect(jsonPath("completed").isEmpty())
                .andExpect(jsonPath("completed_at").isEmpty());
    }
}