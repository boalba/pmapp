package pl.mwprojects.pmapp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE NOT EXISTS (SELECT * FROM person_details WHERE person_details.user_id = users.id)",
            nativeQuery = true)
    List<User> findUsersWithoutPersonalDetails();

    @Query(value = "SELECT * FROM users INNER JOIN projects_users ON users.id = projects_users.users_id WHERE project_id = ?1)",
            nativeQuery = true)
    List<User> findAllUsersByProjectId(Long projectId);

}
