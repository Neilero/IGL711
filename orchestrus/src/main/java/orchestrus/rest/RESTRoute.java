package orchestrus.rest;

import orchestrus.model.Worker;

import javax.validation.constraints.NotNull;

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
	// Service : DB interface
	public static final String BD_INTERFACE_ADDRESS = "http://interface:8080";//System.getenv("ORCHESTRUS_DB_INTERFACE");
	public static final String BD_INTERFACE_WORKERS = BD_INTERFACE_ADDRESS + "/workers/";
	public static final String BD_INTERFACE_PORTS   = BD_INTERFACE_ADDRESS + "/ports/";
	public static final String BD_INTERFACE_IMAGES  = BD_INTERFACE_ADDRESS + "/images/";

	// Service : Worker
	public static String getWorkerDefaultRoute( @NotNull Worker worker ) {
		return String.format( "%s:%s/", worker.getAddress(), worker.getPort() );
	}

	public static String getWorkerRouteStart( @NotNull Worker worker ) {
		return getWorkerDefaultRoute( worker ) + "start";
	}

	public static String getWorkerRouteStop( @NotNull Worker worker ) {
		return getWorkerDefaultRoute( worker ) + "stop";
	}

	public static String getWorkerRouteRunning( @NotNull Worker worker ) {
		return getWorkerDefaultRoute( worker ) + "running";
	}

}
