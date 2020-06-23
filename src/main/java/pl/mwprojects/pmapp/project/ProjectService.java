package pl.mwprojects.pmapp.project;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void saveProject(Project project){
        projectRepository.save(project);
    }

    public Optional<Project> findProjectByProjectNumber(Long projectNumber){
        return projectRepository.findProjectByProjectNumber(projectNumber);
    }

    public List<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public List<Project> findAllProjectsOrderedByProjectNumberASC(){
        return projectRepository.findAllProjectsOrderedByProjectNumberASC();
    }

    public Optional<Project> findProjectById(Long id){
        return projectRepository.findProjectById(id);
    }

    public List<Project> findAllProjectsByUserId(Long userId){
        return projectRepository.findAllProjectsByUserIdOrderedByProjectNumberASC(userId);
    }

    public void deleteProject(Project project){
        projectRepository.delete(project);
    }

    public List<Project> findAllProjectsWithoutTeamId(){
        return projectRepository.findAllProjectsWithoutTeamId();
    }

    public void deleteUserFromProjectByUserId(Long userId){
        projectRepository.deleteUserFromProjectByUserId(userId);
    }

    public List<Project> findAllProjectsOfCurrentTeam(int teamId){
        return projectRepository.findAllProjectsOfCurrentTeam(teamId);
    }

    public void deleteProjectFromUserByProjectId(Long projectId){
        projectRepository.deleteProjectFromUserByProjectId(projectId);
    }

    public void deleteProjectFromTeamByProjectId(Long projectId){
        projectRepository.deleteProjectFromTeamByProjectId(projectId);
    }

    public List<Project> findAllByProjectNumberOrHashOrProjectNameOrderByProjectNumberAsc(Long projectNumber, String hash, String projectName){
        return projectRepository.findAllByProjectNumberOrHashOrProjectNameOrderByProjectNumberAsc(projectNumber, hash, projectName);
    }

}
