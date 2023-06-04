package octopus.basic.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import octopus.basic.user.dto.TUserM;

// @Repository : JpaRepository를 사용하면 @Repository를 사용하지 않아도 됨.
public interface UserRepository extends JpaRepository<TUserM, String> {
    String SELECT_BY_USERID = "select a.* from t_user_m a " +
            " where a.user_id = ?1";
    
    @Query(value = SELECT_BY_USERID, nativeQuery = true)
    public TUserM findByUserId(String userId);
}