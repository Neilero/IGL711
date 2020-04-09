package tp3.workers;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HelloWorld {


    @RequestMapping("/")
    public String HelloWorld(){

        return "hsssi ! ";
    }
    @PostMapping("/run")
    @ResponseBody
    public String runImage(@RequestParam Map<String,String> allParams) {
        System.out.println(allParams.entrySet());
        System.out.println(allParams.get("dockerfile"));
        boolean dockerfile = ToolsWorker.runImage(allParams.get("dockerfile"));
        return "Parameters are " + allParams.entrySet();
    }
}
