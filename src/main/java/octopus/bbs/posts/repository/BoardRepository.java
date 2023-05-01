package octopus.bbs.posts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import octopus.bbs.posts.dto.TBoardM;

// @Repository : JpaRepository를 사용하면 @Repository를 사용하지 않아도 됨.
public interface BoardRepository extends JpaRepository<TBoardM, Long> {
    String UPDATE_CNT = "UPDATE TBoardM a" +
            "   SET a.readCnt = a.readCnt + 1 " +
            " WHERE a.id = ?1";
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    // @Query(value=UPDATE_CNT, nativeQuery=true)
    @Query(value = UPDATE_CNT)
    int updateCnt(Long id);
}