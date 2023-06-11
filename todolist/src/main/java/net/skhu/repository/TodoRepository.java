package net.skhu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.skhu.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {


}