package justkode.starter.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import justkode.starter.domain.user.dto.UserResponseDto;
import justkode.starter.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"My Profile Controller"})
@RestController
@RequestMapping("/api/my_profile")
@RequiredArgsConstructor
public class MyProfileController {
    final private UserService userService;

    @ApiOperation(value = "현재 로그인 중인 유저의 정보를 반환 합니다.")
    @GetMapping("/info")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto.Profile profile(@AuthenticationPrincipal User user) {
        justkode.starter.domain.user.User userWithAuthorities = userService.getUserWithAuthorities(user.getUsername());
        return UserResponseDto.Profile.of(userWithAuthorities);
    }
}
