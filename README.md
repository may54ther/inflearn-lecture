# Spring-Core-Basic
"김영한의 [스프링 핵심 원리 - 기본편](https://www.inflearn.com/course/스프링-핵심-원리-기본편)" 강의 소스코드   

---
## 컴포넌트 스캔
### 1. 컴포넌트 스캔과 의존관계 자동 주입
**1)```@Configuration```, ```@ComponentScan```**  
```@Component``` 가 붙은 모든 클래스를 스프링 빈으로 등록
- 기본 적으로 빈 이름은 **클래스 이름이 카멜케이스로 변환**되어 등록됨
- 그러나 스프링 빈의 이름을 직접 지정하고 싶으면 새로운 이름을 부여할 수 있음

**2)```@Autowired``` 의존관계 자동 주입**   
생성자에 ```@Autowired``` 를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입:  ```getBean()```과 동일

### 2. 컴포넌트 스캔 기본 대상
```Component``` ```Controller```, ```@Service```, ```@Repository```, ```@Configuration```

### 3. FilterType
```@ComponentScan```의 ```@Filter```에서 지정가능한 타입

- ```ANNOTATION```: 기본값, 애노테이션을 인식해서 동작한다.
- ```ASSIGNABLE_TYPE```: 지정한 타입과 자식 타입을 인식해서 동작한다.
- ```ASPECTJ```: AspectJ 패턴 사용
- ```REGEX```: 정규 표현식
- ```CUSTOM```: TypeFilter 이라는 인터페이스를 구현해서 처리

### 4. 중복 등록과 충돌
충돌 시 우선권은 ```수동 빈 > 자동 빈```   
단, **스프링 부트**에서는 수동 빈과 자동 빈의 충돌시 오류 발생(기본 값)


----
## 의존관계 자동 주입

### 1. 의존관계 주입 방법 (4가지)
- 생성자 주입 👍👍👍
- 수정자(Setter) 주입
- 필드 주입
- 일반 메서드 주입

### 2. 생성자 주입을 사용하자.
생성자 주입은 **불변**하다.
- 초기화 후 애플리케이션 종료시점까지 의존관계 변경 없음
- 외부에서 접근할 수 없음 (Setter는 public 하므로 접근 가능)
- ```final``` 키워드를 사용하여, 값 미설정 오류를 컴파일 시점에서 막아줌

### 3. Lombok 라이브러리
- ```@RequiredArgsConstructor```
  - ```final```이 붙은 필드를 모아 생성자를 자동으로 만들어주어 생성자 코드를 작성할 필요가 없음
  - 코드상에 직접 추가되진 않지만, 컴파일 후에 class 파일에 추가

### 4. 조회 하려는 빈이 중복일 때 해결방법 (3가지)
```@Autowired```는 **타입**으로 조회하므로, 타입이 동일한 2개 이상의 빈을 조회 시 충돌 오류 발생(```NoUniqueBeanDefinitionException```)   

- ```@Autowired``` 필드 명 매칭
- ```@Qualifier("추가 구분자")``` 
- ```@Primary``` 👍👍👍

### 5. 조회한 빈이 모두 필요할 때(다형성): List, Map
해당 타입의 스프링 빈이 다 필요한 경우 ```List```, ```Map```을 활용하여 스프링 빈을 직접 선택할 수 있다.

### 6. 자동, 수동의 올바른 실무 운영 기준
- **자동 빈을 기본**으로 사용하기! 또는 **업무 로직 빈**에서 사용
- **수동 빈**은 애플리케이션에 **광범위**하게 영향을 미치는 기술 지원 빈에서 사용
  수동 빈 등록 후, 설정 파일에 모아두자!

---
 ## 스프링 빈
IoC 컨테이너에 의해 관리되고 애플리케이션의 핵심을 이루는 객체

