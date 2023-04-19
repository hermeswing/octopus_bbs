package octopus.bbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import octopus.bbs.dto.TBoardM;

// @Repository : JpaRepository를 사용하면 @Repository를 사용하지 않아도 됨.
public interface BoardRepository extends JpaRepository<TBoardM, Long> {
}