package pal.comp.exchange;


import org.springframework.http.ResponseEntity;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import pal.comp.http.user.ResponseUserDto;

@HttpExchange("/users")
public interface AuthClient {

    @GetExchange("/me")
    ResponseEntity<ResponseUserDto> authUser();
}
