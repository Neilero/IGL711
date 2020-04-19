package ca.usherbrooke.dinf.dbinterface.repository;

import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, Integer>
{
    Worker findById(UUID id);
    List<Worker> findAll();
    void deleteWorkerById(UUID id);
}
