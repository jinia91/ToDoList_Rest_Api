package jinia.todoapp.service;

import jinia.todoapp.repository.TodoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.repository.TodoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

        private final TodoRepository todoRepository;
        private final TodoRepositoryImpl qTodoRepository;

    public Long createNewTodo(Todo todo) {
        todoRepository.save(todo);
        return todo.getId();
    }

    public Todo readTodoDetail(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("request wrong todoId"));
    }

    public List<Todo> getTodoList(Integer limit, Integer skip) {
        return qTodoRepository.findAllLimitSkip(limit, skip);
    }
}
