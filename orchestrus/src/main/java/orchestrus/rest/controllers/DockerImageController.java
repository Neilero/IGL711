package orchestrus.rest.controllers;

import orchestrus.rest.dto.DockerImageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class DockerImageController {

	@PostMapping(path = RESTRoute.START_IMAGE)
	@ResponseBody
	public ResponseEntity<DockerImageDTO> startImage( @RequestParam( name = "id") int imageId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping(path = orchestrus.rest.controllers.RESTRoute.STOP_IMAGE)
	@ResponseBody
	public ResponseEntity<DockerImageDTO> stopImage( @RequestParam(name = "id") int imageId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping(path = orchestrus.rest.controllers.RESTRoute.UPLOAD_IMAGE)
	public ResponseEntity<DockerImageDTO> uploadImage( @RequestParam(name = "file") MultipartFile dockerfile ) {
		// TODO : check if parameters for executing the dockerfile are needed or if they should just be included in it
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}
}
