package drampas.springframework.pmtool.web;

import drampas.springframework.pmtool.domain.Project;
import drampas.springframework.pmtool.services.ProjectService;
import drampas.springframework.pmtool.services.ValidationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService projectService;
    private final ValidationService validationService;

    public ProjectController(ProjectService projectService, ValidationService validationService) {
        this.projectService = projectService;
        this.validationService = validationService;
    }
    @PostMapping("")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result){
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        Project savedProject=projectService.saveOrUpdate(project);
        return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable String projectId){
        return new ResponseEntity<Project>(projectService.findProjectByIdentifier(projectId),HttpStatus.OK);
    }
}
