package wagwagt.community.api.infrastructures.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }
    private Info apiInfo(){
        return new Info()
                .title("WAGWAGT api 문서")
                .description("WAGWAGT 프로젝트 swagger api 문서 입니다.")
                .version("1.0.0");
    }

}
