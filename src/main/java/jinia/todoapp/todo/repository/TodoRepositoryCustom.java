package jinia.todoapp.todo.repository;

import jinia.todoapp.todo.*;

import java.util.List;

public interface TodoRepositoryCustom {

    public List<Todo> findAllLimitSkip(Integer limit, Integer skip);
}
