package octopus.base.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * <pre>
 * </pre>
 */
@Data
public class BaseDto implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 생성자
	 */
	protected String crtId;

	/**
	 * 생성일자
	 */
	//private LocalDateTime crtDt;

	/**
	 * 수정자
	 */
	protected String mdfId;

	/**
	 * 수정일
	 */
	//private LocalDateTime mdfDt;

}
