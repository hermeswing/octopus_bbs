package octopus.bbs.comment.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import octopus.bbs.comment.dto.CommentDto;
import octopus.bbs.comment.dto.TCommentM;

// https://hackids.tistory.com/129 참조
//
// @Repository : JpaRepository를 사용하면 @Repository를 사용하지 않아도 됨.
public interface CommentRepository extends JpaRepository<TCommentM, Long> {
    String SELECT_BY_POSTID = "select a.id, a.post_id, a.contents, " +
            " a.crt_id, u1.user_nm as crt_nm, a.crt_dt, " +
            " a.mdf_id, u2.user_nm as mdf_nm, a.mdf_dt " +
            " from t_comment_m a " +
            " left join t_user_m u1 " +
            " on a.crt_id = u1.user_id " +
            " left join t_user_m u2 " +
            " on a.mdf_id = u2.user_id " +
            " where a.post_id = ?1";
    
    /**
     * <pre>
     * 댓글 목록 조회
     * CommentDto 에 정의된 column 은 전부 조회해야 함.
     * </pre>
     * 
     * @param postId
     * @return
     */
    @Query(value = SELECT_BY_POSTID, nativeQuery = true)
    public List<CommentDto> findAllByPostId(Long postId);
    
    @Query(value = SELECT_BY_POSTID, nativeQuery = true)
    public Page<TCommentM> findByPostId(Long postId, PageRequest pageRequest);
}