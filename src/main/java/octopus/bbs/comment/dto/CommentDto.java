package octopus.bbs.comment.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
public class CommentDto {
    private Long   id;
    private Long   postId;
    private String contents;
    
    private String crtNm;
    private String mdfNm;
    
    private String modalWriter;
    private String modalContent;
    
    private String        crtId; // 생성자
    private LocalDateTime crtDt; // 생성일자
    private String        mdfId; // 수정자
    private LocalDateTime mdfDt; // 수정일
    
    private String createDate; // 생성일자
    private String modifyDate; // 수정일
    
    public void parseDate(LocalDateTime crtDt, LocalDateTime mdfDt) {
        this.createDate = crtDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifyDate = mdfDt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    
    public TCommentM toEntity() {
        return TCommentM.builder().postId(postId).contents(contents)
                .crtId(getCrtId()).mdfId(getMdfId()).build();
    }
    
    public CommentDto(Long id, Long postId, String contents 
            , String crtId, String crtNm, LocalDateTime crtDt
            , String mdfId, String mdfNm, LocalDateTime mdfDt ) {
        this.id       = id;
        this.postId   = postId;
        this.contents = contents;
        this.crtId    = crtId;
        this.crtNm    = crtNm;
        this.mdfId    = mdfId;
        this.mdfNm    = mdfNm;
        parseDate(crtDt, mdfDt);
    }
    
    public CommentDto(TCommentM comment) {
        this.id       = comment.getId();
        this.postId   = comment.getPostId();
        this.contents = comment.getContents();
        this.crtId    = comment.getCrtId();
        this.crtDt    = comment.getCrtDt();
        this.mdfId    = comment.getMdfId();
        this.mdfDt    = comment.getMdfDt();
    }
}
