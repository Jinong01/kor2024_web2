package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.BoardDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class BoardEntity extends BaseTime{

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bno;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String btitle;

    @Column(columnDefinition = "longtext", nullable = false)
    private String bcontent;

    @Column(columnDefinition = "int")
    private int bview;

//    @OneToMany(mappedBy = "boardEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @ToString.Exclude@Builder.Default
//    private List<ReplyEntity> replyEntityList = new ArrayList<>();
//
//    @OneToMany(mappedBy = "boardEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @ToString.Exclude@Builder.Default
//    private List<FileEntity> fileEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "cno")
    private CategoryEntity categoryEntity;

    public BoardDto toDto(){
        return BoardDto.builder().bno(this.bno).btitle(this.btitle)
                .bcontent(this.bcontent).bview(this.bview)
                .mno(memberEntity.getMno())
                .cno(categoryEntity.getCno())
                .mid(memberEntity.getMid())
                .cname(categoryEntity.getCname())
                .cdate(this.getCdate().toString()).build();
    }
}
