package korweb.model.dto;

import korweb.model.entity.PointEntity;
import lombok.*;

@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class PointDto {
    private int pid;
    private String pname;
    private int mpoint;

    public PointEntity toEntity(){
        return PointEntity.builder().pname(this.pname).mpoint(this.mpoint).build();
    }
}
