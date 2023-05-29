package net.skhu.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@Table(indexes = { @Index(name = "IDX_ID", columnList = "id", unique = true) })
public class User {

    public User() {

	}



	@Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email; // 이메일. 얘가 아이디가 될 거임

    private String nickname; // 닉네임

    @Column(unique = true)
    private String password; // 비밀번호

    @OneToMany(mappedBy = "user")
    private List<Todo> todos;
}
