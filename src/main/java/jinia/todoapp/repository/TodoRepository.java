package jinia.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jinia.todoapp.domain.Todo;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> , TodoRepositoryCustom {
}
