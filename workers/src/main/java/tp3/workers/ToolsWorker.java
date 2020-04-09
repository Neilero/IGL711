package tp3.workers;






import static java.lang.System.out;

public class ToolsWorker {

    private boolean isAIMageRunning =  false ;

    public static boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    public static boolean runImage(String image) {
        out.println("Lance");

        return false;
    }
}
