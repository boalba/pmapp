package pl.mwprojects.pmapp.project;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
