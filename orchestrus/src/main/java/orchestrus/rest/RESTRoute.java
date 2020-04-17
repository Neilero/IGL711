package orchestrus.rest;

public class RESTRoute {
	/*
		Public interfaces (can be used by clients)
	 */
	public static final String INDEX = "/";

	public static final String WORKERS = INDEX + "workers";
	public static final String WORKER  = INDEX + "worker";

	public static final String IMAGES      = INDEX + "images";
	public static final String IMAGE       = INDEX + "image";
	public static final String START_IMAGE = IMAGE + "/start";
	public static final String STOP_IMAGE  = IMAGE + "/stop";


	/*
		Private interfaces (used by application's services)
	 */
	public static final String BD_INTERFACE_ADDRESS = System.getenv("ORCHESTRUS_DB_INTERFACE");

	public static final String BD_INTERFACE_WORKERS = BD_INTERFACE_ADDRESS + "/workers/";
}
