package net.skhu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(name = "Todo")
public class Todo {

    @Id
    private int id; // Todo의 기본키

    @Column(name = "task")
    private String taskName; // 전체 할일

    @Column(name = "completed")
    private int completed; // 수행 완료 할일 (0 또는 1로 구분)

    @ManyToOne
    @JoinColumn(name = "email") //User 테이블과의 외래키
    private User user;
}

