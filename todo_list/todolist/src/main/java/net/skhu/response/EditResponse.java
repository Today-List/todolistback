package net.skhu.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.skhu.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditResponse {

    private String email;
    private String nickname;
    private String message;

    public EditResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();

    }

    public EditResponse(String message) {
        this.message = message;
    }

}
