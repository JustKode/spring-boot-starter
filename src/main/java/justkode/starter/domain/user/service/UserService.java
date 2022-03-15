package justkode.starter.domain.user.service;

import justkode.starter.domain.user.User;
import justkode.starter.domain.user.dto.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    void register(UserRequestDto.Register user);
    boolean equalsPassword(String password, String encryptedPassword);
    User getUserWithAuthorities(String username);
    User getMyUserWithAuthorities();
}
