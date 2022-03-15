package justkode.starter.domain.user.dto;

import justkode.starter.domain.user.Authority;
import justkode.starter.domain.user.User;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserResponseDto {
    @Builder
    @Getter @Setter
    @AllArgsConstructor
    public static class Profile {
        final private String userId;
        final private String nickname;
        final private String email;
        final private Set<Authority> authorities;

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
