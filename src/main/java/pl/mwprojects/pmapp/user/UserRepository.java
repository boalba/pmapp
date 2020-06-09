package pl.mwprojects.pmapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE NOT EXISTS (SELECT * FROM person_details WHERE person_details.user_id = users.id)",
            nativeQuery = true)
    List<User> findUsersWithoutPersonalDetails();

}
