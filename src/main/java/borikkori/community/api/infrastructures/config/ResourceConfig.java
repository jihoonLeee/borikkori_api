package borikkori.community.api.infrastructures.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

//  Spring MVC의 정적 리소스 처리에 대한 커스텀 설정
// 로컬 Storage접근해서 이미지 가져오기 위해서 -> 나중에 S3로 옮길거임
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry){
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///D:/upload/images/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES)); // 캐시 저장 시간
    }

}
