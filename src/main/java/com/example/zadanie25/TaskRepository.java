package com.example.zadanie25;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findAllByIsDoneOrderByDeadline(Boolean isDone);
}
