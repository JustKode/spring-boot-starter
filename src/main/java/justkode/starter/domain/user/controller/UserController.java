package justkode.starter.domain.user.controller;

import justkode.starter.domain.user.dto.UserResponseDto;
import justkode.starter.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @GetMapping("/profile/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto.Profile profile(String userId) {
        return UserResponseDto.Profile.of(userService.getUserWithAuthorities(userId));
    }
}
