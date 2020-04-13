package orchestrus.rest.controllers;

import orchestrus.rest.dto.WorkerDTO;
import orchestrus.services.WorkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkerController {

	private final WorkerService workerService;


	public WorkerController() {
		workerService = new WorkerService();
	}


	@GetMapping( path = RESTRoute.WORKERS )
	@ResponseBody
	public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@GetMapping( path = RESTRoute.WORKER )
	@ResponseBody
	public ResponseEntity<WorkerDTO> getWorker( @RequestParam( name = "id" ) int workerId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

}
