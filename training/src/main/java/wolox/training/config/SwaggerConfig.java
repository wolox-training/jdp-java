package wolox.training.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiEndPointsInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("wolox.training.controllers"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiEndPointsInfo() {

        Contact contact = new Contact("Juan Daniel Pérez Pulgarin", "https://github.com/wolox-training/jdp-java", "juan.perez@wolox.co");

        return new ApiInfoBuilder()
                .title("Training")
                .description("Training Juan Daniel")
                .version("1.0.0")
                .contact(contact).build();
    }

}
