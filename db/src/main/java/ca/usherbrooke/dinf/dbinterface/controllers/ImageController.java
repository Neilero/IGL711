package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.DockerImage;
import ca.usherbrooke.dinf.dbinterface.repository.ImageRepository;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/images")
public class ImageController {
    @Autowired
    ImageRepository imageRepository;

    @Autowired
    WorkerRepository workerRepository;

    @GetMapping("/worker/{id}")
    public ResponseEntity<List<DockerImage>> getImagesByWorker(@PathVariable UUID id)
    {
        return new ResponseEntity<>(imageRepository.findByWorker(workerRepository.findById(id)), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<DockerImage>> getImages()
    {
        return new ResponseEntity<>(imageRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<DockerImage> getImageById(@PathVariable UUID id)
    {
        DockerImage image = imageRepository.findById(id);

        if (image == null)
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PostMapping("/")
    ResponseEntity<DockerImage> addImage(@RequestBody DockerImage newImage)
    {
        if (newImage == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(imageRepository.save(newImage), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<DockerImage> updateImage(@RequestBody DockerImage newImage, @PathVariable UUID id)
    {
        if (newImage == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        DockerImage image = imageRepository.findById(id);

        if (image != null)
        {
            image.setName(newImage.getName());
            image.setWorker(newImage.getWorker());
        }
        else
        {
            image = newImage;
            image.setId(id);
        }

        return new ResponseEntity<>(imageRepository.save(image), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<SimpleResponse> deleteImage(@PathVariable UUID id)
    {
        DockerImage image = imageRepository.findById(id);

        if (image == null)
            return new ResponseEntity<>(new SimpleResponse(false, "Image cannot be deleted"), HttpStatus.ACCEPTED);
        else
        {
            imageRepository.delete(image);
            return new ResponseEntity<>(new SimpleResponse(true, "Image has been deleted"), HttpStatus.OK);
        }
    }
}
