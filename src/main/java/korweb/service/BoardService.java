package korweb.service;

import korweb.model.dto.BoardDto;
import korweb.model.dto.MemberDto;
import korweb.model.entity.BoardEntity;
import korweb.model.entity.CategoryEntity;
import korweb.model.entity.MemberEntity;
import korweb.model.entity.ReplyEntity;
import korweb.model.repository.BoardRepository;
import korweb.model.repository.CategoryRepository;
import korweb.model.repository.MemberRepository;
import korweb.model.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BoardService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ReplyRepository replyRepository;

    // [1]
    public boolean write(BoardDto boardDto) {
        // [1] 사용자로부터 전달받은 boardDto(btitle,bcontent,cno) 를 엔티티로 전달
        BoardEntity boardEntity = boardDto.toEntity();
            // 1. 게시물 작성자는 현재 로그인된 회원 이므로 세션에서 회원번호 조회
        MemberDto loginDto = memberService.getMyInfo();
        // 로그인된 상태가 아니면 글쓰기 종료
        if (loginDto == null) return false;
        // 로그인된 상태이면 회원번호 조회
        int loginMno = loginDto.getMno();
        // 로그인된 회원 엔티티를 게시물 엔티티에 대입한다.
        MemberEntity loginEntity = memberRepository.findById(loginMno).get();
        boardEntity.setMemberEntity(loginEntity);
            // 2. 게시물 카테고리는 cno 를 entity 조회해서 게시물 엔티티에 대입한다.
        CategoryEntity categoryEntity = categoryRepository.findById(boardDto.getCno()).get();
        boardEntity.setCategoryEntity(categoryEntity);

        BoardEntity saveEntity = boardRepository.save(boardEntity);
        if (saveEntity.getBno()>0){return true;}
        else {return false;}
    }

    // [2]
    public List<BoardDto> boardFindAll(int cno) {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
            // * cno 를 이용한 동일한 cno 의 게시물 정보 찾기
        List<BoardDto> list = new ArrayList<>();
//        list = boardEntityList.stream()
//                .map(BoardEntity::toDto)
//                .collect(Collectors.toList());
        boardEntityList.forEach(entity -> {
              if(entity.getCategoryEntity().getCno()==cno){
              BoardDto boardDto = entity.toDto();
              list.add(boardDto);}
            });
        return list;
    }

    // [3]
    public BoardDto boardFind(int bno) {
        Optional<BoardEntity> optionalBoard = boardRepository.findById(bno);
        if (optionalBoard.isPresent()){
            BoardEntity boardEntity = optionalBoard.get();
            BoardDto boardDto = boardEntity.toDto();

            // *현재 게시물의 댓글 리스트 조회
            // 1. 모든 댓글 리스트 조회
            List<ReplyEntity> replyEntityList = replyRepository.findAll();
            // 2. 모든 댓글을 DTO 로 변환 -> ReplyDto 대신 MAP 컬렌션 이용한 방법
            List<Map<String, String >> replyList = new ArrayList<>();
            // 3. 엔티티를 MAP 으로 변환하기 위한 엔티티 리스트를 반복문
            replyEntityList.forEach((reply) -> {
                // * 만약에 현재 조회중인 게시물번호 와 댓글리스트내 반복중인 댓글의 게시물번호가 같다면
                if (reply.getBoardEntity().getBno() == bno) {
                    // 4. map 객체 선언
                    Map<String, String> map = new HashMap<>();
                    // 5. map 객체에 하나씩 key:value(엔트리)으로 저장한다.
                    map.put("rno", reply.getRno() + ""); // 숫자타입 + "" => 문자타입 변환
                    map.put("rcontent", reply.getRcontent() + "");
                    map.put("cdate", reply.getCdate().toLocalDate().toString() + "");
                    map.put("mid", reply.getMemberEntity().getMid());
                    map.put("mimg", reply.getMemberEntity().getMimg());
                    // 6. map 를 리스트에 담는다.
                    replyList.add(map);
                }
            });
                // 7. 반복문 종료된 후 boardDto 에 댓글리스트 담기
            boardDto.setReplyList(replyList);

            return boardDto;
        } else {return null;}
    }

    public boolean boardUpdate(BoardDto boardDto) {
        return false;
    }

    public boolean boardDelete(int bno) {
        boardRepository.deleteById(bno);
        return true;
    }

    // ======================== 댓글 ===========================

    public boolean replyWrite(Map<String, String> replyDto) {
        // 1. 현재 로그인된 회원 정보 조회
        MemberDto memberDto = memberService.getMyInfo();
        // 2. 만약에 로그인된 정보가 없으면 함수 종료
        if (memberDto == null){return false;}
        // [로그인중이면]
        // 3. 회원엔티티 조회
        MemberEntity memberEntity = memberRepository.findById(memberDto.getMno()).get();
        // 4. 현재 작성할 댓글이 위치한 조회중인 게시물 엔티티 조회
            // Integer.parseInt("문자열") : 문자열타입 => 정수타입 반환 함수
        int bno = Integer.parseInt(replyDto.get("bno"));
        BoardEntity boardEntity = boardRepository.findById(bno).get();
        // 5. 입력받은 매개변수  map 를 entity 로 변환
        ReplyEntity replyEntity = new ReplyEntity();
        replyEntity.setRcontent(replyDto.get("rcontent"));
        replyEntity.setMemberEntity(memberEntity);
        replyEntity.setBoardEntity(boardEntity);

        ReplyEntity saveEntity = replyRepository.save(replyEntity);
        if (saveEntity.getRno()>0) {return true;}
        return false;
    }

    public List<Map<String ,String >> replyFindAll(int bno) {
        // 1. 모든 댓글 엔티티 조회
        List<ReplyEntity> replyEntityList = replyRepository.findAll();
        // 2. 모든 댓글 map 저장할 list 선언
        List<Map<String ,String>> replyList = new ArrayList<>();
        // 3. 모든 댓글 엔티티를 반복문으로 조회
        replyEntityList.forEach((reply) -> {
            // * 만약에 현재 조회중인 게시물번호 와 댓글리스트내 반복중인 댓글의 게시물번호가 같다면
            if (reply.getBoardEntity().getBno() == bno) {
                // 4. map 객체 선언
                Map<String, String> map = new HashMap<>();
                // 5. map 객체에 하나씩 key:value(엔트리)으로 저장한다.
                map.put("rno", reply.getRno() + ""); // 숫자타입 + "" => 문자타입 변환
                map.put("rcontent", reply.getRcontent() + "");
                map.put("cdate", reply.getCdate().toLocalDate().toString() + "");
                map.put("mid", reply.getMemberEntity().getMid());
                map.put("mimg", reply.getMemberEntity().getMimg());
                // 6. map 를 리스트에 담는다.
                replyList.add(map);
            }
        });
        return replyList;
    }
}
