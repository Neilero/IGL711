package orchestrus.rest.controllers;

import orchestrus.exception.OrchestrusException;
import orchestrus.model.Worker;
import orchestrus.rest.RESTRoute;
import orchestrus.rest.dto.BasicResponse;
import orchestrus.rest.dto.WorkerDTO;
import orchestrus.services.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
		List<WorkerDTO> workers;

		try {
			workers = workerService.getAllWorkers()
												   .stream()
												   .map( WorkerDTO::new )
												   .collect( Collectors.toList() );
		}
		catch ( OrchestrusException e ) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
		}
		catch ( Exception e ) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
		}

		return new ResponseEntity<>( workers, HttpStatus.OK );
	}

	@GetMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<WorkerDTO> getWorker( @RequestParam( name = "id" ) UUID workerId ) {
		Worker foundWorker;

		try {
			foundWorker = workerService.getWorker( workerId );
		}
		catch ( OrchestrusException e ) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
		}
		catch ( Exception e ) {
			return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
		}

		if ( foundWorker == null )
			return new ResponseEntity<>( HttpStatus.NOT_FOUND );

		WorkerDTO worker = new WorkerDTO( foundWorker );
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

	@PutMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<BasicResponse> editWorker( @RequestBody WorkerDTO worker ) {
		BasicResponse response;
		HttpStatus responseStatus;

		try {
			if ( workerService.editWorker( worker.id, worker.toModel() ) ) {
				String message = String.format( "The worker %s has been successfully modified", worker.id );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.OK;
			}
			else {
				String message = String.format( "The worker %s could not been modified", worker.id );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}

	@DeleteMapping( path = RESTRoute.WORKER +"/{id}")
	@ResponseBody
	public ResponseEntity<BasicResponse> removeWorker(@PathVariable UUID id) {
		BasicResponse response;
		HttpStatus responseStatus;

		try {
			if ( workerService.removeWorker( id ) ) {
				String message = String.format( "The worker %s has been successfully removed", id );
				response = new BasicResponse( true, message );
				responseStatus = HttpStatus.OK;
			}
			else {
				String message = String.format( "The worker %s could not been removed", id );
				response = new BasicResponse( false, message );
				responseStatus = HttpStatus.SERVICE_UNAVAILABLE;
			}
		}
		catch ( Exception e ) {
			String message = "An unexpected error occurred: " + e.getMessage();
			response = new BasicResponse( false, message );
			responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<>( response, responseStatus );
	}
}
