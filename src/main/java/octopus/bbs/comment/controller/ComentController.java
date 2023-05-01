package octopus.bbs.comment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import octopus.base.anotation.LoginUser;
import octopus.base.model.UserSessionDto;
import octopus.bbs.comment.dto.CommentDto;
import octopus.bbs.comment.service.CommentService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ComentController {
    private final CommentService commentService;
    
    // 댓글 리스트 조회
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> findAllComment(@PathVariable final Long postId) {
        return commentService.findAllComment(postId);
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
        
        dto.setCrtId(userDto.getUserId());
        dto.setMdfId(userDto.getUserId());
        
        log.debug("BoardDto :: {}", dto);
        
        CommentDto saveComment = commentService.saveComment(dto);
        return saveComment;
    }
    
    // 기존 댓글 수정
    @PatchMapping("/posts/{postId}/comments/{id}")
    public CommentDto updateComment(@PathVariable final Long postId, @PathVariable final Long id,
            @RequestBody final CommentDto dto, @LoginUser UserSessionDto userDto) {
        
        dto.setMdfId(userDto.getUserId());
        log.debug("BoardDto :: {}", dto);
        
        commentService.updateComment(dto);
        return commentService.findCommentById(id);
    }
}
