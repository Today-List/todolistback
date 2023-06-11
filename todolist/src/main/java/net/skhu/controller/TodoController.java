package net.skhu.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import net.skhu.entity.Todo;
import net.skhu.entity.User;
import net.skhu.repository.CalendarRepository;
import net.skhu.repository.FriendRepository;
import net.skhu.repository.TodoRepository;
import net.skhu.repository.UserRepository;
import net.skhu.request.TodoRequest;

@RestController
public class TodoController {
    private final TodoRepository todoRepository;
    private final CalendarRepository calendarRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository, CalendarRepository calendarRepository, FriendRepository friendRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.calendarRepository = calendarRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/todo/add")
    public ResponseEntity<String> addTodo(@RequestBody TodoRequest todoRequest, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        try {
            // 앱에서 받은 요청 데이터에서 날짜와 할 일 추출
            LocalDate date = todoRequest.getDate();
            String task = todoRequest.getTask();

            // Todo 엔티티 객체 생성 및 할 일 정보 설정
            Todo todo = new Todo();
            todo.setTask(task);
            todo.setCompleted(false);

            // Todo 엔티티 객체를 데이터베이스에 저장
            Todo savedTodo = todoRepository.save(todo);

            // Calendar 엔티티 객체 생성 및 날짜, 요일, 사용자, 할 일 정보 설정
            net.skhu.entity.Calendar calendar = new net.skhu.entity.Calendar();
            calendar.setNumdate(Date.valueOf(date));
            calendar.setWeekdate(date.getDayOfWeek().toString());
            calendar.setUser(currentUser);
            calendar.setTodo(savedTodo);

            // Calendar 엔티티 객체를 데이터베이스에 저장
            calendarRepository.save(calendar);

            return ResponseEntity.status(HttpStatus.CREATED).body("할 일이 저장되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("할 일 저장 중 오류가 발생했습니다.");
        }
    }






        // 해당 ID의 할 일을 데이터베이스에서 삭제하는 코드 작성
        // ...

        // 해당 ID의 할 일을 완료로 표시하는 코드 작성
        // ...

        // 특정 날짜와 사용자 ID로 저장된 할 일의 개수를 조회하는 코드 작성

        // ...


    // 친구와 서로 친구이며, 친구와 나의 완료된 할 일의 개수를 조회하는 코드 작성
}
