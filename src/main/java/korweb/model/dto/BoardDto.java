package korweb.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class BoardDto {

    private int bno;
    private String btitle;
    private String bcontent;
    private int cno;
}
