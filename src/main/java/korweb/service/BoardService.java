package korweb.service;

import korweb.model.dto.BoardDto;
import korweb.model.dto.MemberDto;
import korweb.model.entity.BoardEntity;
import korweb.model.entity.CategoryEntity;
import korweb.model.entity.MemberEntity;
import korweb.model.repository.BoardRepository;
import korweb.model.repository.CategoryRepository;
import korweb.model.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public List<BoardDto> boardFindAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> list = new ArrayList<>();
        list = boardEntityList.stream().map(BoardEntity::toDto)
                    .collect(Collectors.toList());
        // boardEntityList.forEach(entity -> {
        //      BoardDto boardDto = entity.toDto();
        //      list.add(boardDto);})
        return list;
    }

    public BoardDto boardFind(int bno) {
        Optional<BoardEntity> optionalBoard = boardRepository.findById(bno);
        if (optionalBoard.isPresent()){
            BoardEntity boardEntity = optionalBoard.get();
            BoardDto boardDto = boardEntity.toDto();
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
}
