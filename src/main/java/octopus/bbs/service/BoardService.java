package octopus.bbs.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.bbs.dto.BoardDto;
import octopus.bbs.dto.TBoardM;
import octopus.bbs.repository.BoardRepository;

@Service
// @AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    // @AllArgsConstructor를 사용하는 경우
    // private CodeDRepository codeDRepository;
    
    private final BoardRepository boardRepository;
    private final ModelMapper     modelMapper;
    
    @Transactional // 선언적 트랜잭션을 사용
    public BoardDto findById(Long id) {
        Optional<TBoardM> board = boardRepository.findById(id);
        
        log.debug("board :: {}", board.get());
        
        if (board.isPresent()) {
            boardRepository.updateCnt(id);
            return modelMapper.map(board.get(), BoardDto.class);
        } else {
            return new BoardDto();
        }
    }
    
    @Transactional(readOnly = true) // 선언적 트랜잭션을 사용
    public List<BoardDto> findAll() {
        List<BoardDto> list = boardRepository.findAll().stream()
                .map(data -> new BoardDto(data))
                .collect(Collectors.toList());
        
        log.debug("list :: {}", list);
        
        return list;
    }
    
    @Transactional
    public BoardDto save(BoardDto dto) {
        
        log.debug("BoardDto :: {}", dto);
        
        TBoardM board = dto.toEntity();
        
        log.debug("tCodeM :: {}", board);
        
        TBoardM saveBoard = boardRepository.save(board);
        
        return new BoardDto(saveBoard);
    }
    
    @Transactional
    public void update(BoardDto dto) {

        log.debug("BoardDto :: {}", dto);
        
        Optional<TBoardM> board = boardRepository.findById(dto.getId());
        
        log.debug("board :: {}", board.get());
        
        board.get().updateBoard(dto);
    }
    
    @Transactional
    public void delete(Long id) {
        log.debug("삭제될 ID :: {}", id);
        
        boardRepository.deleteById(id);
    }
}