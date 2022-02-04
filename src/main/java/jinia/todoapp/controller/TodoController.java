package jinia.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jinia.todoapp.entity.Todo;
import jinia.todoapp.service.TodoService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<List<TodoResponseWithUrlDto>> readTodoList(@RequestParam(required = false) Integer limit,
                                                              @RequestParam(required = false) Integer skip){
        List<TodoResponseWithUrlDto> todoList =
                todoService
                        .getTodoList(limit, skip)
                        .stream()
                        .map(TodoResponseWithUrlDto::of)
                        .collect(Collectors.toList());
        todoList.forEach(dto -> dto.setUrl(buildUrl(dto)));
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    private URI buildUrl(TodoResponseWithUrlDto dto) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto todoRequestDto){
        Todo todo = todoService.readTodoDetail(id);
        todoService.updateTodo(todo, todoRequestDto.getName(), todoRequestDto.getCompleted());
        TodoResponseDto todoResponseDto = TodoResponseDto.of(todo);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id){
        Todo todo = todoService.readTodoDetail(id);
        todoService.deleteTodo(todo);
        return ResponseEntity.noContent().build();
    }
}
