package korweb.config;

import korweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링 컨테이너의 빈 등록
public class SecurityConfig {

    // [1] 시큐리티의 필터 정의하기
    @Bean // 필드 또는 메소드에 빈 등록하는 방법
    public SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {

        // [3] HTTP URL 요청에 따른 부여된 권한/상태 확인후 자원 허가 제한
        http.authorizeHttpRequests( ( httpReq ) -> {
            // 3-1 : 모든 HTTP 요청에 '/**'(모든url뜻) .antMatcher('http경로' ).permitAll() : 지정한 경로에는 누가나 접근할 수 있다.
            // 3-2 : 글쓰기페이지(board/write) 에는 로그인된 회원만 접근할 수 있다.
            // 3-3 : 채팅페이지(chat) 에는 로그인회원이면서 Role 이 USER 회원만 접근할 수 있다.
            // 3-4 : 관리자페이지(admni) 엔ㄴ 로로그인회원이면서 Role 이 admin 이거나 temp1 회원만 접근 할 수 있다.
            httpReq
                    .requestMatchers( AntPathRequestMatcher.antMatcher("/board/write") ).authenticated()
                    .requestMatchers( AntPathRequestMatcher.antMatcher("/chat") ).hasRole("USER")
                    .requestMatchers( AntPathRequestMatcher.antMatcher("/admin") ).hasAnyRole( "admin" , "team1" )
                    .requestMatchers(AntPathRequestMatcher.antMatcher("/**") ).permitAll();
        } );
        // [4] CSRF : post/put (BODY) 요청을 금지  , 특정한 URL만 post/put 가능하도록 수동 허용
        // 개발 : CSRF 사용안함 , 개발 환경에서는 끄고 사용하는 경우가 많다.
        http.csrf(AbstractHttpConfigurer :: disable ); // csrf 끄기. --> post/put 사용할수 있다.
        // 배포/운영 : 특정한 URL 수동으로 허용 , 운영 환경에서는 안전하게 몇몇개의 REST만 허용한다.
        // http.csrf( csrf -> csrf.ignoringRequestMatchers("csrf예외할URL") );
        // http.csrf( csrf -> csrf.ignoringRequestMatchers("/member/signup.do") ); // 회원가입 POST 예외

        // [5] 로그인, 시큐리티에 로그인 기능 [커스텀]을 제공한다. JSON 형식이 아닌 form 형식으로 지원한다.
        http.formLogin(loginForm ->
                loginForm.loginPage("/member/login")                // 로그인할 view page url 정의
                        .loginProcessingUrl("/member/login.do")     // 로그인 처리할 url 정의
                        .usernameParameter("mid")                   // 로그인에 사용할 id 변수명
                        .passwordParameter("mpwd")                  // 로그인에 사용할 pwd 변수명
//                        .defaultSuccessUrl("/")                     // 만약에 로그인 성공시 이동할 page url
//                        .failureUrl("/member/login") // 만약에 로그인 실패시 이동할 page url
                // fetch 나 axios 에서는 아래와 같은 방법으로 사용한다.
                        .successHandler((request, response, authentication) -> {
                            System.out.println("로그인 성공!");
                            response.setContentType("application/json"); // 응답 방식을 JSON 으로 변경
                            response.getWriter().println("true"); // JSON 형식의 true 응답하기
                        })
                        .failureHandler((request, response, exception) -> {
                            System.out.println("로그인 실패!");
                            response.setContentType("application/json"); // 응답 방식을 JSON 으로 변경
                            response.getWriter().println("false"); // JSON 형식의 false 응답하기
                        })
        );

        // [6] 로그아웃, 시큐리티에 로그아웃 기능 [커스텀]을 제공한다.
        http.logout(logout -> logout
                .logoutUrl("/member/logout.do") // 로그아웃 처리할 요청 url 정의 // GET 방식 지원
                .logoutSuccessUrl("/")          // 만약에 로그아웃 성공시 이동할 page url
                .invalidateHttpSession(true)    // 만약에 로그아웃 성공시 세션 초기화
        );

        // [7] 로그인을 처리할 서비스 객체 정의
        http.userDetailsService(memberService);


        // [2] http 객체를 빌드/실행하여 보안 필터 체인을 생성
        return http.build();
    } // F END
    @Autowired private MemberService memberService;

    // [2] 암호화 : 시큐리티가 회원의 패스워드 검증할때 사용할 암호화 객체, Bcrypt
        // --> 개발자가 직접 암호화를 비교하지 않고, 시큐리티가 자동으로 암호화를 비교한다.(로그인처리 자동)
    @Bean // 스프링 컨테이너에 메소드 등록 // PasswordEncoder : 시큐리티가 로그인 할때 사용할 암호화 인코딩 객체
    public PasswordEncoder encoder(){return new BCryptPasswordEncoder();}

} // CLASS END