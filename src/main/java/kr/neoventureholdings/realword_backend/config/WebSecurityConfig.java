package kr.neoventureholdings.realword_backend.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
  private static final String[] PERMIT_ALL_PATTERNS = new String[] {
      "/swagger-ui/**",
      "/h2-console",
      "/h2-console/**",
      "/users/*",
      "/users/**",
      "/**"
  };
  private final PasswordEncoder passwordEncoder;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(
        authHttp -> authHttp
            .requestMatchers( //인증 관련 정보만 추가
                Stream.of(PERMIT_ALL_PATTERNS)
                    .map(AntPathRequestMatcher::antMatcher)
                    .toArray(AntPathRequestMatcher[]::new)
            )
            .permitAll()
            .anyRequest()
            .authenticated()
    );

    httpSecurity.csrf(httpSecurityCsrfConfigurer ->
        httpSecurityCsrfConfigurer
            .ignoringRequestMatchers("/h2-console/**"));

    httpSecurity.headers(httpSecurityHeadersConfigurer ->
        httpSecurityHeadersConfigurer.frameOptions(
            FrameOptionsConfig::disable
        ));

    //  FormLogin 및 Logout 비활성화
    httpSecurity
        .formLogin(AbstractHttpConfigurer::disable)
        .logout(AbstractHttpConfigurer::disable)
    ;

    //  Session 유지 방식을 Stateless 방식으로 변경
    httpSecurity.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return httpSecurity.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(
        Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH", "OPTION"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
