package net.skhu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @Column(name = "are_we_friend")
    private int areWeFriend;

    public void setFromUserId(int fromUserId) {
        this.fromUser = new User();
        this.fromUser.setId(fromUserId);
    }

    public void setToUserId(int toUserId) {
        this.toUser = new User();
        this.toUser.setId(toUserId);
    }
}