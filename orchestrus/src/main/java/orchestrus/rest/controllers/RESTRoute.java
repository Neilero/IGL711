package orchestrus.rest.controllers;

class RESTRoute {
	public static final String BASE_ROUTE = "/";
	public static final String WORKER = BASE_ROUTE + "worker";

	private static final String DOCKER_IMAGE = BASE_ROUTE + "image/";
	public static final String START_IMAGE = DOCKER_IMAGE + "start";
	public static final String STOP_IMAGE = DOCKER_IMAGE + "stop";
	public static final String UPLOAD_IMAGE = DOCKER_IMAGE + "upload";
}
