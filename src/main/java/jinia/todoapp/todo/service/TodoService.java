package jinia.todoapp.todo.service;

import jinia.todoapp.exception.NotFoundException;
import jinia.todoapp.todo.repository.TodoRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jinia.todoapp.todo.Todo;
import jinia.todoapp.todo.repository.TodoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

        private final TodoRepository todoRepository;

   @Transactional
    public Long createNewTodo(Todo todo) {
        todoRepository.save(todo);
        return todo.getId();
    }

    public Todo readTodoDetail(Long todoId) {
        return todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundException("Not Found"));
    }

    public List<Todo> getTodoList(Integer limit, Integer skip) {
        return todoRepository.findAllLimitSkip(limit, skip);
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
