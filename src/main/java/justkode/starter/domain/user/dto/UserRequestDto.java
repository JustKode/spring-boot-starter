package justkode.starter.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


public class UserRequestDto {

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class Login {
        final private String userId;
        final private String password;
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class Register {
        final private String userId;
        final private String password;
        final private String name;
        final private String nickname;
        final private String email;
    }
}
