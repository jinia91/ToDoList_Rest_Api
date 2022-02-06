package jinia.todoapp.log;

import lombok.Getter;

import java.util.UUID;

/**
 * 요청별(쓰레드별) 로그 고유 ID 기록 객체
 */
@Getter
public class TraceId {
    private final String id;
    private final int level;

    public TraceId() {
        this.id = createdId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createdId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public TraceId createNextId(){
        return new TraceId(id, level+1);
    }

    public TraceId createPrevId(){
        return new TraceId(id, level-1);
    }

    public boolean isFirstLevel(){
        return level == 0;
    }
}
