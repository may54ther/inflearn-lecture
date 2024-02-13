package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {
    /**
     * MEMO: requestParamV1
     * 반환 타입 void, 응답에 값을 직접 넣음: view 조회X
     * */
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * MEMO: requestParamV2
     * @Controller 클래스 내 메서드의 반환 타입 String 인 경우에 view 조회O
     * - 1) 클래스: @Controller =>  @RestController 변경
     * - 2) 메서드: @ResponseBody 추가
     * @ResponseBody 추가
     * - view 조회X, HTTP Message Body 에 직접 내용 입력
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") String memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * MEMO: requestParamV3
     * @RequestParam 사용
     * - HTTP 파라미터 이름, 변수 이름이 동일하면 @RequestParam(name="xx") 생략O
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * MEMO: requestParamV4
     * @RequestParam 사용
     * - String, int 등의 단순 타입이면 @RequestParam 생략O
     * */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * MEMO: @RequestParam.required
     * `/request-param`
     * - 필수 파라미터인 username 반환 값이 null -> 500 에러 발생
     * - int 형식은 null 입력 X -> age 형식을 Integer 로 지정 (또는 @RequestParam.defaultValue 사용)
     * `/request-param?username=`
     * - 빈문자("")로 인식되어 통과("", null 차이)
     * */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username,
                                       @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * MEMO: @RequestParam.defaultValue
     * 파라미터의 기본값을 설정, 빈 문자열("")도 기본값으로 치환됨
     * */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                      @RequestParam(required = false, defaultValue = "-1") Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * MEMO: @RequestParam Map, MultiValueMap
     * Map(key=value)
     * MultiValueMap(key=[value1, value2, ...]
     * - 예) username=[kim, lee, park], age=[21]
     * */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * MEMO: modelAttributeV1
     * @ModelAttribute 사용
     * - model.addAttribute(helloData) 코드도 함께 자동 적용, 뒤에 model 설명할 때 자세히 설명
     * */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * MEMO: modelAttributeV2
     * @ModelAttribute 생략 가능
     * => @RequestParam 역시 생략 가능하므로 헷갈리지 않게 아래와 같이 규칙 적용
     * - String, int 같은 단순 타입 = @RequestParam
     * - 나머지 = @ModelAttribute (argument resolver 로 지정해둔 타입 외)
     * */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
