package justkode.starter.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import justkode.starter.domain.user.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class UserRequestDto {

    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {

        @NotEmpty
        @ApiModelProperty(value = "유저 아이디", required = true)
        private String userId;

        @NotEmpty
        @ApiModelProperty(value = "비밀 번호", required = true)
        private String password;
    }

    @ApiModel(value = "유저 회원 가입 정보")
    @Builder
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Register {
        @NotEmpty
        @ApiModelProperty(value = "유저 아이디", required = true)
        private String userId;

        @NotEmpty
        @ApiModelProperty(value = "별명", required = true)
        private String nickname;

        @NotEmpty @Email
        @ApiModelProperty(value = "이메일", required = true)
        private String email;

        @NotEmpty
        @ApiModelProperty(value = "비밀 번호", required = true)
        private String password;

        @NotEmpty
        @ApiModelProperty(value = "이름", required = true)
        private String name;
    }
}
