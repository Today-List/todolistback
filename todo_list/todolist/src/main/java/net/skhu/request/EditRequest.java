package net.skhu.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EditRequest { // 회원정보 수정 요청
    private String nickname;

}
