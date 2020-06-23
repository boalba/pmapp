package pl.mwprojects.pmapp.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Optional<Team> findTeamByTeamName(String teamName);

    @Query(value = "SELECT * FROM teams ORDER BY team_name ASC",
            nativeQuery = true)
    List<Team> findAllTeamsOrderedByTeamNameASC();

    @Query(value = "SELECT * FROM teams INNER JOIN teams_users ON teams.id = teams_users.team_id WHERE teams_users.users_id = ?1",
            nativeQuery = true)
    Optional<Team> findTeamByUserId(Long userId);

    @Query(value = "SELECT * FROM teams WHERE team_leader_id = ?1",
            nativeQuery = true)
    Optional<Team> findTeamByTeamLeaderId(Long teamLeaderId);

    @Query(value = "SELECT * FROM teams INNER JOIN teams_projects ON teams.id = teams_projects.team_id WHERE teams_projects.projects_id = ?1",
            nativeQuery = true)
    Optional<Team> findTeamByProjectsId(Long projectId);

    @Modifying
    @Query(value = "DELETE FROM teams_users WHERE teams_users.users_id = ?1",
            nativeQuery = true)
    void deleteUserFromTeamByUserId(Long userId);

    @Modifying
    @Query(value = "UPDATE teams SET team_leader_id = NULL WHERE team_leader_id = ?1",
            nativeQuery = true)
    void deleteTeamLeaderFromTeamByUserId(Long userId);

}