### 1. 스프링 빈 등록
#### 1) 수동 빈 등록
- ① 스프링 설정 파일(```XML```)
- ② ```@Configuration```, ```@Bean``` 
#### 2) 자동 빈 등록
- 설정 파일 : ```@Configuration```, ```@ComponentScan```
- 구현 클래스 : ```@Component```, ```@Autowired```

### 2. 생명 주기
```
객체 생성 → 의존관계 주입
```
- 생성자 주입은 제외

### 3. 이벤트 생명주기
```
컨테이너 생성 →  스프링 빈 생성 →  의존관계 주입 → 초기화 콜백 → 사용 →  소멸 전 콜백 → 스프링 종료
```
- 초기화 콜백: 빈 생성 후 의존관계 주입 완료시 호출
- 소멸전 콜백: 빈 소멸 직전 호출

> **📌객체의 생성과 초기화를 분리하자.**    
> - 생성자: 필수정보(파라미터) 받기, 메모리 할당 후 객체를 생성하는 **책임**
> - 초기화: 생성된 값들을 활용해 외부 커넥션을 연결하는 **무거운 동작을 수행**    
> ---
> 무거운 초기화 작업을 생성자 안에서 함께하는 것 보단 **생성**과 **초기화**를 명확하게 나누는 것이 유지보수 관점에서 좋음!


### 4. 스프링에서의 빈 생명주기 콜백 지원 (3가지)
#### 1) 인터페이스(InitializingBean, DisposableBean) 👎
- 스프링 전용 인터페이스 (의존)
- 초기화, 소멸 메서드의 이름 변경 불가
- 외부 라이브러리에 적용 불가
#### 2) 설정 정보에 초기화 메서드, 종료 메서드 지정 👍👍
- ```@Bean(initMethod = "초기화 메서드", destroyMethod = "종료 메서드")```
- ```@Bean```의 ```destroyMethod```속성에는 추론 기능이 있어서 값 생략 시 ```close```, ```shutdown``` 이름의 메서드를 자동으로 호출함
- **외부 라이브러리**에서 활용하기 좋은 방법
#### 3) ```@PostConstruct```, ```@PreDestroy``` 애노테이션 지원 👍👍👍
- ```javax.*```으로 자바 표준, 스프링 외에도 동작하는 **권장** 사용법




---
## 메모

### Live Template
>```Cmd + J``` 로 등록된 Live Template 목록을 확인 가능

- ```psvm``` → ```public static void main```
- ```sout``` → ```System.out.println```
- ```soutv``` → ```System.out.println("object = " + object)```

### Shortcuts
> Mac 전용 단축키  

- ```Cmd + ,```: 설정 (Preferences)
- ```Cmd + ;```: 프로젝트 설정 (Project Structure)   
<br>
- ```Cmd + 1```: Project 열기
- ```Opt + F12```: Terminal 열기   
<br>
- ```Shift + Shift```: 전체 검색 (Search Everywhere)
- ```Cmd + E```: 최근에 사용한 파일 (Recent Files)
- ```Cmd + Shift + E```: Recent Locations   
<br>
- ```Ctrl + ↑↓```: 이전/다음 메서드 이동
- ```Cmd +Opt + ←→```: 이전/다음 탭 이동   
<br>
- ```Ctrl + Space```: 자동완성 (Basic code completion)
- ```Ctrl + Shift + Space```: 스마트 자동완성 (Smart code completion)   
<br>
- ```Opt + Enter```: 빠른 수정 제안 (Show intention actions and quick-fixes)
- ```Cmd + N```: 자동 완성 (Generate)
- ```Ctrl + I```: 오버라이드 메서드 자동완성 (Implement Methods)
- ```Cmd + Opt + V```: 변수 추출(Extract Variables)
- ```Cmd + Shift + T ```: 테스트 클래스 생성   
<br>
- ```Shift + F6```: 이름 일괄 변경하기 (Rename)
- ```Cmd + Shift + F6```: 타입 일괄 변경하기 (Type Migration)

