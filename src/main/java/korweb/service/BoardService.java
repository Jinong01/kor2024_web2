package korweb.service;

import korweb.model.dto.BoardDto;
import korweb.model.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    public boolean write(BoardDto boardDto) {
        return false;
    }

    public List<BoardDto> boardFindAll() {
        return null;
    }

    public BoardDto boardFind(int bno) {
        return null;
    }

    public boolean boardUpdate(BoardDto boardDto) {
        return false;
    }

    public boolean boardDelete(int bno) {
        return false;
    }
}
