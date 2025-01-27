package korweb.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import korweb.model.dto.MemberDto;
import korweb.model.dto.PointDto;
import korweb.model.entity.MemberEntity;
import korweb.model.entity.PointEntity;
import korweb.model.repository.MemberRepository;
import korweb.model.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    @Autowired private MemberRepository memberRepository;
    @Autowired private PointRepository pointRepository;

    // 1. 회원가입 서비스
    @Transactional // 트랜잭션
    public boolean signup( MemberDto memberDto ){
        // 1.  저장할 dto를 entity 로 변환한다.
        MemberEntity memberEntity = memberDto.toEntity();
        // 2. 변환된 entity를 save한다.
        // 3. save(영속성/연결된) 한 엔티티를 반환 받는다.
        MemberEntity saveEntity = memberRepository.save( memberEntity );
        // 4. 만약에 영속된 엔티티의 회원번호가 0보다 크면 회원가입 성공
        if( saveEntity.getMno() > 0 ){
            PointDto pointDto = PointDto.builder().pname("회원가입").mpoint(100).build();
            pointPayment(pointDto,memberEntity);
            return true;}
        else{ return  false;}
    } // f end

    // 2. 로그인 서비스
    @Transactional // 트랜잭션
    public boolean login( MemberDto memberDto ){
        //[방법1]
        /*
        // (1) 모든 회원 엔티티를 조회한다.
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        // (2) 모든 회원 엔티티를 하나씩 조회한다.
        for( int index = 0 ; index <= memberEntityList.size()-1 ; index++ ){
            // (3) index번째의 엔티티를 꺼내기
            MemberEntity memberEntity = memberEntityList.get(index);
            // (4) index번째의 엔티티 아이디가 입력받은(dto) 아이디와 같으면
            if( memberEntity.getMid().equals( memberDto.getMid() ) ) {
                // (5) index번째의 엔티티 비밀번호가 입력받은(dto)비밀번호와 같으면
                if( memberEntity.getMpwd().equals( memberDto.getMpwd() ) ){
                    System.out.println(" login OK ");
                    return true; // 로그인 성공
                } // if end
            } // if end
        } // for end
        return false; // 로그인 실패
        */

        // [방법2] JPA Repository 추상메소드 활용.
        boolean result
        = memberRepository.existsByMidAndMpwd( memberDto.getMid() , memberDto.getMpwd() );

        if( result == true ){
            System.out.println("로그인성공");
            setSession( memberDto.getMid() ); // 로그인 성공시 세션에 아이디 저장
            PointDto pointDto = PointDto.builder().pname("로그인").mpoint(1).build();
            MemberEntity memberEntity = memberRepository.findById(getMyInfo().getMno()).get();
            pointPayment(pointDto,memberEntity);
            return true; // 로그인 성공
        }else{
            System.out.println("로그인실패");
            return false; // 로그인 실패
        }
    } // f end
    // ===================== 세션 관련 함수 ============== //
    // (1) 내장된 톰캣 서버의 요청 객체
    @Autowired private HttpServletRequest request;

    // [3] 세션객체내 정보 추가 : 세션객체에 로그인된 회원아이디를 추가하는 함수. ( 로그인 )
    public boolean setSession( String mid ){
        // (2) 요청 객체를 이용한 톰캣내 세션 객체를 반환한다.
        HttpSession httpSession = request.getSession();
        // (3) 세션 객체에 속성(새로운 값) 추가한다.
        httpSession.setAttribute( "loginId" , mid );
        return true;
    } // f end

    // [4] 세션객체내 정보 반환 : 세션객체에 로그인된 회원아이디 반환하는 함수 ( 내정보 조회 , 수정 등등 )
    public String getSession( ){
        // (2)
        HttpSession httpSession = request.getSession();
        // (4) 세션 객체에 속성명의 값 반환한다. * 반환타입이 Object 이다.
        Object object = httpSession.getAttribute( "loginId");
        // (5) 검사후 타입변환
        if( object != null ){// 만약에 세션 정보가 존재하면
            String mid = (String)object; // Object타입 --> String타입
            return mid;
        }
        return null;
    } // f end

    // [5] 세션객체내 정보 초기화 : 로그아웃
    public boolean deleteSession(){
        HttpSession httpSession = request.getSession(); // (2)
        // (3) 세션객체 안에 특정한 속성명 제거
        httpSession.removeAttribute( "loginId");
        return true;
    }

    // [6] 현재 로그인된 회원의 회원정보 조회
    public MemberDto getMyInfo(){
        String mid = getSession();  // 1. 현재 세션에 저장된 회원 아이디 조회
        if( mid != null ){   // 2. 만약에 로그인상태이면
            MemberEntity memberEntity = memberRepository.findByMid( mid );  // 3. 회원아이디로 엔티티 조회
            MemberDto memberDto = memberEntity.toDto(); // 4. entity --> dto 변환
            return memberDto;// 5. 반환
        }
        return null; // * 비로그인상태이면
    } // f end
    // [7] 현재 로그인된 회원 탈퇴
    public boolean myDelete( ){
        String mid = getSession(); // 1. 현재 세션에 저장된 회원 아이디 조회
        if( mid != null ){// 2. 만약에 로그인상태이면
            MemberEntity memberEntity = memberRepository.findByMid( mid ); // 3. 현재 로그인된 아이디로 엔티티 조회
            memberRepository.delete( memberEntity ); // 4. 엔티티 탈퇴/삭제 하기
            deleteSession();// ** 로그인정보 지우기 : 로그아웃
            return true;// 5. 반환
        }
        return false; // * 비로그인상태이면
    } // f end
    // [8] 현재 로그인된 회원 정보 수정 , mname 닉네임 , memail 이메일
    @Transactional
    public boolean myUpdate( MemberDto memberDto ){
        String mid = getSession();
        if( mid != null ){
            MemberEntity memberEntity = memberRepository.findByMid( mid );
            memberEntity.setMname( memberDto.getMname() );
            memberEntity.setMemail( memberDto.getMemail() );
            return true;
        }
        return false;
    } // f end

    // [9] 포인트 지급 함수 .
    @Transactional
    public boolean pointPayment(PointDto pointDto, MemberEntity memberEntity){
        PointEntity pointEntity = pointDto.toEntity();
        pointEntity.setMemberEntity(memberEntity);
        PointEntity saveEntity = pointRepository.save(pointEntity);
        if (saveEntity.getPid() > 0){return true;}
        else {return false;}
    }

    // [10] 내 포인트 조회
    public int getMyPoint(){
        Optional<MemberEntity> optionalEntity = memberRepository.findById(getMyInfo().getMno());
        int myPoint = 0;
        if (optionalEntity.isPresent()){
            MemberEntity memberEntity = optionalEntity.get();
            List<PointEntity> pointEntityList = memberEntity.getPointEntityList();
            for (int index = 0; index <= pointEntityList.size()-1; index++){
                myPoint += pointEntityList.get(index).getMpoint();
            }
        }
        return myPoint;
    }
} // class end



















