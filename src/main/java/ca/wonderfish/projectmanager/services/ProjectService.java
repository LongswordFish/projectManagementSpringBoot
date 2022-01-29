package ca.wonderfish.projectmanager.services;

import ca.wonderfish.projectmanager.domain.Project;
import ca.wonderfish.projectmanager.exceptions.ProjectIdException;
import ca.wonderfish.projectmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch(Exception e){
            throw new ProjectIdException("Project identifier - "+project.getProjectIdentifier()+" already exists.");
        }
    }

    public Project findProjectById(String projectId){
        Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
        if(project!=null){
            return project;
        }else{
            throw new ProjectIdException("Project identifier - "+projectId+" doesn't exist.");
        }
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProjectByProjectId(String projectId){
        Project project = projectRepository.findProjectByProjectIdentifier(projectId.toUpperCase());
        if(project==null){
            throw new ProjectIdException("Project identifier - "+projectId+" doesn't exist.");
        }else{
            projectRepository.deleteById(project.getId());
        }
    }

}
