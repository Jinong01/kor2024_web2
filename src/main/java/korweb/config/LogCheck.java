package korweb.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogCheck {
    // + StudentService 클래스내 모든 메소드가 실행 되기전에 자동으로 부가기능 실행
    @Before("execution(* korweb.service.StudentService.*(..))") // 경로는 java 이후
    public void Check(){
        System.out.println("StudentService 발동");
    }
}
