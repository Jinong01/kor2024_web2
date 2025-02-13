package korweb.model.repository;

import korweb.model.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    // findById(pk 번호) : 지정한 pk 번호의 레코드 조회
    // findAll()        : 모든 레코드 조회

    // 1. findBy 필드명 (int 필드명)
    // Page<BoardEntity> findByCno(int cno, Pageable pageable);
    // 만약에 cno 가 참조키(fk) 일 때는 fk 필드명 넣지않고 엔티티 필드명을 넣는다
    Page<BoardEntity> findByCategoryEntity_Cno(int cno, Pageable pageable);

    // [2] JPA nativeQuery (실제 DB 의 SQL 문법) 이용한 조회
        // 1. 특정한 카테고리 번호에 해당하는 모든 게시물 조회
        // Dao(sql) : select * from board where cno =?;
        // JPA(nativeQuery) : select * from board where cno = :매개변수명
    @Query(value = "select * from board where cno = :cno", nativeQuery = true)
    List<BoardEntity> findByQuery1(int cno);
        // 2. 게시물 제목에 키워드를 포함하는 모든 게시물 조회
        // Dao(sql) : select * from board where btitle like %?%;
        // JPA : select * from board where btitle like %:매개변수%;
    @Query(value = "select * from board where btitle like %:keyWord%", nativeQuery = true)
    List<BoardEntity> findByQuery2 (String keyWord);
        // 3. 특정 카테고리 번호에 게시물 제목에서 키워드를 포함하는 모든 게시물 조회
        // Dao : select * from board where cno = ? and btitle like%?%
        // JPA : select * from board where cno = :cno and btitle like %:keyWord%
    @Query(value = "select * from board where cno = :cno and btitle like %:keyWord%",nativeQuery = true)
    List<BoardEntity> findByQuery3 (int cno, String keyWord);
        // 4. 매개변수 필드에 키워드를 포함하는 모든 게시물 조회, 필드명의 값에 따른 [sql : if(조건, 참 , 거짓)] 적용
        // Dao 는 불가능
        // JPA : select * from board where if(:key = 'btitle, btitle like %:keyWord%)
    @Query(value = "select * from board where if(:key = 'btitle', btitle like %:keyWord%, bcontent like %:keyWord%)",nativeQuery = true)
    List<BoardEntity> findByQuery4 (String key, String keyWord);

        // * 카테고리별 (1. 전체조회[키워드가 존재하지않으면] 2. 제목검색 3. 내용검색) + 페이징처리
    @Query(value = "select * from board where cno = :cno and " +
            " if(:keyWord = '', true," +
            " if(:key = 'btitle', btitle like %:keyWord%," +
            " if(:key = 'bcontent', bcontent like %:keyWord%, true)))",nativeQuery = true)
    Page<BoardEntity> findBySearch(int cno, String key, String keyWord, Pageable pageable);
    // if 조건이 실행 되었을 때 가정
        // SQL IF 조건문 : if(조건,참,거짓)
        // SQL IF 조건문 중첩 : if(조건1, 참1, if(조건2, 참2, if(조건3, 참3 , 그외거짓)))

    // 1. 전체조회 : select * from board where cno = :cno and true
    // 2. 제목조회 : select * from board where cno = :cno and btitle like %:keyWord%
    // 3. 내용조회 : select * from board where cno = :cno and bcontent like %:keyWord%
    // 4. 그외 : select * from board where cno = :cno and true
}
