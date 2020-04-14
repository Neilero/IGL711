package orchestrus.rest.controllers;

class RESTRoute {
	public static final String INDEX = "/";

	public static final String WORKERS = INDEX + "workers";
	public static final String WORKER  = INDEX + "worker";

	public static final String IMAGES      = INDEX + "images";
	public static final String IMAGE       = INDEX + "image";
	public static final String START_IMAGE = IMAGE + "/start";
	public static final String STOP_IMAGE  = IMAGE + "/stop";
}
