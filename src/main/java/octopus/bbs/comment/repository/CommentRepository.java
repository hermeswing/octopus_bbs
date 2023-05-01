package octopus.bbs.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import octopus.bbs.comment.dto.TCommentM;

// @Repository : JpaRepository를 사용하면 @Repository를 사용하지 않아도 됨.
public interface CommentRepository extends JpaRepository<TCommentM, Long> {
}