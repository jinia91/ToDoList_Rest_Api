package jinia.todoapp.service;

import jinia.todoapp.repository.TodoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.repository.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

        private final TodoRepository todoRepository;
        private final TodoRepositoryImpl qTodoRepository;

   @Transactional
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

    @Transactional
    public void updateTodo(Todo todo, String name, Boolean completed) {
        todo.update(name, completed);
    }

    @Transactional
    public void deleteTodo(Todo todo) {
       todoRepository.delete(todo);
    }
}
