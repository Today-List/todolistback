package net.skhu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.skhu.entity.Friend;
import net.skhu.entity.User;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer> {

	 Friend findByFromUserAndToUser(User fromUser, User toUser);

}
