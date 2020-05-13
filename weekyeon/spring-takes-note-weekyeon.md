# 예제로 배우는 스프링 입문(개정판)

* https://www.inflearn.com/course/spring_revised_edition



# 과제

* First Name으로 검색할 수 있도록 고쳐보기

* 키워드 검색 시, 정확히 일치하는 게 아니라 해당 키워드가 포함되어 있는 걸 찾아볼 것

* Owner에 age 추가
  * `/resources/db/mysql/schema.sql` 문서에서 DB 스키마 조작 가능

---

# Spring IoC

* Inversion of Control (IoC)
  
  * 제어권이 역전된 것
  * EX) 일반적으로 자신(객체)이 사용할 의존성은 자신이 만들어서 사용
  
  ```java
  class OwnerController{
      private OwnerRepository repo = new OwnerRepository();
  }
  ```
  
  * EX) IoC
  
  ```java
  class OwnerController {
      
      // repo를 사용은 하지만, 객체를 이곳에서 만들진 않는다.
      private OwnerRepository repo;
  
      //OwnerController 밖에서 누군가가 줄 수 있게끔
      //생성자를 통해 받아옴
      //즉, 의존성이 자신에게 있지 않고 외부에 있음 == IoC
  	public OwnerController(OwnerRepository repo){
          this.repo = repo;
      }
     
  }
  ```

---

### IoC Container

* 빈(Bean)을 만들고 빈 사이의 의존성을 엮어주고, 컨테이너가 가지고 있는 빈들을 제공해줌
* 단, 모든 클래스가 빈으로 등록되어 있는 건 아님
* 무엇을 보고 알 수 있는가?
  * class 왼쪽에 녹생콩이 있으면 등록되어 있는 것!
  * 어노테이션 @Bean 이용해서 만들 수 있음
* 오너 리파짓토리를 누가 넣어주냐! 스프링 IoC 컨테이너
  * 즉, 애플리케이션 컨텍스트가 해당 타입의 빈을 찾아서 넣어줌
* 의존성 주입은 빈끼리만 가능함
  * 즉, 스프링 IoC 컨테이너 안에 있는 빈들끼리만 의존성 주입이 가능함
* ApplicationContext 안에 모든 빈이 있음!

---

### Bean

* 스프링 IoC 컨테이너가 관리하는 객체

* 스프링 IoC 컨테이너가 관리하지 않는 객체는 빈 X

  * 즉, 의존성 주입 불가능!

* 어떻게 스프링 컨테이너 안에 빈을 만들어?

  * Component Scanning
    * @Component
      * @Repository
      * @Service
      * @Controller 
        * Component 라는 메타 어노테이션을 사용한 어노테이션(즉, 사실상 해당 어노테이션은 Component 어노테이션이라고 볼 수 있음)
      * @Configuration

  > 어노테이션 프로세서 중, 스프링 IoC 컨테이너가 사용하는 여러가지 인터페이스들이 있다. 그런 인터페이스들을 **라이프싸이클 콜백**이라고 부른다.
  >
  > 여러가지 라이프 콜백 중에는, 컴포넌트 어노테이션을 가진 모든 클래스를 찾고, 그 클래스의 인스턴스를 만들어서 빈으로 등록하는 어노테이션 프로세서(처리기)가 등록되어 있다.
  >
  > 이 처리기가 어디에 등록되어 있냐면, **@SpringBootApplication** 어노테이션 안에 **@ComponentScan**이라는 어노테이션이 있다. @ComponentScan 어노테이션이 어디부터 컴포넌트를 찾아보라고 알려준다.
  >
  > @SpringBootApplication 어노테이션이 붙은 클래스부터 모든 하위 클래스까지 스캔하여 Component 어노테이션이 붙어있는 모든 클래스를 찾는다.
  >
  > 이러한 과정으로 인해, 우리가 직접 빈으로 등록하지 않아도 스프링이 알아서 해준다.

  * 직접 일일이 XML이나 Java 설정 파일에 등록

  ```java
  //Java 설정 파일을 이용하여 Bean 직접 등록하는 방법
  
  @Configuration
  public class SampleConfig{
      
      @Bean
      public SampleController sampleController(){
        return new SampleController();  
      }
  }
  
  //이렇게 Bean으로 직접 등록하게 되면,
  //어노테이션을 일일이 붙이지 않아도 됨
  ```

  

* 어떻게 스프링 컨테이너 안에서 꺼내서 써?

  * @Autowired
  * @Inject
  * ApplicationContext에서 getBean()으로 직접 꺼내서 씀

---

### 의존성 주입 (Dependency Injection)

* @Autowired

  * @Autowired는 `1`생성자 `2`필드 `3`Setter에 붙일 수 있음

  > 스프링 버전 4.3부터
  >
  > **어떠한 클래스에 생성자가 하나뿐이고**
  >
  > **그 생성자로 주입 받는 레퍼런스 변수들이 빈으로 등록되어 있다면**
  >
  > **그 빈을 자동으로 주입해주는 기능이 있음.**
  >
  > **그래서 @Autowired를 생략할 수 있음**

  * 필드로 의존성 주입을 바로 받는 방법
  
```java
  @Autowired
  private OwnerRepository owners;
```

* Setter에 @Autowired 붙이는 방법
  
```java
  @Autowired
  public void setOwners(OwnerRepository owners){
      this.owners = owners;
  }
```

> 스프링 IoC 컨테이너가 OwnerController 인스턴스를 만든 후,
  >
  > Setter를 통해 스프링 IoC 컨테이너에 들어있는 빈 중에
  >
  > OwnerRepository 타입을 찾아서 넣어준다.

