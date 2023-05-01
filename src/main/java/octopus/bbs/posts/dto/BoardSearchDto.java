package octopus.bbs.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import octopus.base.model.SearchDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@EqualsAndHashCode(callSuper = true) // true의 경우 부모 클래스 필드 값들도 동일한지 체크하며, false(기본값)일 경우 자신 클래스의 필드 값만 고려한다.
public class BoardSearchDto extends SearchDto {
    
    private String keyword;    // 검색 키워드
    private String searchType; // 검색 유형
    
}