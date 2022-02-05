package jinia.todoapp.todo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import jinia.todoapp.img.ImageUrl;
import lombok.*;
import jinia.todoapp.todo.Todo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {

    private Long id;
    private String name;
    private Boolean completed;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime completed_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime created_at;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime updated_at;
    private List<String> imgUrlList;

    static public TodoResponseDto of(Todo todo) {
        TodoResponseDto todoResponseDto = new TodoResponseDto();
        todoResponseDto.setId(todo.getId());
        todoResponseDto.setName(todo.getName());
        todoResponseDto.setCompleted(todo.getCompleted());
        todoResponseDto.setCompleted_at(todo.getComplete_at());
        todoResponseDto.setCreated_at(todo.getCreated_at());
        todoResponseDto.setUpdated_at(todo.getUpdated_at());

        if (todo.getUrlList().size() != 0) {
            todoResponseDto.imgUrlList =
                    todo.getUrlList()
                            .stream()
                            .map(ImageUrl::getUrl)
                            .collect(Collectors.toList());
        }

        return todoResponseDto;
    }
}
