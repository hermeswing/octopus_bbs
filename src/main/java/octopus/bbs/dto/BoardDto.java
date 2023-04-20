package octopus.bbs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import octopus.base.model.BaseDto;

@Data
@Builder
@AllArgsConstructor // 모든인자를 가지는객체 생성
@NoArgsConstructor // 인자 없이 객체 생성
@EqualsAndHashCode(callSuper = true) // true의 경우 부모 클래스 필드 값들도 동일한지 체크하며, false(기본값)일 경우 자신 클래스의 필드 값만 고려한다.
public class BoardDto extends BaseDto {
    private static final long serialVersionUID = 1L;
    
    private Long    id;
    private String  title;
    private String  contents;
    private Integer readCnt;
    
    public TBoardM toEntity() {
        return TBoardM.builder().title(title).contents(contents)
                .readCnt(readCnt)
                .crtId(getCrtId()).mdfId(getMdfId()).build();
    }
    
    public static BoardDto makeDto(TBoardM board) {
        BoardDto dto = BoardDto.builder().id(board.getId())
        .title(board.getTitle())
        .contents(board.getContents())
        .readCnt(board.getReadCnt())
        .build();
        
        dto.setCrtId(board.getCrtId());
        dto.setCrtDt(board.getCrtDt());
        dto.setMdfId(board.getMdfId());
        dto.setMdfDt(board.getMdfDt());
        
        return dto;
    }
}
