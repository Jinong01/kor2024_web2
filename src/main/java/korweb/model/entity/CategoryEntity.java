package korweb.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class CategoryEntity extends BaseTime{

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cno;

    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String cname;

//    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @Builder.Default@ToString.Exclude
//    private List<BoardEntity> boardEntityList = new ArrayList<>();

}
