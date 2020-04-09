package orchestrus.controllers;

import orchestrus.dto.DockerImageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DockerImageController {

	@PostMapping(path = RESTRoute.START_IMAGE)
	@ResponseBody
	public ResponseEntity<DockerImageDTO> startImage( @RequestParam( name = "id") int imageId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping(path = RESTRoute.STOP_IMAGE)
	@ResponseBody
	public ResponseEntity<DockerImageDTO> stopImage( @RequestParam(name = "id") int imageId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping(path = RESTRoute.UPLOAD_IMAGE)
	public ResponseEntity<DockerImageDTO> uploadImage( @RequestParam(name = "file") MultipartFile dockerfile ) {
		// TODO : check if parameters for executing the dockerfile are needed or if they should just be included in it
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}
}
