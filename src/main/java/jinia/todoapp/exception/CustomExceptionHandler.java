package jinia.todoapp.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 요청 dto validation 유효성 검사
     *
     * @return 스펙에 정의된 exceptionResponse + http 상태코드(400)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("400", "Todo 이름은 비어있을 수 없습니다");

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청 dto 추가 유효성검사
     *
     * @return 스펙에 정의된 exceptionResponse + http 상태코드(400)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleUserNotValidArgumentExceptions(Exception ex){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("400", ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 리소스 조회 요청시 에러 핸들링
     *
     * @return 스펙에 정의된 exceptionResponse + http 상태코드(404)
     */
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundExceptions(Exception ex){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("404", ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 인가되지 않은 요청시 에러 핸들링
     *
     * @return 스펙에 정의된 exceptionResponse + http 상태코드(401)
     */
    @ExceptionHandler(NotAuthorizedException.class)
    public final ResponseEntity<Object> handleUserNotAuthorizedExceptions(Exception ex){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("401", ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 서버 에러시 에러 핸들링
     *
     * @return 스펙에 정의된 exceptionResponse + http 상태코드(500)
     */
    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleAllRuntimeExceptions(Exception ex){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse("500", ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
