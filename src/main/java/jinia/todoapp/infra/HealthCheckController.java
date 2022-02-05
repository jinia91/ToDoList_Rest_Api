package jinia.todoapp.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
*  헬스체크 컨트롤러
*/
@RestController
@RequiredArgsConstructor
@ApiIgnore
public class HealthCheckController {

    @GetMapping("/api/ping")
    public String ping(){
        return "OK";
    }
}
