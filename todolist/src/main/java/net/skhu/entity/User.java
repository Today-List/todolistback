package net.skhu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;

     String email;

     String nickname;

     String password;

     @Column(name = "is_login")
     private boolean isLogin; // 로그인 여부



}
