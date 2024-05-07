package zerobase18.playticketing.auth.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.jwt.prefix}")
    private String tokenPrefix;

    @Value("${spring.jwt.header}")
    private String tokenHeader;




    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String token = resolvedTokenFromRequest(request);


        if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {
            String key = "JWT_TOKEN:" + tokenProvider.getCustomerPk(token);
            String storedToken = redisTemplate.opsForValue().get(key);



            if(Boolean.TRUE.equals(redisTemplate.hasKey(key)) && storedToken != null) {
                Authentication authentication = this.tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info(String.format("[%s] -> %s", this.tokenProvider.getUserName(token), request.getRequestURI()));

            }
        }
        filterChain.doFilter(request, response);


    }


    private String resolvedTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(this.tokenHeader);
        if (!ObjectUtils.isEmpty(token) && token.startsWith(this.tokenPrefix)) {
            return token.substring(this.tokenPrefix.length());
        }

        return null;
    }
}
