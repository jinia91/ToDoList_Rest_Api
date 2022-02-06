package jinia.todoapp.todo.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jinia.todoapp.imgurl.ImgUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jinia.todoapp.todo.Todo;
import jinia.todoapp.todo.service.TodoService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final ImgUrlService imgUrlService;

    @ApiResponses({
            @ApiResponse(code = 201, message = "Todo 생성 성공"),
            @ApiResponse(code = 401, message = "인가 실패"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/todos")
    public ResponseEntity<TodoResponseDto> createNewTodo(@Valid @RequestBody TodoRequestDto todoRequestDto){
        Todo todo = todoRequestDto.toEntity();
        todoService.createNewTodo(todo);
        if(todoRequestDto.getImgUrlList() != null){
            imgUrlService.saveUrlList(todoRequestDto.getImgUrlList(), todo);
        }
        TodoResponseDto todoResponseDto = TodoResponseDto.of(todo);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.CREATED);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "정상 조회"),
            @ApiResponse(code = 404, message = "없는 리소스 조회"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoResponseDto> readTodoDetail(@PathVariable Long id){
        Todo todo = todoService.readTodoDetail(id);
        TodoResponseDto todoResponseDto = TodoResponseDto.of(todo);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo List 조회"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
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

    @ApiResponses({
            @ApiResponse(code = 200, message = "Todo 수정 성공"),
            @ApiResponse(code = 401, message = "인가 실패"),
            @ApiResponse(code = 404, message = "없는 리소스 수정 시도"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequestDto todoRequestDto){
        Todo todo = todoService.readTodoDetail(id);
        todoService.updateTodo(todo, todoRequestDto.getName(), todoRequestDto.getCompleted());
        imgUrlService.updateUrlList(todo,todoRequestDto.getImgUrlList());
        TodoResponseDto todoResponseDto = TodoResponseDto.of(todo);
        return new ResponseEntity<>(todoResponseDto, HttpStatus.OK);
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Todo 삭제 성공"),
            @ApiResponse(code = 401, message = "인가 실패"),
            @ApiResponse(code = 401, message = "없는 리소스 삭제 시도"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id){
        Todo todo = todoService.readTodoDetail(id);
        todoService.deleteTodo(todo);
        return ResponseEntity.noContent().build();
    }
}
