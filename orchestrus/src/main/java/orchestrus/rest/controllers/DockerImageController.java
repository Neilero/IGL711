package orchestrus.rest.controllers;

import orchestrus.rest.dto.BasicResponse;
import orchestrus.rest.dto.DockerImageDTO;
import orchestrus.rest.dto.WorkerDTO;
import orchestrus.services.DockerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class DockerImageController {

	private final DockerImageService dockerImageService;


	public DockerImageController() {
		this.dockerImageService = new DockerImageService();
	}


	@GetMapping( path = RESTRoute.IMAGES )
	@ResponseBody
	public ResponseEntity<List<DockerImageDTO>> getAllDockerImages() {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@GetMapping( path = RESTRoute.IMAGE )
	@ResponseBody
	public ResponseEntity<DockerImageDTO> getDockerImage( @RequestParam( name = "id" ) int imageId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping( path = RESTRoute.IMAGE )
	@ResponseBody
	public ResponseEntity<BasicResponse> uploadImage( @RequestParam( name = "file" ) MultipartFile dockerfile,
													  @RequestBody DockerImageDTO imageInfo ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping( path = RESTRoute.START_IMAGE )
	@ResponseBody
	public ResponseEntity<BasicResponse> startImage( @RequestBody DockerImageDTO image ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping( path = RESTRoute.STOP_IMAGE )
	@ResponseBody
	public ResponseEntity<BasicResponse> stopImage( @RequestBody DockerImageDTO image ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}
}
