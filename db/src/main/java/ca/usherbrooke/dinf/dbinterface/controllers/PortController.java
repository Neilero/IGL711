package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.OpenPort;
import ca.usherbrooke.dinf.dbinterface.repository.PortRepository;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<OpenPort>> getPortsByWorker(@PathVariable UUID id)
    {
        return new ResponseEntity<>(portRepository.findByWorker(workerRepository.findById(id)), HttpStatus.OK) ;
    }

    @GetMapping("/")
    public ResponseEntity<List<OpenPort>> getPorts()
    {
        return new ResponseEntity<>(portRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<OpenPort> getPortById(@PathVariable UUID id)
    {
        OpenPort port = portRepository.findById(id);

        if (port == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(port, HttpStatus.OK);
    }

    @PostMapping("/")
    ResponseEntity<OpenPort> addPort(@RequestBody OpenPort newPort)
    {
        if (newPort == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(portRepository.save(newPort), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<OpenPort> updatePort(@RequestBody OpenPort newPort, @PathVariable UUID id)
    {
        if (newPort == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

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

        return new ResponseEntity<>(portRepository.save(port), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SimpleResponse> deletePort(@PathVariable UUID id)
    {
        OpenPort port = portRepository.findById(id);

        if (port == null)
            return new ResponseEntity<>(new SimpleResponse(false, "Port cannot be deleted"), HttpStatus.ACCEPTED);
        else
        {
            portRepository.deleteOpenPortById(id);
            return new ResponseEntity<>(new SimpleResponse(true, "Port has been deleted"), HttpStatus.OK);
        }
    }
}
