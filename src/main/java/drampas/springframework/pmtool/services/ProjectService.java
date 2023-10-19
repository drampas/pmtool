package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.Backlog;
import drampas.springframework.pmtool.domain.PmUser;
import drampas.springframework.pmtool.domain.Project;
import drampas.springframework.pmtool.exeptions.ProjectIdentifierException;
import drampas.springframework.pmtool.exeptions.ProjectNotFoundException;
import drampas.springframework.pmtool.repositories.BacklogRepository;
import drampas.springframework.pmtool.repositories.PmUserRepository;
import drampas.springframework.pmtool.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BacklogRepository backlogRepository;
    private final PmUserRepository pmUserRepository;

    public Project saveOrUpdate(Project project, String username) {
        try {
            PmUser user=pmUserRepository.findByUsername(username);
            project.setUser(user);
            project.setProjectLeader(user.getUsername());

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
    public Project updateProject(Project project, String username){
        if(project.getId()!=null){
            Optional<Project> optionalProject=projectRepository.findById(project.getId());
            if(optionalProject.isPresent()){
                Project existingProject=optionalProject.get();
                if(!existingProject.getUser().getUsername().equals(username)){
                    throw new ProjectNotFoundException("Project "+project.getProjectIdentifier()+" not found in your account");
                }
            }else throw new ProjectNotFoundException("Project "+project.getProjectIdentifier()+" does not exist");

        }
        return saveOrUpdate(project,username);
    }
    public Project findProjectByIdentifier(String identifier,String username){
        Project returnedProject=projectRepository.findByProjectIdentifier(identifier);
        if(returnedProject==null){
            throw new ProjectNotFoundException("Project "+identifier+" does not exist");
        }
        if(!returnedProject.getProjectLeader().equals(username)){
            throw new ProjectNotFoundException("Project "+identifier+" not found in your account");
        }
        return returnedProject;
    }
    public Iterable<Project> findAllProjects(String username){
        return projectRepository.findByProjectLeader(username);
    }
    public void deleteProjectByIdentifier(String projectIdentifier,String username){
        Project returnedProject=findProjectByIdentifier(projectIdentifier,username);
        projectRepository.delete(returnedProject);
    }
}
