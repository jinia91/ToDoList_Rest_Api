package test.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
*  헬스체크 컨트롤러
*/
@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/api/ping")
    public String ping(){
        return "OK";
    }
}
