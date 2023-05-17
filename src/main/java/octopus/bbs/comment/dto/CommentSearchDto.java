package octopus.bbs.comment.dto;

import lombok.Getter;
import lombok.Setter;
import octopus.base.model.SearchDto;

@Getter
@Setter
public class CommentSearchDto extends SearchDto {

    private Long postId;    // 게시글 번호 (FK)

}