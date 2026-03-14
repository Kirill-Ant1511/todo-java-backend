package pal.comp.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pal.comp.http.user.ResponseUserDto;
import pal.comp.entity.UserEntity;
import pal.comp.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping("/me")
    public ResponseEntity<ResponseUserDto> authUser() {
        log.info("authUser");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        var currentUser = (UserEntity) authentication.getPrincipal();
        if(currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(new ResponseUserDto(currentUser.getId(), currentUser.getUsername(), currentUser.getPasswordHash(), currentUser.getAvatar_url()));
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getUser() {
        var users = service.allUsers();
        return ResponseEntity.ok(users);
    }

}
