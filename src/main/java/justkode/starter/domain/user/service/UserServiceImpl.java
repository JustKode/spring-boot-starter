package justkode.starter.domain.user.service;

import justkode.starter.domain.user.User;
import justkode.starter.domain.user.dto.UserRequestDto;
import justkode.starter.domain.user.repository.UserRepository;
import justkode.starter.global.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<User> getUserWithAuthorities(String username) {
        return userRepository.findOneWithAuthoritiesByUsername(username);
    }

    @Transactional(readOnly = true)
    public Optional<User> getMyUserWithAuthorities() {
        return SecurityUtil.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByUsername);
    }
}
