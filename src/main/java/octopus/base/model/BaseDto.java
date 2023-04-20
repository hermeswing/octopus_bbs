package octopus.base.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;

/**
 * <pre>
 * </pre>
 */
@Data
@MappedSuperclass // BaseEntity를 상속한 Entity들은 아래의 필드들을 컬럼으로 인식한다.
@EntityListeners(AuditingEntityListener.class) // Audting(자동으로 값 Mapping) 기능 추가
public class BaseDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * 생성자
     */
    protected String crtId;
    
    /**
     * 생성일자
     */
    private LocalDateTime crtDt;
    
    /**
     * 수정자
     */
    protected String mdfId;
    
    /**
     * 수정일
     */
    private LocalDateTime mdfDt;
    
}
