package hello.core;

import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
/* 수동 빈 등록, 기존에 동일한 이름의 자동 빈이 존재하여 중복 상태
 *  - Spring: 수동 빈이 자동 빈을 오버라이딩
 *  - Spring boot: 오류 발생
 *  -----------------------------------------
 *    @Bean(name="memoryMemberRepository")
 *    public MemberRepository memberRepository(){
 *        return new MemoryMemberRepository();
 *    }
 */
}
