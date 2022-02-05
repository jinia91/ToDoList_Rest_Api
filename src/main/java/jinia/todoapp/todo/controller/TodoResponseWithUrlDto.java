package jinia.todoapp.todo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jinia.todoapp.todo.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseWithUrlDto {
    private Boolean completed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime completed_at;
    private Long id;
    private String name;
    private URI url;

    static public TodoResponseWithUrlDto of(Todo todo){
        TodoResponseWithUrlDto todoResponseWithUrlDto = new TodoResponseWithUrlDto();
        todoResponseWithUrlDto.completed = todo.getCompleted();
        todoResponseWithUrlDto.completed_at = todo.getComplete_at();
        todoResponseWithUrlDto.id= todo.getId();
        todoResponseWithUrlDto.name = todo.getName();
        return todoResponseWithUrlDto;
    }
}
