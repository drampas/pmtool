package drampas.springframework.pmtool.repositories;

import drampas.springframework.pmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask,Long> {

    ProjectTask findByProjectSequence(String projectSequence);
}
