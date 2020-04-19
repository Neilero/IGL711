package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.Worker;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {
    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/")
    ResponseEntity<List<Worker>> getAllWorkers()
    {
        return new ResponseEntity<>(workerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Worker> getWorkerById(@PathVariable UUID id)
    {
        Worker worker = workerRepository.findById(id);

        if (worker == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(worker, HttpStatus.OK);
    }

    @PostMapping("/")
    ResponseEntity<Worker> addWorker(@RequestBody Worker newWorker)
    {
        if (newWorker == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(workerRepository.save(newWorker), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Worker> editWorker(@RequestBody Worker newWorker, @PathVariable UUID id)
    {
        if (newWorker == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Worker worker = workerRepository.findById(id);

        if (worker != null)
        {
            worker.setAddress(newWorker.getAddress());
            worker.setStatus(newWorker.getStatus());
            worker.setAccessPort(newWorker.getAccessPort());
            worker.setOpenPorts(newWorker.getOpenPorts());
            worker.setImages(newWorker.getImages());
        }
        else
        {
            worker = newWorker;
            worker.setId(id);
        }

        return new ResponseEntity<>(workerRepository.save(worker), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SimpleResponse> deleteWorker(@PathVariable UUID id)
    {
        Worker worker = workerRepository.findById(id);

        if (worker == null)
            return new ResponseEntity<>(new SimpleResponse(false, "Worker cannot be deleted"), HttpStatus.ACCEPTED);
        else
        {
            workerRepository.deleteWorkerById(id);
            return new ResponseEntity<>(new SimpleResponse(true, "Worker has been deleted"), HttpStatus.OK);
        }
    }
}
