package orchestrus.rest.controllers;

import orchestrus.rest.dto.DockerImageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DockerImageController {

	@PostMapping( path = RESTRoute.START_IMAGE )
	@ResponseBody
	public ResponseEntity<DockerImageDTO> startImage( @RequestBody DockerImageDTO image ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping( path = orchestrus.rest.controllers.RESTRoute.STOP_IMAGE )
	@ResponseBody
	public ResponseEntity<DockerImageDTO> stopImage( @RequestBody DockerImageDTO image ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping( path = orchestrus.rest.controllers.RESTRoute.UPLOAD_IMAGE )
	public ResponseEntity<DockerImageDTO> uploadImage( @RequestParam( name = "file" ) MultipartFile dockerfile,
													   @RequestBody DockerImageDTO imageInfo ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}
}
