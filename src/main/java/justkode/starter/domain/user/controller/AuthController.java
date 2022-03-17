package justkode.starter.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import justkode.starter.domain.user.dto.UserRequestDto;
import justkode.starter.domain.user.dto.UserResponseDto;
import justkode.starter.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"Auth Controller"})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    final private UserService userService;

    @ApiOperation(value = "회원가입 API 입니다.")
    @PostMapping("/register")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponseDto.Profile register(@ApiParam(value="회원 정보", required = true) @RequestBody final UserRequestDto.Register userDto) {
        return UserResponseDto.Profile.of(userService.register(userDto));
    }

    @ApiOperation(value = "로그인 API 입니다.")
    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.CREATED)
    public UserResponseDto.Token login(@ApiParam(value="회원 정보", required = true) @RequestBody final UserRequestDto.Login userDto) {
        return new UserResponseDto.Token(userService.login(userDto));
    }
}
