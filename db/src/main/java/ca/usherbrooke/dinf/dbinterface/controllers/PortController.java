package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.DockerImage;
import ca.usherbrooke.dinf.dbinterface.model.OpenPort;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.usherbrooke.dinf.dbinterface.repository.PortRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/ports")
public class PortController {
    @Autowired
    PortRepository portRepository;

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/worker/{id}")
    public List<OpenPort> getPortsByWorker(@PathVariable UUID id)
    {
        return portRepository.findByWorker(workerRepository.findById(id));
    }

    @GetMapping("/")
    public List<OpenPort> getPorts()
    {
        return portRepository.findAll();
    }

    @GetMapping("/{id}")
    OpenPort getPortById(@PathVariable UUID id)
    {
        return portRepository.findById(id);
    }

    @PostMapping("/")
    OpenPort addPort(@RequestBody OpenPort newPort)
    {
        return portRepository.save(newPort);
    }

    @PutMapping("/{id}")
    OpenPort updatePort(@RequestBody OpenPort newPort, @PathVariable UUID id)
    {
        OpenPort port = portRepository.findById(id);

        if (port != null)
        {
            port.setPort(newPort.getPort());
            port.setWorker(newPort.getWorker());
        }
        else
        {
            port = newPort;
            port.setId(id);
        }

        return portRepository.save(port);
    }

    @DeleteMapping("/{id}")
    void deletePort(@PathVariable UUID id)
    {
        portRepository.deleteById(id);
    }
}
