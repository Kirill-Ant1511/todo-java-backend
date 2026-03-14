package pal.comp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pal.comp.dto.LoginResponseDto;
import pal.comp.dto.LoginUserDto;
import pal.comp.dto.RegisterUserDto;
import pal.comp.http.user.ResponseUserDto;
import pal.comp.service.AuthenticationService;
import pal.comp.service.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService service;

    @PostMapping("/signup")
    public ResponseEntity<ResponseUserDto> register(@RequestBody RegisterUserDto requestBody) {
        var authenticatedUser = service.register(requestBody);
        return ResponseEntity.ok(authenticatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginUserDto requestBody) {
        var authenticatedUser = service.login(requestBody);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        var loginResponseDto = new LoginResponseDto(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponseDto);
    }
}
