package net.skhu.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import net.skhu.entity.Calendar;
import net.skhu.entity.Todo;
import net.skhu.entity.User;
import net.skhu.repository.CalendarRepository;
import net.skhu.repository.FriendRepository;
import net.skhu.repository.TodoRepository;
import net.skhu.repository.UserRepository;
import net.skhu.request.TodoRequest;
import net.skhu.response.TodoStatsResponse;


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

    @PostMapping("/todo/add") //할일추가
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

        // 버튼누르면 해당 유저의 할 일을 데이터베이스에서 삭제하는 코드 작성
    @PostMapping("/todo/delete")
    public ResponseEntity<String> deleteTodo(@RequestBody TodoRequest todoRequest, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 로그인되지 않았습니다.");
        }

        try {
            // 요청에서 할 일 ID를 추출합니다.
            int todoId = todoRequest.getTodoId();

            // 해당 할 일 ID와 현재 로그인된 사용자의 ID를 사용하여 캘린더에서 할 일을 찾습니다.
            Optional<Calendar> optionalCalendar = calendarRepository.findByUserIdAndTodoId(currentUser.getId(), todoId);
            if (optionalCalendar.isPresent()) {
                // 캘린더에서 할 일을 삭제합니다.
                calendarRepository.delete(optionalCalendar.get());

                // 연관된 할 일을 데이터베이스에서 삭제합니다.
                todoRepository.deleteById(todoId);

                return ResponseEntity.status(HttpStatus.OK).body("할 일이 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("할 일을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("할 일 삭제 중 오류가 발생했습니다.");
        }
    }


        // 버튼누르면 해당 유저의 할 일을 완료로 표시하는 코드 작성
    @PostMapping("/todo/updateStatus")
    public ResponseEntity<String> updateTodoStatus(@RequestBody TodoRequest todoRequest, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 로그인되지 않았습니다.");
        }

        try {
            // 요청에서 할 일 ID를 추출합니다.
            int todoId = todoRequest.getTodoId();

            // 해당 할 일 ID와 현재 로그인된 사용자의 ID를 사용하여 할 일을 찾습니다.
            Optional<Calendar> optionalCalendar = calendarRepository.findByUserIdAndTodoId(currentUser.getId(), todoId);
            if (optionalCalendar.isPresent()) {
                Calendar calendar = optionalCalendar.get();
                Todo todo = calendar.getTodo();

                // 현재 할 일의 상태를 가져옵니다.
                boolean completed = todo.isCompleted();

                // 할 일의 상태를 반대로 변경합니다.
                todo.setCompleted(!completed);

                // 변경된 할 일을 데이터베이스에 저장합니다.
                todoRepository.save(todo);

                return ResponseEntity.status(HttpStatus.OK).body("할 일의 상태가 업데이트되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("할 일을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("할 일 상태 업데이트 중 오류가 발생했습니다.");
        }
    }


        // 특정 날짜와 해당 유저의 저장된 할 일의 개수를 조회하는 코드 작성
    @GetMapping("/todo/stats")
    public ResponseEntity<TodoStatsResponse> getTodoStats(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, HttpSession session) {
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        try {
            // 해당 날짜와 해당 유저의 총 할 일 개수를 가져옵니다.
            int totalTodoCount = calendarRepository.countByUserIdAndDate(currentUser.getId(), date);

            // 해당 날짜와 해당 유저의 완료된 일 개수를 가져옵니다.
            int completedTodoCount = calendarRepository.countByUserIdAndDateAndCompleted(currentUser.getId(), date, true);

            // TodoStatsResponse 객체를 생성하여 결과를 담습니다.
            TodoStatsResponse statsResponse = new TodoStatsResponse();
            statsResponse.setDate(date);
            statsResponse.setTotalTodoCount(totalTodoCount);
            statsResponse.setCompletedTodoCount(completedTodoCount);

            return ResponseEntity.status(HttpStatus.OK).body(statsResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


        // git test1




}
