package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {
    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/")
    List<Worker> getAllWorkers()
    {
        return workerRepository.findAll();
    }

    @GetMapping("/{id}")
    Worker getWorkerById(@PathVariable UUID id)
    {
        return workerRepository.findById(id);
    }

    @PostMapping("/")
    Worker addWorker(@RequestBody Worker newWorker)
    {
        return workerRepository.save(newWorker);
    }

    @PutMapping("/{id}")
    Worker editWorker(@RequestBody Worker newWorker, @PathVariable UUID id)
    {
        Worker worker = workerRepository.findById(id);

        if (worker != null)
        {
            worker.setAddress(newWorker.getAddress());
            worker.setPort(newWorker.getPort());
            worker.setStatus(newWorker.getStatus());
            worker.setPorts(newWorker.getPorts());
            worker.setImages(newWorker.getImages());
        }
        else
        {
            worker = newWorker;
            worker.setId(id);
        }

        return workerRepository.save(worker);
    }

    @DeleteMapping("/{id}")
    void deleteWorker(@PathVariable UUID id)
    {
        workerRepository.deleteById(id);
    }
}
