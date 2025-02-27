package korweb.model.dto;

import korweb.model.entity.BoardEntity;
import korweb.model.entity.ReplyEntity;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter@Setter@ToString@Builder@AllArgsConstructor@NoArgsConstructor
public class BoardDto {

    private int bno;
    private String btitle;
    private String bcontent;
    private int bview;
    private int mno;
    private int cno;
    private String cdate;

    private String mid;
    private String cname;

    // + 댓글리스트
    private List<Map<String ,String >> replyList;

    public BoardEntity toEntity(){
        return BoardEntity.builder().btitle(this.btitle)
                .bcontent(this.bcontent).bview(this.bview).build();
    }
}
