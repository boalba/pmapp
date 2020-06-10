package pl.mwprojects.pmapp.project;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
