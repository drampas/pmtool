package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.Backlog;
import drampas.springframework.pmtool.domain.ProjectTask;
import drampas.springframework.pmtool.exeptions.ProjectNotFoundException;
import drampas.springframework.pmtool.repositories.BacklogRepository;
import drampas.springframework.pmtool.repositories.ProjectTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectTaskService {
    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;
    private final ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier,ProjectTask task,String username){
        //project service checks if the project belongs to the user trying to access it
        Backlog backlog=projectService.findProjectByIdentifier(projectIdentifier,username).getBacklog();
        backlog.setPTsequence(backlog.getPTsequence()+1);

        task.setBacklog(backlog);
        task.setProjectSequence(projectIdentifier+"-"+backlog.getPTsequence());
        task.setProjectIdentifier(projectIdentifier);

        //initial priority when priority is not provided
        //initial status when status is not provided
        if(task.getStatus()==null || task.getStatus().isBlank()){
            task.setStatus("TO_DO");
        }
        //should be handled by the front end form,or I should refactor priority into an enum
        if(task.getPriority()==null || task.getPriority()==0){
            task.setPriority(3);
        }
        return projectTaskRepository.save(task);
    }
    public Iterable<ProjectTask> getProjectTasks(String backlogId,String username){
        Backlog backlog=projectService.findProjectByIdentifier(backlogId,username).getBacklog();
        return backlog.getProjectTasks();
    }

    public ProjectTask getPtByPtSequence(String backlogId,String ptSequence,String username){
        //again using the project service to check if the project belongs to the user trying to access it
        projectService.findProjectByIdentifier(backlogId,username);

        ProjectTask task=projectTaskRepository.findByProjectSequence(ptSequence);
        if(backlogRepository.findByProjectIdentifier(backlogId)==null){
            throw new ProjectNotFoundException("Project "+backlogId+" does not exist");
        }
        if(task==null){
            throw new ProjectNotFoundException("Project task "+ptSequence+" does not exist");
        }
        if (!task.getProjectIdentifier().equals(backlogId)){
            throw new ProjectNotFoundException("Project task "+ptSequence+" does not exist in project "+backlogId);
        }
        return projectTaskRepository.findByProjectSequence(ptSequence);
    }

    public ProjectTask updateProjectTask(String backlogId,String ptSequence,ProjectTask task,String username ){
        ProjectTask taskToUpdate=getPtByPtSequence(backlogId,ptSequence,username);
        taskToUpdate=task;
        return projectTaskRepository.save(taskToUpdate);
    }

    public void deleteProjectTask(String backlogId,String ptSequence,String username){
        ProjectTask task=getPtByPtSequence(backlogId,ptSequence,username);
        projectTaskRepository.delete(task);
    }
}
