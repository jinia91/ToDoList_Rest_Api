package jinia.todoapp.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String status;
    private String error;
}
