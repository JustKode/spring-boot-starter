package justkode.starter.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import justkode.starter.domain.user.User;
import justkode.starter.domain.user.dto.UserResponseDto;
import justkode.starter.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Api(tags = {"User Controller"})
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    final private UserService userService;

    @ApiOperation(value = "userId를 가지고 있는 유저 정보를 반환 합니다.")
    @ApiImplicitParam(name = "userId", value = "조회할 유저 Id", required = true, dataType = "String")
    @GetMapping("/profile/{userId}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserResponseDto.Profile profile(@PathVariable final String userId) {
        return UserResponseDto.Profile.of(userService.getUserWithAuthorities(userId));
    }
}
