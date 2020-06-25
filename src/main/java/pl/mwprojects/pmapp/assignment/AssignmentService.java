package pl.mwprojects.pmapp.assignment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AssignmentService {

    private AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }


    public Optional<Assignment> findAssignmentByAssignmentName(String assignmentName){
        return assignmentRepository.findAssignmentByAssignmentName(assignmentName);
    }

    public void saveAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
    }

    public void deleteUserFromAssignmentByUserId(Long userId){
        assignmentRepository.deleteUserFromAssignmentByUserId(userId);
    }

    public void deleteProjectFromAssignmentByProjectId(Long projectId){
        assignmentRepository.deleteProjectFromAssignmentByProjectId(projectId);
    }

    public List<Assignment> findAllAssignments(){
        return assignmentRepository.findAll();
    }

    public List<Assignment> findAllAssignmentsOrderByAssignmentStopAsc(){
        return assignmentRepository.findAllAssignmentsOrderByAssignmentStopAsc();
    }

    public List<Assignment> findAllByAssignmentNameOrProjectNumberOrProjectNameOrderByAssignmentStopAsc(String assignmentName, Long projectNumber, String projectName){
        return assignmentRepository.findAllByAssignmentNameOrProjectNumberOrProjectNameOrderByAssignmentStopAsc(assignmentName, projectNumber, projectName);
    }

    public List<Assignment> findAllAssignmentsByUserIdOrderByAssignmentStopAsc(Long userId){
        return assignmentRepository.findAllAssignmentsByUserIdOrderByAssignmentStopAsc(userId);
    }

}
