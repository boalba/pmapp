package pl.mwprojects.pmapp.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    Optional<Assignment> findAssignmentByAssignmentName(String assignmentName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM assignments_users WHERE users_id= ?1",
            nativeQuery = true)
    void deleteUserFromAssignmentByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE assignments SET project_id = NULL WHERE project_id= ?1",
            nativeQuery = true)
    void deleteProjectFromAssignmentByProjectId(Long projectId);

}
