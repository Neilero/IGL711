package orchestrus.rest.controllers;

import orchestrus.exception.OrchestrusException;
import orchestrus.rest.RESTRoute;
import orchestrus.rest.dto.BasicResponse;
import orchestrus.rest.dto.DockerImageDTO;
import orchestrus.services.DockerImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class DockerImageController {

	private final DockerImageService dockerImageService;


	public DockerImageController() {
		this.dockerImageService = DockerImageService.getInstance();
	}


	@GetMapping( path = RESTRoute.IMAGES )
	@ResponseBody
	public ResponseEntity<List<DockerImageDTO>> getAllDockerImages() {
		List<DockerImageDTO> dockerImages;

		try {
			dockerImages = dockerImageService.getAllDockerImages()
																  .stream()
																  .map( DockerImageDTO::new )
																  .collect( Collectors.toList() );
		}
		catch ( OrchestrusException e ) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
		}
		catch ( Exception e ) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
		}

		return new ResponseEntity<>( dockerImages, HttpStatus.OK );
	}

	@GetMapping( path = RESTRoute.IMAGE )
	@ResponseBody
	public ResponseEntity<DockerImageDTO> getDockerImage( @RequestParam( name = "id" ) UUID imageId ) {
		DockerImageDTO dockerImage;

		try {
			dockerImage = new DockerImageDTO( dockerImageService.getDockerImage( imageId ) );
		}
		catch ( OrchestrusException e ) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
		}
		catch ( Exception e ) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
		}

		return new ResponseEntity<>( dockerImage, HttpStatus.OK );
	}

	@PostMapping( path = RESTRoute.IMAGE )
	@ResponseBody
	public ResponseEntity<BasicResponse> uploadImage( @RequestParam( name = "file" ) MultipartFile dockerfile,
													  @RequestBody DockerImageDTO imageInfo ) {
		// Won't be implemented but could be used to add the possibility of starting image from dockerfile
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@PostMapping( path = RESTRoute.START_IMAGE )
	@ResponseBody
	public ResponseEntity<BasicResponse> startImage( @RequestBody DockerImageDTO image ) {
		BasicResponse response;
		HttpStatus    responseStatus;

		try {
			if ( dockerImageService.startImage( image.toModel() ) ) {
				String message = String.format( "The image %s has been successfully started in worker %s", image.name, image.worker.id );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.OK;
			}
			else {
				String message = String.format( "The image %s could not been started in worker %s", image.name, image.worker.id );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
		catch ( OrchestrusException e ) {
			String message = "An error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.BAD_REQUEST;
		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}

	@PostMapping( path = RESTRoute.STOP_IMAGE )
	@ResponseBody
	public ResponseEntity<BasicResponse> stopImage( @RequestBody DockerImageDTO image ) {
		BasicResponse response;
		HttpStatus    responseStatus;

		try {
			if ( dockerImageService.stopImage( image.toModel() ) ) {
				String message = String.format( "The image %s has been successfully stopped in worker %s", image.name, image.worker.id );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.OK;
			}
			else {
				String message = String.format( "The image %s could not been stopped in worker %s", image.name, image.worker.id );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
		catch ( OrchestrusException e ) {
			String message = "An error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.BAD_REQUEST;
		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}
}
