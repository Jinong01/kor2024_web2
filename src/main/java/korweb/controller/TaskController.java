package korweb.controller;


import korweb.service.TaskService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

//public class TaskController {
//     (1) 인스턴스 생성하여 메소드를 호출한다.
//     public void method1(String[] args) {
//        TaskService taskService = new TaskService();
//        taskService.method1();
//    }
//
//    // (2)  이경우는 TaskService 재사용 불가능
//    public void method1(){
//        new TaskService().method1();
//    }
//
//     (3) 싱글톤
//    public void method1(){
//        TaskService.getInstance().method1();
//    }
//
//    // (4) 메소드 자체가 static
//    public void method1(){
//        TaskService.method2();
//    }
//
//    // (5) spring
//    @Autowired private TaskService taskService;
//    public void method2(){taskService.method1();}
//}

// =============AOP 없이 구현된 코드=================//
class TaskService2{
    // 메소드1
    public void enter1() {
        System.out.println("(보안) 코로나 열체크"); // (1) 부가기능
        System.out.println("식당 입장");        //  (2) 비즈니스 로직 -> 서비스를 제공함에 있어서 핵심이 되는 로직
    }

    // 메소드2
    public void enter2(){
        System.out.println("(보안) 코로나 열체크"); // (1) 부가기능
        System.out.println("학원입장");         // (2) 비즈니스 로직

    }}

public class TaskController {
    public static void main(String[] args) {
        TaskService2 taskService2 = new TaskService2();
        taskService2.enter1();
        taskService2.enter2();
        // 단점 : 유지보수가 복잡하다. 하나의 메소드에서 부가기능이 변경되면 다른 메소드도 같이 수정해야한다.
        // 예) 국가에서 식당과 학원에 입장하기 위해 보안을 열 체크 와 자가진단 -> 부가기능 별로 따로 분해해서 관리하자. AOP
    }
}

// =============AOP 활용해 구현된 코드=================//
@SpringBootApplication
class TaskStart3{
    public static void main(String[] args) {
        SpringApplication.run(TaskStart3.class);
    }
}

@RestController
class TaskController3{
    @Autowired TaskService3 taskService3;
    public void aop(){
        taskService3.enter1();
    }
}

@Service
class TaskService3{
    public void enter1() {
        //System.out.println("(보안) 코로나 열체크"); // (1) 부가기능
        System.out.println("식당 입장");        //  (2) 비즈니스 로직 -> 서비스를 제공함에 있어서 핵심이 되는 로직
    }

    public void enter2(){
        //System.out.println("(보안) 코로나 열체크"); // (1) 부가기능
        System.out.println("학원입장");         // (2) 비즈니스 로직

    }
}

@Aspect // AOP 설정하는 클래스 뜻
@Component // 빈 등록
class TaskSecurity{
    @Before("execution(* TaskService3.*(..))")
    // 즉] TaskService3 클래스의 모든 메소드는 실행하기전에 아래 securityCheck 함수가 *자동*으로 실행된다.
    public void securityCheck(){
        System.out.println("(보안) 코로나 열체크");
    }
}