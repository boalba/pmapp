package pl.mwprojects.pmapp.personDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PersonalDetailsRepository extends JpaRepository<PersonDetails, Long> {

    @Query(value = "SELECT * FROM person_details ORDER BY sure_name ASC",
            nativeQuery = true)
    List<PersonDetails> findAllPeopleInAlphabeticalOrder();

    @Query(value = "SELECT * FROM person_details INNER JOIN projects_users ON person_details.user_id = projects_users.users_id WHERE project_id = ?1",
            nativeQuery = true)
    List<PersonDetails> findAllPeopleByProjectId(Long id);
}
