package net.skhu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.entity.Friend;
import net.skhu.entity.User;
import net.skhu.repository.FriendRepository;
import net.skhu.repository.UserRepository;
import net.skhu.request.FriendRequest;

@RestController
public class FriendController {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendController(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }
//'친구 닉네임'으로 검색해서 '수락'을 하면
    @PostMapping("/friend/add")
    public ResponseEntity<String> addFriend(@RequestBody FriendRequest friendRequest) {
        User fromUser = userRepository.findById(friendRequest.getFromUserId()).orElse(null);
        User toUser = userRepository.findByNickname(friendRequest.getNickname()).orElse(null);

        if (fromUser == null || toUser == null) {
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }

        Friend existingFriend = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        if (existingFriend != null) {
            return ResponseEntity.badRequest().body("이미 친구로 등록된 사용자입니다.");
        }

        Friend newFriendRequest = new Friend();
        newFriendRequest.setFromUserId(fromUser.getId());
        newFriendRequest.setToUserId(toUser.getId());
        newFriendRequest.setAreWeFriend(0);
 
        friendRepository.save(newFriendRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body("친구 요청이 전송되었습니다.");
    }




    @PostMapping("/friend/accept") // 친구 요청 수락
    public ResponseEntity<String> acceptFriend(@RequestBody FriendRequest friendRequest) {
        User fromUser = userRepository.findById(friendRequest.getFromUserId()).orElse(null);
        User toUser = userRepository.findById(friendRequest.getToUserId()).orElse(null);

        if (fromUser == null || toUser == null) {
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }

        Friend friend = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        if (friend == null || friend.getAreWeFriend() != 0) {
            return ResponseEntity.badRequest().body("친구 요청을 찾을 수 없습니다.");
        }

        friend.setAreWeFriend(1); // 친구 요청 수락 상태로 변경
        friendRepository.save(friend);

        return ResponseEntity.ok("친구 요청을 수락하였습니다.");
    }


    @DeleteMapping("/friend/delete") //친구삭제
    public ResponseEntity<String> deleteFriend(@RequestBody FriendRequest friendRequest) {
        User fromUser = userRepository.findById(friendRequest.getFromUserId()).orElse(null);
        User toUser = userRepository.findById(friendRequest.getToUserId()).orElse(null);

        if (fromUser == null || toUser == null) {
            return ResponseEntity.badRequest().body("사용자를 찾을 수 없습니다.");
        }

        Friend friend = friendRepository.findByFromUserAndToUser(fromUser, toUser);
        if (friend == null) {
            return ResponseEntity.badRequest().body("친구로 등록되어 있지 않은 사용자입니다.");
        }

        friendRepository.delete(friend);

        return ResponseEntity.ok("친구 등록이 해제되었습니다.");
    }
}