* 위 세 가지 방법 중 스프링 프레임워크 레퍼런스에서 권장하는 방법은 **생성자**
    * 생성자를 사용할 때의 장점
      * 필수적으로 사용해야 하는 레퍼런스 없이는 인스턴스를 만들지 못하도록 강제할 수 있음
      * EX) OwnerController는 OwnerRepository가 없으면 제대로 동작할 수 없는 클래스임. 즉, OwnerRepository가 반드시 필요함. 이때, @Autowired를 이용하여 강제할 수 있음.
    * 생성자를 사용할 때의 단점 : 순환참조 발생(A가 B를 참고, B가 A를 참조하고. 둘 다 생성자 인젝션 사용한다고 가정했을 때, 둘 다 못 만듦. 그래서 애플리케이션이 제대로 동작하지 않음. 이런 경우 필드나 세터 인젝션 사용.)

---

### 과제

* OwnerController에 PetRepository 주입하기

  ```java
  @Controller
  class OwnerController{
      
      //1. 필드 이용하여 주입
      //이 인스턴스를 무조건 여기에 만들어야 하므로 final 키워드 사용하면 안됨
      @Autowired
      private PetRepository petRepository;
      
      //2. 생성자 이용하여 주입
      //final을 붙이는 이유
      //한 번 받은 다음에 다른 레퍼런스로 바뀌지 않게끔 보장하기 위함
      private final PetRepository petRepository;
      public OwnerController(PetRepository petRepository){
          this.petRepository = petRepository;
      }
      
      //3.Setter 이용하여 주입
      //이 인스턴스를 무조건 여기에 만들어야 하므로 final 키워드 사용하면 안됨
      private  PetRepository petRepository;
      @Autowired
      public void setPetRepository(PetRepository petRepository){
          this.petRepository = petRepository;
      }
      
  }
  ```

---

# AOP

* 흩어진 코드를 한 곳으로 모아

  >사용하는 로직에서 여러 곳에 흩어지는 로직이 있다.
  >
  >같은 코드인데 여기도 들어가고, 저기도 들어가는 코드가 있다.
  >
  >그럴 때 스프링 AOP를 적용하는 것을 고려해보는 것도 좋다.

* @Transactional 어노테이션 : 스프링 AOP 기반으로 만들어져 있는 어노테이션

* AOP 구현 방법
  * 컴파일
    * A.java -----(AOP)-----> A.class
    * AspectJ가 제공
  * 바이트코드 조작
    * A.java ---> A.class -----(AOP)----->
    * 클래스 로더가 클래스를 읽어와서 메모리에 올릴 때 조작
    * AspectJ가 제공
  * 프록시 패턴
    * 스프링 AOP가 사용하는 방법
    * 디자인 패턴 중 하나를 사용해서 AOP와 같은 효과를 내는 방법
    * https://refactoring.guru/design-patterns/proxy
    * 프록시 패턴 만들기
  * AOP 적용 예제
    * @LogExecutionTime으로  메소드 처리 시간 로깅하기

---

# PSA

* Service Abstraction

* https://en.wikipedia.org/wiki/Service_abstraction

* 서블릿 애플리케이션을 만들고 있음에도, 서블릿을 전혀 쓰지 않는다.

  ```java
  public class OwnerCreateServelt extends HttpServlet{
      doGet ~
      doPost ~
  }
  ```

  * 스프링 PetClinic은 서블릿을 쓰고 있지 않다.
    * @GetMapping, @PostMapping 등 어노테이션 사용
    * 이러한 코드의 기반엔 서블릿 코드가 있다.

---

### 스프링 웹 MVC

* 스프링이 제공하는 스프링 웹 MVC 추상화 계층

```java
//Controller
@GetMapping("/owners/new")
public String initCreationForm(Map<String, Object> model){
    //Model
    Owner owner = new Owner();
    model.put("owner", owner);
    //View
    return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
}
```

* @Controller
  * 요청을 맵핑할 수 있는 컨트롤러 역할을 수행하는 클래스
  * 해당 클래스 안에 @GetMapping, @PostMapping 등으로 요청을 맵핑함
    * 맵핑한다 : url에 해당하는 요청이 들어왔을 때, 그 요청을 메소드가 처리하게끔 맵핑한다.

* 왜 추상화 계층이냐
  * 편의성을 위해!
    * 서블릿을 로우 레벨로 사용하지 않아도 됨(직접 쓰지 않아도 됨)

---

### 스프링 트랜잭션

* 데이터에 데이터를 넣을 때 A를 하고 B를 하고 C까지 해야 하나의 작업으로 완료되는 경우

* All or Nothing

* https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/transaction/PlatformTransactionManager.html

* JDBC에서 하는 가장 기본적인 트랜잭션 코드(로우 레벨)

  ```java
  try{
      //SQL이 여러번 날아가더라도 커밋을 하지 않겠다.
      //기다리겠다.
      dbConnection.setAutoCommit(false);
  
      ...실행문...
  
      //명시적으로 commit 하라고 명령
      //commit 하면 디비에 반영됨
      dbConnection.commit();
      
  } catch (SQLException e){
      
      //실행문에서 하나라도 오류가 나면 롤백을 하겠다.
      dbConnection.rollback();
      
  }
  ```

* 스프링이 제공해주는 추상화 레벨 : @Transactional
  * @Transactional이 붙어있는 메소드에는 트랜잭션 처리가 됨
    * 명시적인 코딩을 하지 않아도 됨

---

### 스프링 캐시

* CacheManager
* https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/cache/CacheManager.html
* @Cacheable

---

* 타임리프 엔진