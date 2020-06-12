package pl.mwprojects.pmapp.project;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

    public Project findProjectByProjectNumber(Long projectNumber){
        return projectRepository.findProjectByProjectNumber(projectNumber);
    }

    public List<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public List<Project> findAllProjectsOrderedByProjectNumberASC(){
        return projectRepository.findAllProjectsOrderedByProjectNumberASC();
    }

    public Project findProjectById(Long id){
        return projectRepository.findProjectById(id);
    }
}
