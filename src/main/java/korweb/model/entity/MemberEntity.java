package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.MemberDto;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Getter@Setter@ToString@Builder // 룸복
@AllArgsConstructor@NoArgsConstructor // 룸복
@Entity // 엔티티
@Table( name = "member") // 테이블명
public class MemberEntity extends BaseTime {

    @Id // pk
    @GeneratedValue( strategy = GenerationType.IDENTITY ) // auto_increment
    private int mno; // 회원번호

    @Column( nullable = false  , unique = true , columnDefinition = "varchar(30)")
    private String mid; // 회원아이디

    @Column( nullable = false , columnDefinition = "varchar(100)" )
    private String mpwd; // 회원비밀번호

    @Column( nullable = false ,  columnDefinition = "varchar(20)" )
    private String mname; // 회원닉네임

    @Column(  nullable = false , unique = true , columnDefinition = "varchar(50)" )
    private String memail; // 회원이메일

    @Column( nullable = false, columnDefinition = "varchar(255)")
    private String mimg; // 회원 프로필 사진

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default@ToString.Exclude
    private List<PointEntity> pointEntityList = new ArrayList<>();

//    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @Builder.Default@ToString.Exclude
//    private List<BoardEntity> boardEntityList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<ReplyEntity> replyEntityList = new ArrayList<>();

    // entity --> dto 변환함수
    public MemberDto toDto(){
        return MemberDto.builder()
                .mno( this.mno )
                .mid( this.mid )
                .mpwd( this.mpwd )
                .mname( this.mname )
                .memail( this.memail)
                .mimg(this.mimg)
                .build();
    }
}












