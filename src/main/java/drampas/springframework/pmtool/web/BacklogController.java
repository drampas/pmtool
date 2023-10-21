package drampas.springframework.pmtool.web;

import drampas.springframework.pmtool.domain.ProjectTask;
import drampas.springframework.pmtool.services.ProjectTaskService;
import drampas.springframework.pmtool.services.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
@RequiredArgsConstructor
public class BacklogController {

    private final ValidationService validationService;
    private final ProjectTaskService projectTaskService;

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTask(@PathVariable String backlogId, @Valid @RequestBody ProjectTask task
            , BindingResult result, Principal principal){
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        ProjectTask savedTask=projectTaskService.addProjectTask(backlogId,task, principal.getName());
        return new ResponseEntity<ProjectTask>(savedTask, HttpStatus.CREATED);
    }
    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getProjectTasks(@PathVariable String backlogId,Principal principal){
        return projectTaskService.getProjectTasks(backlogId,principal.getName());
    }
    @GetMapping("/{backlogId}/{ptSequence}")
    public ResponseEntity<?> getProjectTaskByPtSequence(@PathVariable String backlogId,
                                                        @PathVariable String ptSequence,Principal principal){
        ProjectTask task=projectTaskService.getPtByPtSequence(backlogId, ptSequence,principal.getName());
        return new ResponseEntity<ProjectTask>(task,HttpStatus.OK);
    }
    @PatchMapping("/{backlogId}/{ptSequence}")
    public ResponseEntity<?> updateProjectTask(@PathVariable String backlogId,@PathVariable String ptSequence
                                                ,@Valid @RequestBody ProjectTask task,BindingResult result,Principal principal){
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        ProjectTask returnedTask=projectTaskService.updateProjectTask(backlogId,ptSequence,task,principal.getName());
        return new ResponseEntity<ProjectTask>(returnedTask,HttpStatus.OK);
    }
    @DeleteMapping("/{backlogId}/{ptSequence}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId,@PathVariable String ptSequence,Principal principal){
        projectTaskService.deleteProjectTask(backlogId, ptSequence, principal.getName());
        return new ResponseEntity<String>("Project Task "+ptSequence+" was deleted successfully",HttpStatus.OK);
    }
}
