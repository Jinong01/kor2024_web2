package korweb.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file")
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class FileEntity extends BaseTime{

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fno;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String fname;

    @ManyToOne
    @JoinColumn(name = "bno")
    private BoardEntity boardEntity;
}
