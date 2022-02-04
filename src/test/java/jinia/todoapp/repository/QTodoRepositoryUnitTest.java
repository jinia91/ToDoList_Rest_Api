package jinia.todoapp.repository;

import jinia.todoapp.entity.Todo;
import jinia.todoapp.infra.DbConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(DbConfig.class)
@ExtendWith(SpringExtension.class)
class QTodoRepositoryUnitTest {

    TodoRepository todoRepository;
    TodoRepositoryCustom qTodoRepository;

    @Autowired
    public QTodoRepositoryUnitTest(TodoRepository todoRepository, @Qualifier("qTodoRepository") TodoRepositoryCustom qTodoRepository) {
        this.todoRepository = todoRepository;
        this.qTodoRepository = qTodoRepository;
    }

    @Test
    public void 파라미터_null일경우_TODO리스트_페이징_성공_테스트() throws Exception {
        // given
        int N = 20;
        while (N-- > 0) {
            todoRepository.save(new Todo("더미할일", null));
        }
        // when
        List<Todo> todoList = qTodoRepository.findAllLimitSkip(null, null);
        // then
        assertThat(todoList.size()).isEqualTo(10);
    }

    @Test
    public void 파라미터가_존재할경우_TODO리스트_페이징_성공_테스트() throws Exception {
        // given
        int N = 20;
        while (N-- > 0) {
            todoRepository.save(new Todo("더미할일", null));
        }
        // when
        List<Todo> todoList = qTodoRepository.findAllLimitSkip(5, 10);
        // then
        assertThat(todoList.size()).isEqualTo(5);
    }

    @Test
    public void limit이_100초과일경우_TODO리스트_페이징_성공_테스트() throws Exception {
        // given
        int N = 20;
        while (N-- > 0) {
            todoRepository.save(new Todo("더미할일", null));
        }
        // when
        List<Todo> todoList = qTodoRepository.findAllLimitSkip(500, 10);
        // then
        assertThat(todoList.size()).isEqualTo(10);
    }
}