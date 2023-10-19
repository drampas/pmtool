package drampas.springframework.pmtool.web;

import drampas.springframework.pmtool.domain.Project;
import drampas.springframework.pmtool.services.ProjectService;
import drampas.springframework.pmtool.services.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ValidationService validationService;

    @PostMapping("")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){
        ResponseEntity<?> errorMap=validationService.validationErrorMap(result);
        if(errorMap!=null){
            return errorMap;
        }
        Project savedProject=projectService.updateProject(project,principal.getName());
        return new ResponseEntity<Project>(savedProject, HttpStatus.CREATED);
    }
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable String projectId,Principal principal){
        return new ResponseEntity<Project>(projectService.findProjectByIdentifier(projectId,principal.getName()),HttpStatus.OK);
    }
    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProjects(principal.getName());
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId,Principal principal){
        projectService.deleteProjectByIdentifier(projectId,principal.getName());
        return new ResponseEntity<String>("Project "+projectId +" deleted",HttpStatus.OK);
    }

}
