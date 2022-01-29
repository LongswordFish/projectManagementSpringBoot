package ca.wonderfish.projectmanager.web;

import ca.wonderfish.projectmanager.domain.Project;
import ca.wonderfish.projectmanager.exceptions.ProjectIdException;
import ca.wonderfish.projectmanager.services.MapValidationErrorService;
import ca.wonderfish.projectmanager.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result){

        ResponseEntity<?> hasErrors= mapValidationErrorService.MapValidationService(result);
        if(hasErrors==null){
            projectService.saveOrUpdateProject(project);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + project.getProjectIdentifier()).build().toUri();
           // return new ResponseEntity<Object>(uri, HttpStatus.CREATED);
            return ResponseEntity.created(uri).build();
        }else{
            return hasErrors;
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable String projectId){
        return new ResponseEntity<Project>(projectService.findProjectById(projectId),HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Project>  getAllProjects(){
        return projectService.findAllProjects();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<String> deleteProjectById(@PathVariable String projectId){

        projectService.deleteProjectByProjectId(projectId);
        return new ResponseEntity<String>("Project with project identifier of "+projectId+" has been deleted.",HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result,@PathVariable String projectId){

        ResponseEntity<?> hasErrors= mapValidationErrorService.MapValidationService(result);
        if(hasErrors==null){
            Long id = projectService.findProjectById(projectId).getId();
            project.setId(id);
            projectService.saveOrUpdateProject(project);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/" + project.getProjectIdentifier()).build().toUri();
            // return new ResponseEntity<Object>(uri, HttpStatus.CREATED);
            return ResponseEntity.created(uri).build();
        }else{
            return hasErrors;
        }
    }
}
