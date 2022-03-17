package justkode.starter.domain.user.service;

import justkode.starter.domain.user.User;
import justkode.starter.domain.user.dto.UserRequestDto;
import justkode.starter.domain.user.repository.UserRepository;
import justkode.starter.global.security.filter.JwtFilter;
import justkode.starter.global.security.provider.TokenProvider;
import justkode.starter.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    final private TokenProvider tokenProvider;
    final private AuthenticationManagerBuilder authenticationManagerBuilder;

    public String login(UserRequestDto.Login userDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDto.getUserId(), userDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        return jwt;
    }

    @Transactional
    public User register(UserRequestDto.Register user) {
        User userEntity = User.builder()
                .userId(user.getUserId())
                .password(passwordEncoder.encode(user.getPassword()))
                .name(user.getName())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .activated(true)
                .build();

        return userRepository.save(userEntity);
    }

    public boolean equalsPassword(String password, String encryptedPassword) {
        return passwordEncoder.matches(password, encryptedPassword);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(String userId) {
        return userRepository.findOneWithAuthoritiesByUserId(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.")
        );
    }

    @Transactional(readOnly = true)
    public User getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUserId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다.")
        );
    }
}
