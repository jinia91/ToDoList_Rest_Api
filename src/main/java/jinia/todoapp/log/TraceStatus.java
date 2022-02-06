package jinia.todoapp.log;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ThreadSafe 를 위해, 로그 고유 ID의 상태값을 전달하는 객체
 */
@AllArgsConstructor
@Getter
public class TraceStatus {
    private TraceId traceId;
    private Long startTimesMs;
    private String message;
}
