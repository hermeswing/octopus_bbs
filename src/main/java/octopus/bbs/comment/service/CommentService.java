package octopus.bbs.comment.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.config.ModelMapperConfig;
import octopus.base.model.Pagination;
import octopus.base.model.PagingListResult;
import octopus.bbs.comment.dto.CommentDto;
import octopus.bbs.comment.dto.CommentSearchDto;
import octopus.bbs.comment.dto.TCommentM;
import octopus.bbs.comment.repository.CommentRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final ModelMapperConfig modelMapperConfig;
    
    /**
     * 댓글 리스트 조회
     * 
     * @param postId - 게시글 번호 (FK)
     * @return 특정 게시글에 등록된 댓글 리스트
     */
    public List<CommentDto> findAllComment(final Long postId) {
        
        List<CommentDto> list = commentRepository.findAllByPostId(postId);
        
        log.debug("list :: {}", list);
        
        return list;
    }
    
    /**
     * <pre>
     * 댓글 리스트 조회
     * 페이지 처리
     * </pre>
     * 
     * @param postId - 게시글 번호 (FK)
     * @return 특정 게시글에 등록된 댓글 리스트
     */
    public PagingListResult<CommentDto> findAllOfPage(final CommentSearchDto params) {
        
        PageRequest pageRequest = PageRequest.of(params.getPage(), params.getRecordSize(),
                Sort.by(Sort.Direction.DESC, "id"));
        
        log.info("PostId() :: {}", params.getPostId());
        log.info("Offset :: {}", pageRequest.getOffset());
        log.info("PageSize :: {}", pageRequest.getPageSize());
        log.info("PageNumber :: {}", pageRequest.getPageNumber());
        
        List<CommentDto> commentList = null;
        Page<TCommentM>  list        = commentRepository.findByPostId(params.getPostId(),
                pageRequest);
        log.debug("comments list :: {}", list);
        
        if (list.hasContent()) {
            commentList = list.stream().map(data -> new CommentDto(data))
                    .collect(Collectors.toList());
            
            List<TCommentM> comments  = list.getContent();             // 검색된 데이터
            int             totalPage = list.getTotalPages();          // 전체 페이지 수
            boolean         hasNext   = list.hasNext();                // 다음 페이지 존재여부
            int             totalCnt  = (int) list.getTotalElements(); // 검색된 전체 건수
            boolean         isData    = list.hasContent();             // 검색된 자료가 있는가?
            
            log.debug("boardList :: {}", commentList);
            log.debug("totalPage :: {}", totalPage);
            log.debug("hasNext :: {}", hasNext);
            log.debug("totalCnt :: {}", totalCnt);
            log.debug("isData :: {}", isData);
            
            // Pagination 객체를 생성해서 페이지 정보 계산 후 SearchDto 타입의 객체인 params에 계산된 페이지 정보 저장
            Pagination pagination = new Pagination(totalCnt, params);
            params.setPagination(pagination);
            
            return new PagingListResult<>(commentList, pagination);
        } else {
            return new PagingListResult<>(Collections.emptyList(), null);
        }
    }
    
    /**
     * 댓글 상세정보 조회
     * 
     * @param id - PK
     * @return 댓글 상세정보
     */
    public CommentDto findCommentById(final Long id) {
        Optional<TCommentM> comment = commentRepository.findById(id);
        
        log.debug("comment :: {}", comment.get());
        
        ModelMapper modelMapper = modelMapperConfig.stricMapper();
        if (comment.isPresent()) {
            return modelMapper.map(comment.get(), CommentDto.class);
        } else {
            return new CommentDto();
        }
    }
    
    /**
     * 댓글 저장
     * 
     * @param params - 댓글 정보
     * @return Generated PK
     */
    @Transactional
    public CommentDto saveComment(CommentDto dto) {
        log.debug("CommentDto :: {}", dto);
        
        TCommentM comment = dto.toEntity();
        
        log.debug("TCommentM :: {}", comment);
        
        TCommentM saveComment = commentRepository.save(comment);
        
        return new CommentDto(saveComment);
    }
    
    /**
     * 댓글 수정
     * 
     * @param params - 댓글 정보
     * @return PK
     */
    @Transactional
    public void updateComment(CommentDto dto) {
        log.debug("CommentDto :: {}", dto);
        
        Optional<TCommentM> comment = commentRepository.findById(dto.getId());
        
        log.debug("comment 1 :: {}", comment.get());
        
        comment.get().updateComment(dto);
        
        log.debug("comment 2 :: {}", comment.get());
    }
    
    /**
     * 댓글 삭제
     * 
     * @param id - PK
     * @return PK
     */
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
    
}