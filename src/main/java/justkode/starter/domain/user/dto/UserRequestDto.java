package justkode.starter.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import justkode.starter.domain.user.User;
import lombok.*;


public class UserRequestDto {

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {

        @ApiModelProperty(value = "유저 아이디")
        private String userId;

        @ApiModelProperty(value = "비밀 번호")
        private String password;
    }

    @ApiModel(value = "유저 회원 가입 정보")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @ApiModelProperty(value = "유저 아이디")
        private String userId;

        @ApiModelProperty(value = "별명")
        private String nickname;

        @ApiModelProperty(value = "이메일")
        private String email;

        @ApiModelProperty(value = "비밀 번호")
        private String password;

        @ApiModelProperty(value = "이름")
        private String name;
    }
}
