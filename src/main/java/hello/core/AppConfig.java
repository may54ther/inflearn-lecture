package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    // AppConfig 리팩터링: 역할, 구현의 구분이 확실해짐.
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository());
    }
    private MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), DiscountPolicy());
    }
    private DiscountPolicy DiscountPolicy() {
        return new RateDiscountPolicy();
    }
}
