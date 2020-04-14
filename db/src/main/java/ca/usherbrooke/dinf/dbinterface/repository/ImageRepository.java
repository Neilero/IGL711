package ca.usherbrooke.dinf.dbinterface.repository;

import ca.usherbrooke.dinf.dbinterface.model.DockerImage;
import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<DockerImage, Integer> {
    DockerImage findById(UUID id);
    List<DockerImage> findAll();
    List<DockerImage> findByWorker(Worker worker);
    void deleteById(UUID id);
}
