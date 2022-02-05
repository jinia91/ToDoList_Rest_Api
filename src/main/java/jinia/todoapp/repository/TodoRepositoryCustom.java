package jinia.todoapp.repository;

import jinia.todoapp.domain.Todo;

import java.util.List;

public interface TodoRepositoryCustom {

    public List<Todo> findAllLimitSkip(Integer limit, Integer skip);
}
