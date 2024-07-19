package kitae.spring.library.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf 비활성화
        http.csrf(csrf -> csrf.disable());

        // /api/<type>/secure 경로에 대한 인증 처리
        http.authorizeRequests(authorize -> authorize
                .requestMatchers(
                        "/api/books/secure/**",
                        "/api/messages/secure/**",
                        "/api/admin/secure/**",
                        "/api/reviews/secure/**")
                .authenticated());

        //  JWT 토큰을 사용하여 OAuth2 리소스 서버 구성
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {}));

        // CORS 설정
        http.cors(cors -> {});

        // Content Negotiation 설정
        http.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // Okta의 401 응답 본문 구성
        Okta.configureResourceServer401ResponseBody(http);

        return http.build();
    }
}