package ca.wonderfish.projectmanager.repository;

import ca.wonderfish.projectmanager.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

        Project findProjectByProjectIdentifier(String projectId);

        @Override
        Iterable<Project> findAll();
}
