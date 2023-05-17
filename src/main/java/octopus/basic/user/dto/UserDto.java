package octopus.basic.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import octopus.base.model.BaseDto;

@Data
@Builder
@AllArgsConstructor // 모든인자를 가지는객체 생성
@NoArgsConstructor // 인자 없이 객체 생성
@ToString
@EqualsAndHashCode(callSuper = true) // true의 경우 부모 클래스 필드 값들도 동일한지 체크하며, false(기본값)일 경우 자신 클래스의 필드 값만 고려한다.
public class UserDto extends BaseDto {
    private static final long serialVersionUID = 1L;
    
    private Long   id;
    private String userId;
    private String userNm;
    
    public TUserM toEntity() {
        return TUserM.builder().userId(userId).userNm(userNm)
                .crtId(getCrtId()).mdfId(getMdfId()).build();
    }
    
    public UserDto(TUserM user) {
        this.id     = user.getId();
        this.userId = user.getUserId();
        this.userNm = user.getUserNm();
        super.crtId = user.getCrtId();
        super.crtDt = user.getCrtDt();
        super.mdfId = user.getMdfId();
        super.mdfDt = user.getMdfDt();
    }
}
