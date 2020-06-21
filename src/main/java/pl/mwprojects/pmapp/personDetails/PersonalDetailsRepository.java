package pl.mwprojects.pmapp.personDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface PersonalDetailsRepository extends JpaRepository<PersonDetails, Long> {

    @Query(value = "SELECT * FROM person_details ORDER BY sure_name ASC",
            nativeQuery = true)
    List<PersonDetails> findAllPeopleInAlphabeticalOrder();

    @Query(value = "SELECT * FROM person_details INNER JOIN projects_users ON person_details.user_id = projects_users.users_id WHERE projects_id = ?1",
            nativeQuery = true)
    List<PersonDetails> findAllPeopleByProjectId(Long projectId);

    @Query(value = "SELECT * FROM person_details WHERE NOT EXISTS (SELECT * FROM teams_users WHERE person_details.user_id = teams_users.users_id) AND NOT EXISTS (SELECT * FROM teams WHERE person_details.user_id = teams.team_leader_id)",
            nativeQuery = true)
    List<PersonDetails> findAllPeopleWithoutTeamId();

    @Modifying
    @Query(value = "DELETE FROM person_details WHERE person_details.user_id = ?1",
            nativeQuery = true)
    void deleteUserFromPersonDetailsByUserId(Long userId);

    @Query(value = "SELECT * FROM person_details INNER JOIN teams ON person_details.user_id = teams.team_leader_id WHERE teams.id = ?1",
            nativeQuery = true)
    Optional<PersonDetails> findPersonDetailsOfCurrentTeamLeader(int teamId);

    @Query(value = "SELECT * FROM person_details INNER JOIN teams_users ON person_details.user_id = teams_users.users_id WHERE teams_users.team_id = ?1",
            nativeQuery = true)
    List<PersonDetails> findAllPersonDetailsOfCurrentTeam(int teamId);

}
