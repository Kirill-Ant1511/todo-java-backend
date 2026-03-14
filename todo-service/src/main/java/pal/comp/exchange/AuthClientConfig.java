package pal.comp.exchange;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.rmi.UnexpectedException;

@Configuration
public class AuthClientConfig {
    @Value("${auth-service.base-url}")
    private String authServiceBaseUrl;

    @Bean
    public AuthClient authClient() {
        // Создаём базовый RestClient с URL другого микросервиса
        RestClient restClient = RestClient.builder()
                .baseUrl(authServiceBaseUrl)
                .requestInterceptor(new TokenInterceptor())
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
                    System.out.println("Auth Exception on Todo service");
                    if (response.getStatusCode() == HttpStatus.FORBIDDEN) {
                        throw new AccessDeniedException("Forbidden exception");
                    } else if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                        throw new AuthenticationException("User not found or token expired");
                    }
                    throw new AuthenticationException("Client error: " + response.getStatusCode());
                })
                .build();

        // Создаём адаптер и фабрику для генерации прокси
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return factory.createClient(AuthClient.class);
    }
}
