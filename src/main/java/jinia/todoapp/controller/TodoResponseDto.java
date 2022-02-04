package jinia.todoapp.controller;

import lombok.*;
import jinia.todoapp.entity.Todo;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String name;
    private Boolean completed;
    private LocalDateTime completed_at;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    static public TodoResponseDto of(Todo todo){
        return new TodoResponseDto(
                todo.getId(),
                todo.getName(),
                todo.getCompleted(),
                todo.getComplete_at(),
                todo.getCreated_at(),
                todo.getUpdated_at());
    }
}
