package korweb.model.dto;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter@Setter@Builder@ToString@AllArgsConstructor@NoArgsConstructor
// 시큐리티의 일반회원과 oauth2 회원 정보를 통합하는 Dto
public class LoginDto implements UserDetails, OAuth2User {

    // + 필수
    private String mid; // 로그인한 회원의 아이디
    private String mpwd; // 로그인할 회원의 비밀번호 (oauth2 회원은 사용하지 않는다.)
    private List<GrantedAuthority> mrolList; // 로그인한 회원의 권한/등급/레벨 목록
    // + 선택 :

    // UserDetails : 시큐리티에서 일반회원들의 정보를 조작하는 인터페이스
    // OAuth2User : 시큐리티에서 oauth 회원들의 정보를 조작하는 인터페이스
        // -> 두 인터페이스를 LoginDto(내가 만든 클래스) 에서 구현(implements)
        // 즉] LoginDto 에서 두 인터페이스를 모두 포함하므로 LoginDto 타입으로 두 타입들을 조작할 수 있다.

    @Override // [1] 로그인한 회원의 아이디를 반환할 메소드
    public String getName() {
        return this.mid;
    }

    @Override
    public <A> A getAttribute(String name) { //  수업에서 재정의 X, oauth2 회원에서 사용하는 oauth2 속성 객체 반환할 메소드
        return OAuth2User.super.getAttribute(name);
    }

    @Override // 수업에서 재정의 X
    public Map<String, Object> getAttributes() {
        return Map.of();
    }
              // 시큐리티에서 Username = 로그인할 때 사용하는 아이디
    @Override // [2] 일반 회원이 로그인 아이디를 반환하는 메소드
    public String getUsername() {
        return this.mid;
    }

    @Override // [3] 일반 회원이 로그인 비밀번호를 반환하는 메소드
    public String getPassword() {
        return this.mpwd;
    }

    @Override  // [4] 권한목록을 반환하는 메소드
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.mrolList;
    }

    @Override // 수업에서 재정의 X, 계정활성화 여부, 기본값 true, 활용 : 계정 잠금 기능
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override // 수업에서 재정의 X, 비밀번호 만료 여부 확인, 기본값 true
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override // 수업에서 재정의 X, 계정 잠금 여부, 기본값 ture : 잠금이 아니다, 활용 : 휴면계정
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override // 수업에서 재정의 X, 계정 만료 여부, 기본값 true : 만료되지 않았다
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }
}
