package pal.comp.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import pal.comp.service.JwtService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Настройка фильтра авторизации
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Получаем строку Authorization с Header
        final String authorizationHeader = request.getHeader("Authorization");

        // Проверяем есть вообще такой Header
        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            doFilter(request, response, filterChain);
            return;
        }

        try {
            // Получение токена с header
            final String jwtToken = authorizationHeader.substring(7);
            // Получения username с токена
            final String username = jwtService.extractUsername(jwtToken);
            // Получение текущей авторизации
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            System.out.println(username + " " + jwtToken);

            // Проверяем не был ли уже авторизирован пользователь, если не был продолжаем выполнение,
            // иначе просто продолжаем выполнение запроса
            if(username != null && authentication == null) {

                // Получаем userDetails их базы данных
                // Реализация userDetailService происходит в файле ./ApplicationConfiguration.java
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Проверяем валидность токена
                System.out.println("Token is valid" + jwtService.isTokenValid(jwtToken, userDetails));
                if(jwtService.isTokenValid(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        }
    }
}
