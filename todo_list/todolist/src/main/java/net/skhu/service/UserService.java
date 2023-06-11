package net.skhu.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.skhu.dto.UserDto;
import net.skhu.entity.User;
import net.skhu.exception.UserNotFoundException;
import net.skhu.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void save(UserDto userDto) {

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // 인코딩된 패스워드 저장
        user.setNickname(userDto.getNickname());


        userRepository.save(user);
    }


    public void save(User user) {
        userRepository.save(user);
    }


    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UserNotFoundException("유저를 찾을 수 없습니다. " + email);
    }



    public boolean checkDuplicateNickname(String username) {
        return userRepository.findByNickname(username).isPresent();
    }


    public void deleteUser(int userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        userRepository.delete(user.get());
    }


}
