package kitae.spring.library.config;

import kitae.spring.library.entity.Book;
import kitae.spring.library.entity.Message;
import kitae.spring.library.entity.Review;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private String theAllowedOrigins = "http://localhost:3000";

    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry corsRegistry) {
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};    // 지원하지 않는 메소드 배열
        config.exposeIdsFor(Book.class);    // Book 엔티티의 ID 노출
        config.exposeIdsFor(Review.class);  // Review 엔티티의 ID 노출
        config.exposeIdsFor(Message.class); // Message 엔티티의 ID 노출

        disableHttpMethods(Book.class, config, theUnsupportedActions);  // 지원하지 않는 메소드 비활성화
        disableHttpMethods(Review.class, config, theUnsupportedActions);  // 지원하지 않는 메소드 비활성화
        disableHttpMethods(Message.class, config, theUnsupportedActions); // 지원하지 않는 메소드 비활성화

        corsRegistry.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);    // CORS 설정
    }

    private void disableHttpMethods(Class<?> theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()       // 엔티티에 대한 구성 가져오기
                .forDomainType(theClass)       // 엔티티 클래스 지정
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))         // 단일 지원하지 않는 메소드 비활성화
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));  // 컬랙션 지원하지 않는 메소드 비활성화
    }
}
