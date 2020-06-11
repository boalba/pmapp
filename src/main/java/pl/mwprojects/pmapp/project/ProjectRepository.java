package pl.mwprojects.pmapp.project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findProjectByProjectNumber(Long projectNumber);

    @Query(value = "SELECT * FROM projects ORDER BY project_number ASC",
            nativeQuery = true)
    List<Project> findAllProjectsOrderedByProjectNumberASC();
}
