package net.skhu.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.skhu.entity.Calendar;
import net.skhu.entity.User;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findByUserId(int userId);
    int countByUserIdAndNumdate(int userId, LocalDate numdate);
    void deleteByUserAndNumdate(User user, Date numdate);
    Optional<Calendar> findByUserIdAndTodoId(int userId, int todoId);

    @Query("SELECT COUNT(c) FROM Calendar c WHERE c.user.id = :userId AND c.numdate = :date")
    int countByUserIdAndDate(int userId, LocalDate date);

    @Query("SELECT COUNT(c) FROM Calendar c WHERE c.user.id = :userId AND c.numdate = :date AND c.todo.completed = true")
    int countByUserIdAndDateAndCompleted(int userId, LocalDate date, boolean completed);
}
