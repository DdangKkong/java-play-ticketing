package zerobase18.playticketing.auth.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zerobase18.playticketing.auth.security.AuthenticationFilter;
import zerobase18.playticketing.auth.security.JwtAccessDeniedHandler;
import zerobase18.playticketing.auth.security.JwtAuthenticationEntryPoint;
import zerobase18.playticketing.auth.security.JwtExceptionFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor

public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint customAuthenticationEntryPoint;



    private static final String[] AUTH = {
            "/customer/signup", "/customer/signin",
            "/company/signup", "/company/signup"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF, CORS 설정
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());

        // 세션 관리 설정
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));

        // 권한 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(AUTH).permitAll()
                // .requestMatchers("/theaters/**").hasRole("COMPANY")
                .requestMatchers("/answer").hasRole("ADMIN")
                .anyRequest().permitAll())
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler));

        // AuthenticationFilter 추가
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionFilter, AuthenticationFilter.class);

        return http.build();
    }
}


