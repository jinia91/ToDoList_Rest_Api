package jinia.todoapp.todo.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jinia.todoapp.todo.Todo;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequestDto {

    @NotEmpty
    private String name;
    private Boolean completed;
    private List<String> imgUrlList;

    public Todo toEntity() {
       return Todo.builder()
                .name(name)
                .completed(completed)
                .build();
    }

    public TodoRequestDto(String name, Boolean completed) {
        this.name = name;
        this.completed = completed;
    }
}
