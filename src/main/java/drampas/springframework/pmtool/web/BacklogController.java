package drampas.springframework.pmtool.web;

import drampas.springframework.pmtool.domain.ProjectTask;
import drampas.springframework.pmtool.services.ProjectTaskService;
import drampas.springframework.pmtool.services.ValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    private final ValidationService validationService;
    private final ProjectTaskService projectTaskService;

    public BacklogController(ValidationService validationService, ProjectTaskService projectTaskService) {
        this.validationService = validationService;
        this.projectTaskService = projectTaskService;
    }

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTask(@PathVariable String backlogId, @Valid @RequestBody ProjectTask task
            , BindingResult result){
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        ProjectTask savedTask=projectTaskService.addProjectTask(backlogId,task);
        return new ResponseEntity<ProjectTask>(savedTask, HttpStatus.CREATED);
    }
    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getProjectTasks(@PathVariable String backlogId){
        return projectTaskService.getProjectTasks(backlogId);
    }
}
