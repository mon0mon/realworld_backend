package kr.neoventureholdings.realword_backend.config.security;

import java.util.Arrays;
import java.util.List;
import kr.neoventureholdings.realword_backend.config.security.authentication.CustomAuthenticationProvider;
import kr.neoventureholdings.realword_backend.config.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final CustomAuthenticationProvider authProvider;

  @Bean
  public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(
        AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.authenticationProvider(authProvider);
    return authenticationManagerBuilder.build();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity,
      CustomAuthorizationFilter customAuthorizationFilter) throws Exception {
    httpSecurity.authorizeHttpRequests(authz ->
        authz
            .requestMatchers("/*").permitAll()
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/users/**").permitAll()
            .requestMatchers("/profiles/*").permitAll()
            .requestMatchers("/articles").permitAll()
            .requestMatchers("/articles/*").permitAll()
            .requestMatchers("/tags").permitAll()
            .requestMatchers("/articles/*/comments").permitAll()
            .anyRequest().authenticated());

    httpSecurity.csrf(AbstractHttpConfigurer::disable);

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

    //  CORS 설정 추가
    httpSecurity.cors(Customizer.withDefaults());

    //  인증 필터 추가
    httpSecurity.addFilterBefore(customAuthorizationFilter,
        UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:8080"));
    configuration.setAllowedMethods(
        Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "PATCH", "OPTIONS"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
