package jinia.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jinia.todoapp.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
