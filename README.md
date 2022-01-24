# Hello-Spring

"김영한의 [스프링 입문](https://www.inflearn.com/course/스프링-입문-스프링부트)" 강의를 보며 생성한 레파지토리



# 스프링 웹 개발 기초
> ## 정적 HTML
모든 사용자에게 동일한 정보를 제공하는 정적(static) 콘텐츠
- 정적 HTML 파일은 ```/resources/static/```에 생성

> ## 템플릿 엔진 
요청(Request)에 대한 처리 결과를 포함하여, 사용자 별로 다른 결과를 제공하기 위해 ```템플릿 엔진(Template Engine)```을 사용하여 동적 컨텐츠를 생성할 수 있다.
- 템플릿 HTML 파일은 ```/resources/template/```에 생성


### Thymeleaf
템플릿 엔진의 종류 중에 하나로 이 레파지토리에서는 ```Thymeleaf``` 템플릿 엔진을 사용
- **HTML**(View)상단에 ```<html lang="ko" xmlns:th="http://www.thymeleaf.org">```를 추가하여 ```Thymeleaf``` 사용을 알림
- **Controller**임을 명시하기 위해 ```@Controller``` 어노테이션 추가  
- **Controller**에서 리턴 값으로 반환한 문자를 통해 **viewResolver**가 **View**를 찾아서 처리


#### Controller
```
// HelloController.java

@Controller
public class HelloController {

    @GetMapping("/hello") 
    public String hello(Model model) {
    
        model.addAttribute("name", "ahakim");
        return "hello"; 
    }
}
```
- ```@GetMapping()```: 사용자의 요청 URL 지정
- ```model.addAttribute()``` : **View**에 전달할 데이터 추가
- 리턴 값 "hello"을 통해 **viewResolver** 가 ```/resources/template/hello.html```을 찾음

#### View
```
//hello.html 

<!DOCTYPE html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <h1 th:text="${name}">Name</h1>
    </body>
</html>
```
- ```th:text="${name}"``` : **Controller**에서 받은 데이터 사용

>  ## @ResponseBody
```@ResponseBody``` 어노테이션을 사용하는 경우 ```viewResolver``` 대신 ```HttpMessageConterter```를 통해 ```HTTP Body```에 문자 내용을 직접 반환한다.
- ```StringHttpMessageConverter```: 문자열
- ```MappingJackson2HttpMessageConverter```: 객체 → JSON

### 문자열  
```
@GetMapping("hello-string")
@ResponseBody
public String helloString(@RequestParam(value = "name", required = false) String name, Model model){
    model.addAttribute("name", name);
    return "hello "+name; 
}
```
### 객체
JSON 형태로 반환
```
@GetMapping("hello-api")
@ResponseBody
public Hello helloApi(@RequestParam("name") String name, Model model){
    Hello hello = new Hello();
    hello.setName(name);
    return hello;
}

static class Hello{
    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

# 스프링 빈과 의존관계
> ## 스프링 빈(Spring Bean)
스프링 컨테이너(Spring Container)가 관리하는 자바 객체  
- 일반적으로 ```new``` 키워드를 통해 인스턴스화하는 객체와 다르게, 컨테이너의 관리를 통해 객체를 여러번 생성할 필요 없이 공용으로 사용 가능

> ## 의존관계 설정
### 1. 컴포넌트 스캔
```@Service```, ```@Repository```, ```@Controller``` 어노테이션이 있으면 스프링 빈에 자동 등록
```
@Controller
public class MemberController  { ... }

@Service
public class MemberService  { ... }

@Repository //interface의 구현체에만 어노테이션 추가
public interface MemoryMemberRepository implements MemberRepository { ... }
```
#### @Autowired
스프링이 관리하는 객체, 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않음

1. 필드(field)
```
@Controller
public class MemberController {
  @Autowired private final MemberService memberService;
}
``` 
2. setter
``` 
@Controller
public class MemberController {
  private final MemberService memberService;
  
  @Autowired
  public void setMemberService(MemberService memberService){
    this.memberService = memberService;
  }
}
```
3. 생성자(Constructor)     
   객체가 생성되는 시점에 빈을 주입    
   → 의존성이 주입되지 않아 발생할 수 있는 ```NullPointerException``` 방지
``` 
@Controller
public class MemberController {
  private final MemberService memberService;
  
  @Autowired
  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }
}
``` 
## 2. 자바 코드로 직접 스프링 빈 등록
- MemberService, MemoryMemberRepository 의 ```@Service```, ```@Repository```, ```@Autowired``` 어노테이션 제거 
- Configuration 파일을 생성하여 직접 등록
```
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}

```





---
## 메모
- **Optional** (Java 8)<br>
  ```null```이 될 수도 있는 객체을 감싸고 있는 일종의 Wrapper Class 
    