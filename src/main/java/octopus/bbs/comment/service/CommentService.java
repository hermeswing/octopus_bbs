package octopus.bbs.comment.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.config.ModelMapperConfig;
import octopus.bbs.comment.dto.CommentDto;
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
        List<CommentDto> list = commentRepository.findAll().stream()
                .map(data -> new CommentDto(data))
                .collect(Collectors.toList());
        
        log.debug("list :: {}", list);
        
        return list;
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