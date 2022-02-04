package jinia.todoapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.repository.TodoRepository;

@Service
@RequiredArgsConstructor
public class TodoService {

        private final TodoRepository todoRepository;

    public Long createNewTodo(Todo todo) {
        todoRepository.save(todo);
        return todo.getId();
    }
}
