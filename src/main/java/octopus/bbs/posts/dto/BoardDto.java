package octopus.bbs.posts.dto;

import javax.persistence.Convert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import octopus.base.converter.BooleanToYNConverter;
import octopus.base.model.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true) // true의 경우 부모 클래스 필드 값들도 동일한지 체크하며, false(기본값)일 경우 자신 클래스의 필드 값만 고려한다.
public class BoardDto extends BaseDto {
    private static final long serialVersionUID = 1L;
    
    private Long    id;
    private String  title;
    private String  contents;
    private Integer readCnt;
    
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean noticeYn; // 공지여부
    
    public TBoardM toEntity() {
        return TBoardM.builder().title(title).contents(contents)
                .crtId(getCrtId()).mdfId(getMdfId()).build();
    }
    
    public BoardDto(TBoardM board) {
        this.id       = board.getId();
        this.title    = board.getTitle();
        this.contents = board.getContents();
        this.readCnt  = board.getReadCnt();
        this.noticeYn = board.getNoticeYn();
        super.crtId   = board.getCrtId();
        super.crtDt   = board.getCrtDt();
        super.mdfId   = board.getMdfId();
        super.mdfDt   = board.getMdfDt();
    }
}
