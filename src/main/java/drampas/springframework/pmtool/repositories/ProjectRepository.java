package drampas.springframework.pmtool.repositories;

import drampas.springframework.pmtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {

    Project findByProjectIdentifier(String projectIdentifier);
    Iterable<Project> findByProjectLeader(String username);
}
