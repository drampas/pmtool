package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.Project;
import drampas.springframework.pmtool.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdate(Project project){
        return projectRepository.save(project);
    }
}
