package ca.usherbrooke.dinf.dbinterface.repository;

import ca.usherbrooke.dinf.dbinterface.model.OpenPort;
import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PortRepository extends JpaRepository<OpenPort, Integer> {
    OpenPort findById(UUID id);
    List<OpenPort> findAll();
    List<OpenPort> findByWorker(Worker worker);
    void delete(OpenPort port);
}
