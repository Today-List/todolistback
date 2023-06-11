package net.skhu.dto;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.skhu.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    // 회원가입 요청 DTO


    private String email; // 이메일

    private String password; // 비밀번호

    private String nickname; // 닉네임

    public User toEntity() {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setNickname(nickname);
        return user;
    }

    public static UserDto convertToDto(Optional<User> user) {
        if(user.isPresent()){
            User u = user.get();
            UserDto userDto = new UserDto();
            userDto.setNickname(u.getNickname());
            return userDto;
        }

        return null;
    }


}
