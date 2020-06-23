package pl.mwprojects.pmapp.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findProjectByProjectNumber(Long projectNumber);

    @Query(value = "SELECT * FROM projects ORDER BY project_number ASC",
            nativeQuery = true)
    List<Project> findAllProjectsOrderedByProjectNumberASC();

    Optional<Project> findProjectById(Long id);

    @Query(value = "SELECT * FROM projects INNER JOIN projects_users ON projects.id = projects_users.project_id WHERE projects_users.users_id = ?1 ORDER BY project_number ASC",
            nativeQuery = true)
    List<Project> findAllProjectsByUserIdOrderedByProjectNumberASC(Long userId);

    @Query(value = "SELECT * FROM projects WHERE NOT EXISTS (SELECT * FROM teams_projects WHERE projects.id = teams_projects.projects_id)",
            nativeQuery = true)
    List<Project> findAllProjectsWithoutTeamId();

    @Modifying
    @Query(value = "DELETE FROM projects_users WHERE projects_users.users_id = ?1",
            nativeQuery = true)
    void deleteUserFromProjectByUserId(Long userId);

    @Query(value = "SELECT * FROM projects INNER JOIN teams_projects ON projects.id = teams_projects.projects_id WHERE teams_projects.team_id = ?1",
            nativeQuery = true)
    List<Project> findAllProjectsOfCurrentTeam(int teamId);

    @Modifying
    @Query(value = "DELETE FROM projects_users WHERE projects_users.project_id = ?1",
            nativeQuery = true)
    void deleteProjectFromUserByProjectId(Long projectId);

    @Modifying
    @Query(value = "DELETE FROM teams_projects WHERE teams_projects.projects_id = ?1",
            nativeQuery = true)
    void deleteProjectFromTeamByProjectId(Long projectId);

    List<Project> findAllByProjectNumberOrHashOrProjectNameOrderByProjectNumberAsc(Long projectNumber, String hash, String projectName);

}
