package jinia.todoapp.controller;

import jinia.todoapp.domain.Todo;
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
