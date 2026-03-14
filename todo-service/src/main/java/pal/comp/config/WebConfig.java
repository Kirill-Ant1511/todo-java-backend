package pal.comp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.handler.MappedInterceptor;
import pal.comp.exchange.AuthInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    @Bean
    public MappedInterceptor authInterceptor(AuthInterceptor interceptor) {
        return new MappedInterceptor(new String[]{"/**"}, interceptor);
    }
}
