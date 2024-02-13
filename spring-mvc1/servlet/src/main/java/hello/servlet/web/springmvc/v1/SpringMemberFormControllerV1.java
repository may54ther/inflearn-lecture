package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * MEMO: V1
 *
 * @Controller = @Component + @RequestMapping
 *
 * @Component : 컴포넌트 스캔 대상으로 등록(스프링 빈으로 등록)
 * @RequestMapping : RequestMappingHandlerMapping 의 매핑 정보 인식을 위해서 클래스 레벨에 등록
 */
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }
}
