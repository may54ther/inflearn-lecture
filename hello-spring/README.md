# Hello-Spring
"김영한의 [스프링 입문](https://www.inflearn.com/course/스프링-입문-스프링부트)" 강의 소스코드

---
## 스프링 웹 개발 기초
### 1. 정적 HTML
모든 사용자에게 동일한 정보를 제공하는 정적(static) 콘텐츠
- 정적 HTML 파일은 ```/resources/static/```에 생성

### 2. 템플릿 엔진
요청(Request)에 대한 처리 결과를 포함하여, 사용자 별로 다른 결과를 제공하기 위해 ```템플릿 엔진(Template Engine)```을 사용하여 동적 컨텐츠를 생성할 수 있다.
- 템플릿 HTML 파일은 ```/resources/template/```에 생성

#### Thymeleaf
동적 컨텐츠를 생성하는 템플릿 엔진 종류 중 하나이다.
- **HTML**(View)상단에 ```<html lang="ko" xmlns:th="http://www.thymeleaf.org">```를 추가하여 ```Thymeleaf``` 사용을 알림
- **Controller**임을 명시하기 위해 ```@Controller``` 어노테이션 추가
- **Controller**에서 리턴 값으로 반환한 문자를 통해 **viewResolver**가 **View**를 찾아서 처리

> - **Controller**
> ```
> // HelloController.java
> 
> @Controller
> public class HelloController {
> 
>     @GetMapping("/hello") 
>     public String hello(Model model) {
>     
>         model.addAttribute("name", "ahakim");
>         return "hello"; 
>     }
> }
>```
>- ```@GetMapping()```: 사용자의 요청 URL 지정
>- ```model.addAttribute()``` : **View**에 전달할 데이터 추가
>- 리턴 값 "hello"을 통해 **viewResolver** 가 ```/resources/template/hello.html```을 찾음
>---
> - **View**
> ```
> //hello.html 
> 
> <!DOCTYPE html>
>     <html lang="en" xmlns:th="http://www.thymeleaf.org">
>     <head>
>         <meta charset="UTF-8">
>         <title>Title</title>
>     </head>
>     <body>
>         <h1 th:text="${name}">Name</h1>
>     </body>
> </html>
> ```
> - ```th:text="${name}"``` : **Controller**에서 받은 데이터 사용
>
### 3. @ResponseBody
```@ResponseBody``` 어노테이션을 사용하는 경우 ```viewResolver``` 대신 ```HttpMessageConverter```를 통해 ```HTTP Body```에 문자 내용을 직접 반환한다.
- ```StringHttpMessageㅍ```: 문자열
- ```MappingJackson2HttpMessageConverter```: 객체 → JSON

> - **문자열 반환**
> ```
> @GetMapping("hello-string")
> @ResponseBody
> public String helloString(@RequestParam(value = "name", required = false) String name, Model model){
>     model.addAttribute("name", name);
>     return "hello "+name; 
> }
> ```
> ---
> - **객체**  
    > JSON 형태로 반환
> ```
> @GetMapping("hello-api")
> @ResponseBody
> public Hello helloApi(@RequestParam("name") String name, Model model){
>     Hello hello = new Hello();
>     hello.setName(name);
>     return hello;
> }
> 
> static class Hello{
>     private String name;
> 
>     public String getName() { return name; }
>     public void setName(String name) { this.name = name; }
> }
> ```

---

## 스프링 빈과 의존관계
### 1. 스프링 빈(Spring Bean)
스프링 컨테이너(Spring Container)가 관리하는 자바 객체
- 일반적으로 ```new``` 키워드를 통해 인스턴스화하는 객체와 다르게, 컨테이너의 관리를 통해 객체를 여러번 생성할 필요 없이 공용으로 사용 가능

