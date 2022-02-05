package jinia.todoapp.todo.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import jinia.todoapp.todo.Todo;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@AllArgsConstructor
public class TodoRequestDto {

    @NotEmpty
    private String name;
    private Boolean completed;

    public Todo toEntity() {
       return Todo.builder()
                .name(name)
                .completed(completed)
                .build();
    }
}
