package jinia.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.service.TodoService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todos")
    public ResponseEntity<TodoResponseDto> createNewTodo(@Valid @RequestBody TodoRequestDto todoRequestDto){
        Todo todo = todoRequestDto.toEntity();
        todoService.createNewTodo(todo);
        TodoResponseDto todoResponseDto = TodoResponseDto.of(todo);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
    }
}
