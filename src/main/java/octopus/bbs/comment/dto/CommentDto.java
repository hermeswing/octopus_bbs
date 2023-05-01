package octopus.bbs.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import octopus.base.model.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true) // true의 경우 부모 클래스 필드 값들도 동일한지 체크하며, false(기본값)일 경우 자신 클래스의 필드 값만 고려한다.
public class CommentDto extends BaseDto {
    private static final long serialVersionUID = 1L;
    
    private Long   id;
    private Long   postId;
    private String contents;
    
    private String modalWriter;
    private String modalContent;
    
    public TCommentM toEntity() {
        return TCommentM.builder().postId(postId).contents(contents)
                .crtId(getCrtId()).mdfId(getMdfId()).build();
    }
    
    public CommentDto(TCommentM comment) {
        this.id       = comment.getId();
        this.postId   = comment.getPostId();
        this.contents = comment.getContents();
        super.crtId   = comment.getCrtId();
        super.crtDt   = comment.getCrtDt();
        super.mdfId   = comment.getMdfId();
        super.mdfDt   = comment.getMdfDt();
    }
}
