package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
public class WorkerController {
    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/workers")
    List<Worker> getAllWorkers()
    {
        return workerRepository.findAll();
    }

    @GetMapping("/workers/{id}")
    Worker getWorkerById(@PathVariable UUID id)
    {
        return workerRepository.findById(id);
    }

    @PostMapping("/workers")
    Worker addWorker(@RequestBody Worker newWorker)
    {
        return workerRepository.save(newWorker);
    }

    @DeleteMapping("/workers/{id}")
    void deleteWorker(@PathVariable UUID id)
    {
        workerRepository.deleteById(id);
    }
}
