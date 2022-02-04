package jinia.todoapp.service;

import jinia.todoapp.entity.Todo;
import jinia.todoapp.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceUnitTest {

    @InjectMocks
    TodoService todoService;

    @Mock
    TodoRepository todoRepository;

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
        assertThat(todoId).isEqualTo(1L);
        assertThat(todo1.getId()).isEqualTo(todoId);
    }

    private Todo mockRepositorySave(Todo todo1) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class clazz = Class.forName("jinia.todoapp.entity.Todo");
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(todo1, 1L);
        return todo1;
    }


}