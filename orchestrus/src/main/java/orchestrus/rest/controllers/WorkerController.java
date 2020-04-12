package orchestrus.rest.controllers;

import orchestrus.rest.dto.WorkerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkerController {

	@GetMapping(path = RESTRoute.WORKER)
	@ResponseBody
	public ResponseEntity<List<WorkerDTO>> getAllWorkers() {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

	@GetMapping(path = RESTRoute.WORKER)
	@ResponseBody
	public ResponseEntity<WorkerDTO> getWorkerInfo( @RequestParam(name = "id") int workerId ) {
		return new ResponseEntity<>( HttpStatus.NOT_IMPLEMENTED );
	}

}
