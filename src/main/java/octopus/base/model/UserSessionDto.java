package octopus.base.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import octopus.basic.user.dto.UserDto;

@Data
@Builder
@AllArgsConstructor // 모든인자를 가지는객체 생성
@NoArgsConstructor // 인자 없이 객체 생성
@ToString
public class UserSessionDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userId;
	private String userNm;
	
    public UserSessionDto(UserDto user) {
        this.userId = user.getUserId();
        this.userNm = user.getUserNm();
    }
}
