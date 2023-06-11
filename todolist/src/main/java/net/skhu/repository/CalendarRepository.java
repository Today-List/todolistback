package net.skhu.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.skhu.entity.Calendar;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    List<Calendar> findByUserId(int userId);
    int countByUserIdAndNumdate(int userId, LocalDate numdate);
}
