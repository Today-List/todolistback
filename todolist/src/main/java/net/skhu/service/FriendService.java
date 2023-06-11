package net.skhu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.entity.Friend;
import net.skhu.entity.User;
import net.skhu.repository.FriendRepository;

@Service
public class FriendService {

    private final FriendRepository friendRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    public void addFriend(User fromUser, User toUser) {
        Friend friend = new Friend();
        friend.setFromUser(fromUser);
        friend.setToUser(toUser);
        friend.setAreWeFriend(1); // 친구 상태를 1로 설정 (1: 친구)

        friendRepository.save(friend);
    }

    public void removeFriend(User fromUser, User toUser) {
        Friend friend = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        if (friend != null) {
            friendRepository.delete(friend);
        }
    }
}
