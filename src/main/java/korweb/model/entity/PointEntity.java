package korweb.model.entity;

import jakarta.persistence.*;
import korweb.model.dto.PointDto;
import lombok.*;

@Entity
@Table(name = "point")
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class PointEntity extends BaseTime{

    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private int pid;

    @Column(columnDefinition = "varchar(30)", nullable = false)
    private String pname;

    @Column(columnDefinition = "int")
    private int mpoint;

    @ManyToOne
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;


    public PointDto toDto(){
        return PointDto.builder().pname(this.pname).mpoint(this.mpoint).build();
    }
}
