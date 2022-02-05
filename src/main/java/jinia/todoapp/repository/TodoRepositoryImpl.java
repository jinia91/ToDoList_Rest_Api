package jinia.todoapp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jinia.todoapp.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static jinia.todoapp.domain.QTodo.*;

@RequiredArgsConstructor
@Qualifier("qTodoRepository")
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    static final Integer DEFAULT_LIMIT = 10;

    public List<Todo> findAllLimitSkip(Integer limit, Integer skip) {

        if(limit == null){
            limit = DEFAULT_LIMIT;
        }
        if(limit > 100){
            limit = 100;
        }
        if(skip == null){
            skip = 0;
        }

        return jpaQueryFactory
                .selectFrom(todo)
                .limit(limit)
                .offset(skip)
                .orderBy(todo.id.desc())
                .fetch();
    }
}
