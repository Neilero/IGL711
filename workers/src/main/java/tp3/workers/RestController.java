package tp3.workers;

import com.spotify.docker.client.exceptions.DockerCertificateException;
import com.spotify.docker.client.exceptions.DockerException;
import docker.DockerTools;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    //TEST de l api
    @RequestMapping("/")
    public String HelloWorld(){

        return "hsssi ! ";

    }
    //TEST de l api
    @PostMapping("/run")
    @ResponseBody
    public String runImage(@RequestParam Map<String,String> allParams) throws InterruptedException, DockerException, DockerCertificateException {
        System.out.println(allParams.entrySet());
        System.out.println(allParams.get("dockerfile"));
        //boolean dockerfile = ToolsWorker.runImage(allParams.get("dockerfile"));
        DockerTools.createContainer(allParams.get("dockerfile"));
        return "Parameters are " + allParams.entrySet();
    }

    //start image
    @PostMapping("/start")
    @ResponseBody
    public void startImage(){

    }
    //stop image
    @PostMapping("/stop")
    @ResponseBody
    public void stopImage(){

    }
    //poke
    public boolean poke(){

        return true ;
    }
}
