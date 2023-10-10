package drampas.springframework.pmtool.services;

import drampas.springframework.pmtool.domain.Backlog;
import drampas.springframework.pmtool.domain.ProjectTask;
import drampas.springframework.pmtool.exeptions.ProjectNotFoundException;
import drampas.springframework.pmtool.repositories.BacklogRepository;
import drampas.springframework.pmtool.repositories.ProjectTaskRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskService {
    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;

    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier,ProjectTask task){

        Backlog backlog=backlogRepository.findByProjectIdentifier(projectIdentifier);
        //backlog.getProjectTasks().add(task);
        backlog.setPTsequence(backlog.getPTsequence()+1);
        task.setBacklog(backlog);
        task.setProjectSequence(projectIdentifier+"-"+backlog.getPTsequence());
        task.setProjectIdentifier(projectIdentifier);
        //backlogRepository.save(backlog);
        return projectTaskRepository.save(task);
    }
    public Iterable<ProjectTask> getProjectTasks(String backlogId){
        Backlog backlog=backlogRepository.findByProjectIdentifier(backlogId);
        return backlog.getProjectTasks();
    }

    public ProjectTask getPtByPtSequence(String backlogId,String ptSequence){
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

    public ProjectTask updateProjectTask(String backlogId,String ptSequence,ProjectTask task ){
        ProjectTask taskToUpdate=getPtByPtSequence(backlogId,ptSequence);
        taskToUpdate=task;
        return projectTaskRepository.save(taskToUpdate);
    }

    public void deleteProjectTask(String backlogId,String ptSequence){
        ProjectTask task=getPtByPtSequence(backlogId,ptSequence);
        projectTaskRepository.delete(task);
    }
}
