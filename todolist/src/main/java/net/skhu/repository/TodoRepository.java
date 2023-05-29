package net.skhu.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer>{

}
