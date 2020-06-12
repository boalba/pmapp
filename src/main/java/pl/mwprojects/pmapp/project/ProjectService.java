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
}
