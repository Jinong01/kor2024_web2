package korweb.controller;

import korweb.model.dto.BoardDto;
import korweb.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/board/write.do")
    public boolean boardWrite(@RequestBody BoardDto boardDto){return boardService.write(boardDto);}

//    @GetMapping("/board/findall.do")
//    public List<BoardDto> boardFindAll(){return boardService.boardFindAll();}

    @GetMapping("/board/findall.do")
    public List<BoardDto> boardFindAll(@RequestParam int cno){return boardService.boardFindAll(cno);}

    @GetMapping("/board/find.do")
    public BoardDto boardFind(@RequestParam int bno){return boardService.boardFind(bno);}

    @PutMapping("/board/update.do")
    public boolean boardUpdate(@RequestBody BoardDto boardDto){return boardService.boardUpdate(boardDto);}

    @DeleteMapping("/board/delete.do")
    public boolean boardDelete(@RequestParam int bno){return boardService.boardDelete(bno);}
}
