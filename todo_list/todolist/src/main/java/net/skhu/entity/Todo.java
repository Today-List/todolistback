package net.skhu.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;

     String task;

     boolean completed;

     @OneToMany(mappedBy = "todo", cascade = CascadeType.REMOVE)
     private List<Calendar> calendars;



}