package justkode.starter.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import justkode.starter.domain.user.Authority;
import justkode.starter.domain.user.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserResponseDto {
    @ApiModel(value = "유저 프로필 정보")
    @Builder
    @Getter @Setter
    @AllArgsConstructor
    public static class Profile {
        @ApiModelProperty(value = "유저 아이디")
        final private String userId;

        @ApiModelProperty(value = "별명")
        final private String nickname;

        @ApiModelProperty(value = "이메일")
        final private String email;

        @ApiModelProperty(value = "유저가 가지고 있는 권한 리스트")
        final private Collection<? extends GrantedAuthority> authorities;

        public static Profile of(User user) {
            return Profile.builder()
                    .userId(user.getUserId())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .authorities(user.getAuthorities())
                    .build();
        }
    }
}
