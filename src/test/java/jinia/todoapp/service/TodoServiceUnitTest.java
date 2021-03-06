package jinia.todoapp.service;

import jinia.todoapp.todo.Todo;
import jinia.todoapp.todo.repository.TodoRepositoryImpl;
import jinia.todoapp.todo.repository.TodoRepository;
import jinia.todoapp.todo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TodoServiceUnitTest {

    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository todoRepository;

    @Mock
    TodoRepositoryImpl qTodoRepository;

    @Test
    public void 새로운_TODO_생성_성공_테스트() throws Exception {
        // given
        Todo todo1 = Todo.builder()
                .name("단위 테스트합시다")
                .completed(null)
                .build();
        Mockito
                .when(todoRepository.save(todo1))
                .then(invocation -> mockRepositorySave(todo1));
        // when
        Long todoId = todoService.createNewTodo(todo1);
        // then
        assertThat(todoId).isEqualTo(todo1.getId());
    }

    private Todo mockRepositorySave(Todo todo1) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class clazz = Class.forName("jinia.todoapp.todo.Todo");
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(todo1, 1L);
        return todo1;
    }

    @Test
    public void 단건TODO조회_성공_테스트() throws Exception {
        // given
        Todo todo1 = Todo.builder()
                .name("" +
                        "단위 테스트합시다")
                .completed(null)
                .build();
        Mockito
                .when(todoRepository.findById(1L))
                .then(invocation ->  Optional.of(mockRepositorySave(todo1)));
        // when
        Todo todo = todoService.readTodoDetail(1L);
        // then
        assertThat(todo.getId()).isEqualTo(1L);
        assertThat(todo.getName()).isEqualTo("단위 테스트합시다");
    }

}