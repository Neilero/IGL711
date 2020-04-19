package ca.usherbrooke.dinf.dbinterface.repository;

import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface WorkerRepository extends JpaRepository<Worker, Integer>
{
    Worker findById(UUID id);
    List<Worker> findAll();

    @Transactional
    void deleteWorkerById(UUID id);
}
