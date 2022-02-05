package jinia.todoapp.todo.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import jinia.todoapp.todo.Todo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> , TodoRepositoryCustom {

    @EntityGraph(attributePaths = "urlList")
    Optional<Todo> findById(Long id);
}