### 2. 스프링 빈과 의존관계
#### 1) 컴포넌트 스캔과 자동 의존관계 설정
```@Component``` 애노테이션이 있으면 스프링 빈으로 **자동 등록**된다.
- ```@Component```를 포함하는 ```@Service```, ```@Repository```, ```@Controller```도 자동 등록
```
@Controller
public class MemberController  { ... }

@Service
public class MemberService  { ... }

@Repository //interface의 구현체에만 어노테이션 추가
public interface MemoryMemberRepository implements MemberRepository { ... }
```
> #### **@Autowired**
> 스프링이 관리하는 객체, 스프링 빈으로 등록한 객체만 동작한다.
>
> ---
> - **필드(field)**
> ```
> @Controller
> public class MemberController {
>   @Autowired private final MemberService memberService;
> }
> ``` 
> - **수정자 (setter)**
> ``` 
> @Controller
> public class MemberController {
>   private final MemberService memberService;
>   
>   @Autowired
>   public void setMemberService(MemberService memberService){
>     this.memberService = memberService;
>   }
> }
> ```
> - **생성자(Constructor)**    
    > 객체가 생성되는 시점에 빈을 주입하는 방식으로, 의존성이 주입되지 않아 발생할 수 있는 오류(NPE,```NullPointerException```) 방지
> ``` 
> @Controller
> public class MemberController {
>   private final MemberService memberService;
>   
>   @Autowired
>   public MemberController(MemberService memberService) {
>     this.memberService = memberService;
>   }
> }
>```

#### 2) 자바 코드로 직접 빈 등록
설정 파일을 생성하여 **직접(수동)** 등록
- ```@Configuration```, ```@Bean```
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
## 스프링 DB 접근 기술

### 1. H2 연결 설정
해당 강의에서는 [H2](https://www.h2database.com/html/main.html) 를 사용하여 **DB 접근 기술** 실습을 진행
>
> **build.gradle**
> ```
> dependencies {
>     ... 
>     runtimeOnly 'com.h2database:h2' 
> }
> ```
> ---
> **application.properties**
> ```
> spring.datasource.url=jdbc:h2:tcp://localhost/~/test
> spring.datasource.driver-class-name=org.h2.Driver
> spring.datasource.username=sa
> ```
### 2. 데이터베이스 접근 방법 (3가지)
> 📌**Persistence Framework 란?**
> - 데이터의 저장, 조회, 변경, 삭제를 다루는 클래스 및 설정 파일들의 집합
> - 복잡하고 번거로운 JDBC Programming과 다르게 빠르고 안정적인 구동 보장
> - SQL Mapper, ORM(Object-Relational Mapping)
#### 1) JDBC (Java Database Connectivity)
데이터베이스에 접속할 수 있도록 하는 Java API

> **build.gradle**
> ```
> // JDBC 연결 설정
> dependencies {
>     ...
>     implementation 'org.springframework.boot:spring-boot-starter-jdbc'
> }
> ```
#### 2) SQL Mapper
SQL 구문을 통해 데이터베이스에 질의한 결과를 객체(Object)에 매핑(Mapping)
- 예) Mybatis, JdbcTemplate 등
> 연결 설정은 JDBC와 동일
>
#### 3) ORM(Object-Relational Mapping; 객체-관계 매핑)
개별의 객체(Object)와 데이터베이스 사이를 중간에서 매핑(Mapping)
- SQL 구문의 작성없이 메서드, 코드를 이용하여 직관적인 데이터 조작 가능
    - 객체 간의 관계를 바탕으로 SQL 자동 생성
- 예) JPA, Hibernate 등
    - JPA: Hibernate를 interface 형태로 만든 Java Persistence API
>
>**build.gradle**
>```
>dependencies {
>    ...
>    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
>}
>```

---
## AOP(Aspect Oriented Programming; 관점 지향 프로그래밍)
```공통 관심 사항(cross-cutting concern)```과 ```핵심 관심 사항(core concern)```의 분리


---
## 메모
- ```Optional```: ```null```이 될 수도 있는 객체을 감싸고 있는 일종의 Wrapper Class 
    
