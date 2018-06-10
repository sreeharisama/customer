package com.core;

import java.util.Map;

public abstract class RestAPI {
	/**
	 * This method will process the REST API GET request and returns client
	 * response as string.
	 */
	public abstract String processGetRequest(String apiURL, Map<String, Object> queryParams);

	/**
	 * This method will process the REST API POST request and returns the client
	 * response.
	 */
	public abstract String processPostRequest(String apiURL, Map<String, Object> queryParams,
			Map<String, Object> postParams);

}
