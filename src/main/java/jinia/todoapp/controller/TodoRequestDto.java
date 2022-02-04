package jinia.todoapp.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jinia.todoapp.entity.Todo;

@Getter @Setter
@AllArgsConstructor
public class TodoRequestDto {
    private String name;
    private Boolean completed;

    public Todo toEntity() {
       return Todo.builder()
                .name(name)
                .completed(completed)
                .build();
    }
}
