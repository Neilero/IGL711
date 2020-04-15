package ca.usherbrooke.dinf.dbinterface.controllers;

import ca.usherbrooke.dinf.dbinterface.model.DockerImage;
import ca.usherbrooke.dinf.dbinterface.model.Worker;
import ca.usherbrooke.dinf.dbinterface.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ca.usherbrooke.dinf.dbinterface.repository.ImageRepository;

import javax.annotation.Resource;
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
    public List<DockerImage> getImagesByWorker(@PathVariable UUID id)
    {
        return imageRepository.findByWorker(workerRepository.findById(id));
    }

    @GetMapping("/")
    public List<DockerImage> getImages()
    {
        return imageRepository.findAll();
    }

    @GetMapping("/{id}")
    DockerImage getImageById(@PathVariable UUID id)
    {
        return imageRepository.findById(id);
    }

    @PostMapping("/")
    DockerImage addImage(@RequestBody DockerImage newImage)
    {
        return imageRepository.save(newImage);
    }

    @PutMapping("/{id}")
    DockerImage updateImage(@RequestBody DockerImage newImage, @PathVariable UUID id)
    {
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

        return imageRepository.save(image);
    }

    @DeleteMapping("/{id}")
    void deleteImage(@PathVariable UUID id)
    {
        imageRepository.deleteById(id);
    }
}
