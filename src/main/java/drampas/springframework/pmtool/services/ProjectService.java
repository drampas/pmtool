package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.Backlog;
import drampas.springframework.pmtool.domain.Project;
import drampas.springframework.pmtool.exeptions.ProjectIdentifierException;
import drampas.springframework.pmtool.exeptions.ProjectNotFoundException;
import drampas.springframework.pmtool.repositories.BacklogRepository;
import drampas.springframework.pmtool.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;

    public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    public Project saveOrUpdate(Project project) {
        try {
            if(project.getId()==null){
                Backlog backlog=new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier());
            }
            if(project.getBacklog()==null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier()));
            }
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdentifierException("Project id:"+project.getProjectIdentifier()+" is already being used");
        }
    }
    public Project findProjectByIdentifier(String identifier){
        Project returnedProject=projectRepository.findByProjectIdentifier(identifier);
        if(returnedProject==null){
            throw new ProjectNotFoundException("Project "+identifier+" does not exist");
        }
        return projectRepository.findByProjectIdentifier(identifier);
    }
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }
    public void deleteProjectByIdentifier(String projectIdentifier){
        Project returnedProject=projectRepository.findByProjectIdentifier(projectIdentifier);
        if(returnedProject==null){
            throw new ProjectNotFoundException("Project "+projectIdentifier+" does not exist");
        }
        projectRepository.delete(returnedProject);
    }
}
