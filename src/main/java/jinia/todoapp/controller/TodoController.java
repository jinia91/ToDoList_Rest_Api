package jinia.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.service.TodoService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponseDto> readTodoDetail(@PathVariable Long todoId){
        Todo todo = todoService.readTodoDetail(todoId);
        TodoResponseDto todoResponseDto = TodoResponseDto.of(todo);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDto>> readTodoList(@RequestParam(required = false) Integer limit,
                                                              @RequestParam(required = false) Integer skip){
        List<TodoResponseDto> todoList =
                todoService
                        .getTodoList(limit, skip)
                        .stream()
                        .map(TodoResponseDto::of)
                        .collect(Collectors.toList());
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

}
