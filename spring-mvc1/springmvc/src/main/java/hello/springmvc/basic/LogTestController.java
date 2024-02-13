package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** MEMO
 *
 *  1. @Controller, @RestController
 *  @Controller : 반환 값이 String -> View 이름으로 인식하여 View 검색 후 랜더링
 *  @RestController : 반환 값이 String -> HTTP Message Body 에 바로 입력
 *  2. Lombok
 *  @Slf4j -> private final Logger log = LoggerFactory.getLogger(getClass()); 생략 가능
 * */
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info (" info log={}", name);
        log.warn (" warn log={}", name);
        log.error("error log={}", name);

        //사용X -> 표시할 로그 내용이 없어도 더하기(+) 연산이 실행
        log.debug("String concat log=" + name);

        return "ok";
    }
}
