package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan
@SpringBootApplication
public class ServletApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletApplication.class, args);
    }

    /**
     * [MEMO] SpringBoot의 ViewResolver: InternalResourceViewResolver
     * application.properties -> spring.mvc.view.prefix, spring.mvc.view.suffix 추가
     *
     * @Bean
     * ViewResolver InternalResourceViewResolver() {
     *  return new InternalResourceViewResolver("/WEB-INF/views/"".jsp");
     * }
     * */
}
