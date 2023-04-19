package octopus.bbs.service;

import java.util.Optional;

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
    
    @Transactional(readOnly = true)
    public BoardDto findById(Long id) {
        Optional<TBoardM> board = boardRepository.findById(id);
        
        log.debug("board :: {}", board.get());
        
        if (board.isPresent()) {
            // return TBoardMDto.getDto(tCodeM.get());
            return modelMapper.map(board.get(), BoardDto.class);
        } else {
            return new BoardDto();
        }
    }
    
    // @Transactional(readOnly = true)
    // public List<BoardDto> findAllCd() {
    // List<BoardDto> list = boardRepository.findAll().stream()
    // .map(data -> BoardDto.makeDto(data))
    // .collect(Collectors.toList());
    // // List<TCodeMDto> list = codeDRepository.findAll().stream().map(data ->
    // // modelMapper.map(data, TCodeMDto.class))
    // // .collect(Collectors.toList());
    //
    // log.debug("list :: {}", list);
    //
    // return list;
    // }
    //
    // @Transactional
    // public TBoardM save(BoardDto dto) {
    //
    // log.debug("dto :: {}", dto);
    //
    // TBoardM board = dto.toEntity();
    //
    // log.debug("tCodeM :: {}", board);
    //
    // return boardRepository.save(board);
    // }
    //
    // @Transactional
    // public int update(BoardDto dto) {
    //
    // log.debug("dto :: {}", dto);
    //
    // TBoardM board = dto.toEntity();
    //
    // log.debug("tCodeM :: {}", board);
    //
    // return boardRepository.updateTCodeM(board.getId(), board.getPCdNm());
    // }
    //
    // @Transactional
    // public void update02(BoardDto dto) {
    // Optional<TBoardM> board = boardRepository.findById(dto.getId());
    //
    // log.debug("board :: {}", board.get());
    //
    // board.get().updateCodeM(dto);
    // }
    //
    // @Transactional
    // public void delete(BoardDto dto) {
    // Optional<TBoardM> board = boardRepository.findById(dto.getId());
    //
    // log.debug("board :: {}", board.get());
    //
    // board.get().deleteById(board.get());
    // }
}