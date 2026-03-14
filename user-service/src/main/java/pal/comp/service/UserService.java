package pal.comp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pal.comp.http.user.ResponseUserDto;
import pal.comp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<ResponseUserDto> allUsers() {
        var users = userRepository.findAll();
        return users.stream().map(userEntity -> new ResponseUserDto(userEntity.getId(), userEntity.getUsername(), userEntity.getPasswordHash(), userEntity.getAvatar_url())).toList();
    }
}
