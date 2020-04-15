package orchestrus.rest.controllers;

import orchestrus.exceptions.WorkerServiceException;
import orchestrus.model.Worker;
import orchestrus.rest.dto.BasicResponse;
import orchestrus.rest.dto.WorkerDTO;
import orchestrus.services.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class WorkerController {

	private final WorkerService workerService;


	public WorkerController() {
		workerService = new WorkerService();
	}


	@GetMapping( path = RESTRoute.WORKERS )
	@ResponseBody
	public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
		List<WorkerDTO> workers = workerService.getAllWorkers()
											   .stream()
											   .map( WorkerDTO::new )
											   .collect( Collectors.toList() );

		return new ResponseEntity<>( workers, HttpStatus.OK );
	}

	@GetMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<WorkerDTO> getWorker( @RequestParam( name = "id" ) int workerId ) {
		WorkerDTO worker = new WorkerDTO( workerService.getWorker( workerId ) );

		return new ResponseEntity<>( worker, HttpStatus.OK );
	}

	@PostMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<BasicResponse> addWorker( @RequestBody WorkerDTO worker ) {
		BasicResponse response;
		HttpStatus responseStatus;

		try {
			if ( workerService.addWorker( worker.address, worker.port ) ) {
				String message = String.format( "The worker at %s:%d has been successfully added", worker.address, worker.port );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.CREATED;
			}
			else {
				String message = String.format( "The worker at %s:%d could not been added", worker.address, worker.port );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
//		catch ( WorkerServiceException e ) { TODO : uncomment when method has been implemented
//			String message = "An error occurred: " + e.getMessage();
//			response = new BasicResponse( false, message );
//			responseStatus = HttpStatus.BAD_REQUEST;
//		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}

	@PutMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<BasicResponse> editWorker( @RequestBody WorkerDTO worker ) {
		BasicResponse response;
		HttpStatus responseStatus;

		try {
			if ( workerService.editWorker( worker.address, worker.port ) ) {
				String message = String.format( "The worker n°%d has been successfully modified", worker.id );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.OK;
			}
			else {
				String message = String.format( "The worker n°%d could not been modified", worker.id );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
//		catch ( WorkerServiceException e ) { TODO : uncomment when method has been implemented
//			String message = "An error occurred: " + e.getMessage();
//			response = new BasicResponse( false, message );
//			responseStatus = HttpStatus.BAD_REQUEST;
//		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}

	@DeleteMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<BasicResponse> removeWorker( @RequestBody WorkerDTO worker ) {
		BasicResponse response;
		HttpStatus responseStatus;

		try {
			if ( workerService.removeWorker( worker.address, worker.port ) ) {
				String message = String.format( "The worker n°%d has been successfully removed", worker.id );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.OK;
			}
			else {
				String message = String.format( "The worker n°%d could not been removed", worker.id );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
//		catch ( WorkerServiceException e ) { TODO : uncomment when method has been implemented
//			String message = "An error occurred: " + e.getMessage();
//			response = new BasicResponse( false, message );
//			responseStatus = HttpStatus.BAD_REQUEST;
//		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}
}
