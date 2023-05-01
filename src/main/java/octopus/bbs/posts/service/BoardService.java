package octopus.bbs.posts.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.config.ModelMapperConfig;
import octopus.base.model.Pagination;
import octopus.base.model.PagingListResult;
import octopus.bbs.posts.dto.BoardDto;
import octopus.bbs.posts.dto.BoardSearchDto;
import octopus.bbs.posts.dto.TBoardM;
import octopus.bbs.posts.repository.BoardRepository;

@Service
// @AllArgsConstructor
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    // @AllArgsConstructor를 사용하는 경우
    // private CodeDRepository codeDRepository;
    
    private final BoardRepository   boardRepository;
    private final ModelMapperConfig modelMapperConfig;
    
    @Transactional // 선언적 트랜잭션을 사용
    public BoardDto findById(Long id) {
        Optional<TBoardM> board = boardRepository.findById(id);
        
        log.debug("board :: {}", board.get());
        
        ModelMapper modelMapper = modelMapperConfig.stricMapper();
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
    
    @Transactional(readOnly = true) // 선언적 트랜잭션을 사용
    public PagingListResult<BoardDto> findAllOfPage(final BoardSearchDto params,
            PageRequest pageRequest) {
        log.info("Offset :: {}", pageRequest.getOffset());
        log.info("PageSize :: {}", pageRequest.getPageSize());
        log.info("PageNumber :: {}", pageRequest.getPageNumber());
        
        List<BoardDto> boardList = null;
        Page<TBoardM>  list      = boardRepository.findAll(pageRequest);
        log.debug("board :: {}", list);
        
        if (list.hasContent()) {
            boardList = list.stream().map(data -> new BoardDto(data))
                    .collect(Collectors.toList());
            
            List<TBoardM> board     = list.getContent();             // 검색된 데이터
            int           totalPage = list.getTotalPages();          // 전체 페이지 수
            boolean       hasNext   = list.hasNext();                // 다음 페이지 존재여부
            int           totalCnt  = (int) list.getTotalElements(); // 검색된 전체 건수
            boolean       isData    = list.hasContent();             // 검색된 자료가 있는가?
            
            log.debug("boardList :: {}", boardList);
            log.debug("totalPage :: {}", totalPage);
            log.debug("hasNext :: {}", hasNext);
            log.debug("totalCnt :: {}", totalCnt);
            log.debug("isData :: {}", isData);
            
            // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
            Pagination pagination = new Pagination(totalCnt, params);
            params.setPagination(pagination);
            
            return new PagingListResult<>(boardList, pagination);
        } else {
            return new PagingListResult<>(Collections.emptyList(), null);
        }
    }
    
    /**
     * Pageable 참조소스
     * 
     * @param params
     * @param pageable
     * @return
     */
    @Transactional(readOnly = true) // 선언적 트랜잭션을 사용
    public PagingListResult<BoardDto> findAllOfPage2(BoardSearchDto params, Pageable pageable) {
        log.info("Offset :: {}", pageable.getOffset());
        log.info("PageSize :: {}", pageable.getPageSize());
        log.info("PageNumber :: {}", pageable.getPageNumber());
        
        List<BoardDto> boardList = null;
        Page<TBoardM>  list      = boardRepository.findAll(pageable);
        log.debug("board :: {}", list);
        
        if (list.hasContent()) {
            boardList = list.stream().map(data -> new BoardDto(data))
                    .collect(Collectors.toList());
            
            List<TBoardM> board     = list.getContent();             // 검색된 데이터
            int           totalPage = list.getTotalPages();          // 전체 페이지 수
            boolean       hasNext   = list.hasNext();                // 다음 페이지 존재여부
            int           totalCnt  = (int) list.getTotalElements(); // 검색된 전체 건수
            boolean       isData    = list.hasContent();             // 검색된 자료가 있는가?
            
            log.info("board :: {}", board);
            log.info("totalPage :: {}", totalPage);
            log.info("hasNext :: {}", hasNext);
            log.info("totalCnt :: {}", totalCnt);
            log.info("isData :: {}", isData);
            
            // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
            Pagination pagination = new Pagination(totalCnt, params);
            params.setPagination(pagination);
            
            return new PagingListResult<>(boardList, pagination);
        } else {
            return new PagingListResult<>(Collections.emptyList(), null);
        }
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
        
        log.debug("board 1 :: {}", board.get());
        
        board.get().updateBoard(dto);
        
        log.debug("board 2 :: {}", board.get());
    }
    
    @Transactional
    public void restUpdate(BoardDto dto) {
        
        log.debug("BoardDto :: {}", dto);
        
        Optional<TBoardM> board = boardRepository.findById(dto.getId());
        
        log.debug("board 1 :: {}", board.get());
        
        board.get().updateBoard(dto);
        
        log.debug("board 2 :: {}", board.get());
    }
    
    @Transactional
    public void delete(Long id) {
        log.debug("삭제될 ID :: {}", id);
        
        boardRepository.deleteById(id);
    }
}