package tp3.workers;

import docker.DockerTools;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.dto.BasicResponse;
import rest.dto.DockerImageDTO;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    DockerTools tools = new DockerTools();

    //TEST de l api
    @RequestMapping("/")
    public String HelloWorld(){
        /*
        DockerImage imageTest = new DockerImage("python:3", null);

        if ( tools.stop(imageTest)) {

            return "ok  ! ";
        }
        */

        return "ko";

    }
    /*
    //TEST de l api
    @PostMapping("/run")
    @ResponseBody
    public String runImage(@RequestParam Map<String,String> allParams) throws Exception {
        System.out.println(allParams.entrySet());
        System.out.println(allParams.get("dockerfile"));
        //boolean dockerfile = ToolsWorker.runImage(allParams.get("dockerfile"));
        DockerTools.createContainer(allParams.get("dockerfile"));
        return "Parameters are " + allParams.entrySet();
        System.out.println(allParams.get("dockerfile"));
        OpenPort openPort1 = new OpenPort(null, 9898, null );
        OpenPort openPort2 = new OpenPort(null, 9899, null );
        List<OpenPort> listOpenPort = Arrays.asList(openPort1, openPort2);
        DockerImage dockerImg1 = new DockerImage(null,null);
        List<DockerImage> runningImages = Arrays.asList(dockerImg1);

        Worker work = new Worker(null,
                null,
                1,
                null,
                runningImages,
                listOpenPort);

        DockerImage imageTest = new DockerImage("python:3", work);

        if ( tools.startImage(imageTest)) {

            return "Parameters are " + allParams.entrySet();
        }
        return "Parameters are " + allParams.entrySet();

    }*/

    //start image
    @PostMapping("/start")
    @ResponseBody
    public  ResponseEntity<BasicResponse> startImage(@RequestBody DockerImageDTO image){
        BasicResponse response;
        HttpStatus responseStatus;


        try {
            if ( tools.startImage(image.toModel() ) ) {
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
        catch ( Exception e ) {
            String message = "An unexpected error occurred: " + e.getMessage();
            response = new BasicResponse( false, message );
            responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>( response, responseStatus );

    }
    //stop image
    @PostMapping("/stop")
    @ResponseBody
    public ResponseEntity<BasicResponse> stopImage(@RequestBody DockerImageDTO image){
        BasicResponse response;
        HttpStatus responseStatus;


        try {
            if ( tools.stop(image.toModel() ) ) {
                String message = String.format( "The image %s has been successfully stoped in worker %s", image.name, image.worker.id );
                response = new BasicResponse( true, message );
                responseStatus = HttpStatus.OK;
            }
            else {
                String message = String.format( "The image %s could not been stoped in worker %s", image.name, image.worker.id );
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


    @GetMapping("/running")
    @ResponseBody
    public boolean poke(){

        try {
            if ( tools.IsAnImageRuning() ) {
                return true;
            }
            else {
                return false;
            }
        }
        catch ( Exception e ) {
            String message = "An unexpected error occurred: " + e.getMessage();
        }
        return false;
    }
}
