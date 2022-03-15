package justkode.starter.domain.user.service;

import justkode.starter.domain.user.User;
import justkode.starter.domain.user.dto.UserRequestDto;
import justkode.starter.domain.user.repository.UserRepository;
import justkode.starter.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(UserRequestDto.Register user) {

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
