package pl.mwprojects.pmapp.assignment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

}
