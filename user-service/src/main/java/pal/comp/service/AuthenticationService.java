package pal.comp.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pal.comp.dto.LoginUserDto;
import pal.comp.dto.RegisterUserDto;
import pal.comp.http.user.ResponseUserDto;
import pal.comp.entity.UserEntity;
import pal.comp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public ResponseUserDto register(RegisterUserDto userDto) {
        var user = new UserEntity();
        user.setUsername(userDto.username());
        user.setPasswordHash(passwordEncoder.encode(userDto.password()));
        var createdUser = userRepository.save(user);
        return new ResponseUserDto(createdUser.getId(), createdUser.getUsername(), createdUser.getPassword(), createdUser.getAvatar_url());
    }

    public UserEntity login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.username(),
                        loginUserDto.password()
                )
        );

        return userRepository.findByUsername(loginUserDto.username()).orElseThrow(
                () -> new EntityNotFoundException("Such user not exists")
        );
    }
}
