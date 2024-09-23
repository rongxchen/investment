package rongxchen.investment.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rongxchen.investment.models.Response;
import rongxchen.investment.models.dto.LoginDTO;
import rongxchen.investment.models.dto.RegisterDTO;
import rongxchen.investment.services.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public Response<Object> login(@RequestBody @Valid LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    @PostMapping("/register")
    public Response<Boolean> register(@RequestBody @Valid RegisterDTO registerDTO) {
        return userService.register(registerDTO);
    }

    @PostMapping("/refresh-token")
    public Response<Object> refreshToken(@RequestHeader("refresh-token") String refreshToken) {
        String token = userService.refreshToken(refreshToken);
        if (token == null) {
            return Response.failed("invalid refresh token").body(null);
        }
        return Response.builder()
                .message("refresh token ok")
                .body(token);
    }

}
