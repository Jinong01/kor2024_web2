package korweb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reply")
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class ReplyEntity extends BaseTime{

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rno;

    @Column(columnDefinition = "varchar(255)")
    private String rcontent;

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;

    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;

}
