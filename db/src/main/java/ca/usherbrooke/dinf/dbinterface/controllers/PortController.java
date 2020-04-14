package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.OpenPort;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.usherbrooke.dinf.dbinterface.repository.PortRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
public class PortController {
    @Autowired
    PortRepository portRepository;

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/ports/worker/{id}")
    public List<OpenPort> getPortsByWorker(@PathVariable UUID id)
    {
        return portRepository.findByWorker(workerRepository.findById(id));
    }

    @GetMapping("/ports/{id}")
    OpenPort getPortById(@PathVariable UUID id)
    {
        return portRepository.findById(id);
    }

    @PostMapping("/ports")
    OpenPort updatePort(@RequestBody OpenPort newPort)
    {
        return portRepository.save(newPort);
    }

    @DeleteMapping("/ports/{id}")
    void deletePort(@PathVariable UUID id)
    {
        portRepository.deleteById(id);
    }
}
