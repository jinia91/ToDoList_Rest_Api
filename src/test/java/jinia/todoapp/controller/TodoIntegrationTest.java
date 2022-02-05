package jinia.todoapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jinia.todoapp.domain.Todo;
import jinia.todoapp.infra.TestDbCleaner;
import jinia.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.iterableWithSize;
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
    void cleanUp() {
        testDbCleaner.execute();
    }

    @Value("${auth.key}")
    String apiKey;

    @Test
    public void 새로운_TODO_생성요청_성공_테스트() throws Exception {
        // given
        TodoRequestDto todoRequestDto = createSampleTodoRequestDto();
        String contents = objectMapper.writeValueAsString(todoRequestDto);
        // when
        ResultActions perform = mockMvc.perform(post("/todos")
                .param("apikey",apiKey)
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

    @Test
    public void 새로운_TODO_생성요청_인가실패_테스트() throws Exception {
        // given
        TodoRequestDto todoRequestDto = createSampleTodoRequestDto();
        String contents = objectMapper.writeValueAsString(todoRequestDto);
        // when
        ResultActions perform = mockMvc.perform(post("/todos")
//                .param("apikey",apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contents));
        // then
        perform.andExpect(status().isUnauthorized());
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

    @Test
    public void TODO_단건_조회_실패_테스트() throws Exception {
        // given
        Todo sampleTodo = Todo.builder()
                .name("통합테스트해야되용")
                .completed(null)
                .build();
        todoRepository.save(sampleTodo);
        // when
        ResultActions perform = mockMvc.perform(get("/todos/10"));
        // then
        perform.andExpect(status().isNotFound());
    }

    @Test
    public void TODO리스트_조회_성공_테스트() throws Exception {
        // given
        int n = 20;
        while (n-- > 0) {
            todoRepository.save(new Todo("더미슉슉슉", null));
        }
        // when
        ResultActions perform = mockMvc.perform(get("/todos"));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$", iterableWithSize(10)));
        for (int i = 20; i > 10; i--) {
            String url = "http://localhost/todos/" + i;
            perform.andExpect(jsonPath(String.format("$[%d]['url']", 20 - i))
                    .value(url));
        }
    }

    @Test
    public void TODO_업데이트_성공_테스트() throws Exception {
        // given
        todoRepository.save(new Todo("before", null));
        Todo afterTodo = new Todo("after", true);
        String contents = objectMapper.writeValueAsString(afterTodo);
        // when
        ResultActions perform = mockMvc.perform(put("/todos/1")
                .param("apikey",apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contents));
        // then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("name").value(afterTodo.getName()))
                .andExpect(jsonPath("completed").value(true));
    }

    @Test
    public void TODO_업데이트_인가실패_테스트() throws Exception {
        // given
        todoRepository.save(new Todo("before", null));
        Todo afterTodo = new Todo("after", true);
        String contents = objectMapper.writeValueAsString(afterTodo);
        // when
        ResultActions perform = mockMvc.perform(put("/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(contents));
        // then
        perform.andExpect(status().isUnauthorized());
    }


    @Test
    public void TODO_삭제_성공_테스트() throws Exception {
        // given
        Todo todo = todoRepository.save(new Todo("삭제예정", null));
        // when
        ResultActions perform = mockMvc.perform(delete("/todos/" + todo.getId())
                .param("apikey",apiKey));
        // then
        perform.andExpect(status().isNoContent());
    }

    @Test
    public void TODO_삭제_인가_실패_테스트() throws Exception {
        // given
        Todo todo = todoRepository.save(new Todo("삭제예정", null));
        // when
        ResultActions perform = mockMvc.perform(delete("/todos/" + todo.getId()));
        // then
        perform.andExpect(status().isUnauthorized());
    }
}