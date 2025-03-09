package borikkori.community.api.config.swagger;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
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
                .title("borikkori api 문서")
                .description("borikkori 프로젝트 swagger api 문서 입니다.")
                .version("1.0.0");
    }

    /****
     * 헤더를 추가할 수 있는 텍스트 필드가 추가
     * 회원 가입과 로그인은 리프레시 토큰이 필요하지 않고, 액세스 토큰이 만료된 경우에만 리프레시 토큰을 전달
     * @return
     */
    @Bean
    public OperationCustomizer globalHeader(){
        return ((operation, handlerMethod) -> {
            operation.addParametersItem(new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .schema(new StringSchema().name("refresh-token"))
                    .name("refresh-token").required(false));
            return operation;
        });
    }

}
