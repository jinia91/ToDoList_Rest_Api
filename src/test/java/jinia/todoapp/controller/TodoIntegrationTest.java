package jinia.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.infra.TestDbCleaner;
import jinia.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TodoIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TestDbCleaner testDbCleaner;

    @AfterEach
    void cleanUp(){
        testDbCleaner.execute();
    }

    @Test
    public void 새로운_TODO_생성요청_성공_테스트() throws Exception {
        // given
        TodoRequestDto todoRequestDto = createSampleTodoRequestDto();
        String contents = objectMapper.writeValueAsString(todoRequestDto);
        // when
        ResultActions perform = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contents));


        // then
        perform.andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value(todoRequestDto.getName()))
                .andExpect(jsonPath("completed").isEmpty())
                .andExpect(jsonPath("completed_at").isEmpty());
    }

    private TodoRequestDto createSampleTodoRequestDto() {
        String name = "Todo 열심히 하자";
        Boolean completed = null;
        return new TodoRequestDto(name, completed);
    }

    @Test
    public void TODO_단건_조회_성공_테스트() throws Exception {
        // given
        Todo sampleTodo = Todo.builder()
                .name("통합테스트해야되용")
                .completed(null)
                .build();
        todoRepository.save(sampleTodo);
        // when
        ResultActions perform = mockMvc.perform(get("/todos/1"));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value("통합테스트해야되용"))
                .andExpect(jsonPath("completed").isEmpty())
                .andExpect(jsonPath("completed_at").isEmpty());
    }
}