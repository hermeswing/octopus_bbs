package octopus.bbs.comment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.anotation.LoginUser;
import octopus.base.model.ListResult;
import octopus.base.model.PagingListResult;
import octopus.base.model.UserSessionDto;
import octopus.base.service.ResponseService;
import octopus.bbs.comment.dto.CommentDto;
import octopus.bbs.comment.dto.CommentSearchDto;
import octopus.bbs.comment.service.CommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ComentController {
    private final ResponseService responseService;
    private final CommentService  commentService;
    
    /**
     * <pre>
     * 모든 댓글 목록 조회
     * </pre>
     * 
     * @param postId
     * @return
     */
    @GetMapping("/posts/{postId}/all")
    public ListResult<CommentDto> findAllComment(@PathVariable final Long postId) {
        
        List<CommentDto> list = commentService.findAllComment(postId);
        
        return responseService.getListResult(list);
    }
    
    /**
     * <pre>
     * 모든 댓글 목록 조회
     * 페이지 처리
     * </pre>
     * 
     * @param params
     * @return
     */
    @GetMapping("/posts/{postId}/comments")
    public PagingListResult<CommentDto> findAllOfPage(final CommentSearchDto params) {
        return commentService.findAllOfPage(params);
    }
    
    // 댓글 상세정보 조회
    @GetMapping("/posts/{postId}/comments/{id}")
    public CommentDto findCommentById(@PathVariable final Long postId,
            @PathVariable final Long id) {
        return commentService.findCommentById(id);
    }
    
    // 신규 댓글 생성
    @PostMapping("/posts/{postId}/comments")
    public CommentDto saveComment(@PathVariable final Long postId,
            final CommentDto dto, @LoginUser UserSessionDto userDto) {
        
        log.debug("userDto :: {}", userDto);
        
        dto.setCrtId(userDto.getUserId());
        dto.setMdfId(userDto.getUserId());
        
        log.debug("CommentDto :: {}", dto);
        
        CommentDto saveComment = commentService.saveComment(dto);
        return saveComment;
    }
    
    // 기존 댓글 수정
    @PostMapping("/posts/{postId}/comments/{id}")
    public CommentDto updateComment(@PathVariable final Long postId, @PathVariable final Long id,
            @RequestBody CommentDto dto, @LoginUser UserSessionDto userDto) {
        
        log.debug("userDto :: {}", userDto);
        
        // json -> List
        // String json = params.get("list").toString();
        // ObjectMapper mapper = new ObjectMapper();
        // List list = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>(){});
        
        // json -> DTO
        // List<DTO> list = mapper.readValue(json, new TypeReference<ArrayList<DTO>>(){});
        
        log.debug("CommentDto :: {}", dto);
        
        commentService.updateComment(dto);
        return commentService.findCommentById(id);
    }
}
