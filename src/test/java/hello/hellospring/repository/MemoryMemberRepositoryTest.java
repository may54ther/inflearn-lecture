package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemberRepository repository = new MemoryMemberRepository();

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        Member result = repository.findById(member.getId()).get();
        // junit : Assertions.assertEquals(result, member);
        // assertj : Assertions.assertThat(member).isEqualTo(result);
        // Assertions 부분은 static 으로 import 해서 사용하는 경우 생략 가능!
        assertThat(member).isEqualTo(result);
    }
}
