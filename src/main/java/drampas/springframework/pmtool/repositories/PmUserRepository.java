package drampas.springframework.pmtool.repositories;

import drampas.springframework.pmtool.domain.PmUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PmUserRepository extends CrudRepository<PmUser,Long> {

    PmUser findByUsername(String username);
}
