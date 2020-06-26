package pl.mwprojects.pmapp.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
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

    @Query(value = "SELECT * FROM assignments ORDER BY assignment_stop ASC",
            nativeQuery = true)
    List<Assignment> findAllAssignmentsOrderByAssignmentStopAsc();

    @Query(value = "SELECT * FROM assignments INNER JOIN projects ON assignments.project_id = projects.id WHERE (assignments.assignment_name = ?1 OR projects.project_number = ?2 OR projects.project_name = ?3) ORDER BY assignments.assignment_stop ASC",
            nativeQuery = true)
    List<Assignment> findAllByAssignmentNameOrProjectNumberOrProjectNameOrderByAssignmentStopAsc(String assignmentName, Long projectNumber, String projectName);

    @Query(value = "SELECT * FROM assignments_users INNER JOIN assignments ON assignments_users.assignments_id = assignments.id WHERE users_id = ?1 ORDER BY assignments.assignment_stop ASC",
            nativeQuery = true)
    List<Assignment> findAllAssignmentsByUserIdOrderByAssignmentStopAsc(Long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM assignments WHERE project_id= ?1",
            nativeQuery = true)
    void deleteAssignmentByProjectId(Long projectId);

    @Query(value = "SELECT * FROM assignments WHERE project_id = ?1",
            nativeQuery = true)
    List<Assignment> findAllAssignmentsByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM assignments_users WHERE assignments_id= ?1",
            nativeQuery = true)
    void deleteAssignmentFromAssignment_User(Long assignmentId);

    Optional<Assignment> findAssignmentById(Long assignmentId);

}
