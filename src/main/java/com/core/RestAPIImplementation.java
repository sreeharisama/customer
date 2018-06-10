package com.core;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class RestAPIImplementation extends RestAPI {

	@Override
	public String processGetRequest(String apiURL, Map<String, Object> queryParams) {
		Client client = createClient();
		try {
			ClientResponse clientResponse = null;
			WebResource webResource = client.resource(apiURL);

			System.out.println(String.format("Get Request URL: %s?%s", apiURL, queryParams));

			if (queryParams != null) {
				clientResponse = webResource.queryParams(mapToMultivaluedMap(queryParams))
						.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			} else {
				clientResponse = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			}

			return (verifyClientResponse(clientResponse));
		} catch (Exception e) {
			System.out.println("RestAPIImplementation" + e.getMessage());
		} finally {
			client.destroy();
		}
		return null;
	}

	/**
	 * Method to verify the client response object
	 */
	private String verifyClientResponse(ClientResponse clientResponse) {
		int statusCode = clientResponse.getStatus();
		if (statusCode != 200 && statusCode != 201 && statusCode != 204) {
			System.out.println("Failed : HTTP error code :" + clientResponse.getStatus());
		}
		System.out.println("API status code :%d" + clientResponse.getStatus());
		return clientResponse.getEntity(String.class);
	}

	@Override
	public String processPostRequest(String apiURL, Map<String, Object> queryParams, Map<String, Object> postParams) {
		if (queryParams != null && postParams != null) {
			return processRequest(apiURL, queryParams, postParams, "BOTH");
		} else if (queryParams != null && postParams == null) {
			return processRequest(apiURL, queryParams, null, "QUERY");
		} else if (queryParams == null && postParams != null) {
			return processRequest(apiURL, null, postParams, "POST");
		}
		return null;
	}

	/**
	 * Creates Jersey API client
	 */
	private Client createClient() {
		ClientConfig config = new DefaultClientConfig();
		try {
			SSLContext ctx = SSLContext.getInstance("SSL");
			TrustManager[] trustAllCerts = {};
			ctx.init(null, trustAllCerts, null);
			config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(null, ctx));
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			System.out.println("Exception while creating an SSL-aware API client, " + "continuing with non-SSL client");
			e.printStackTrace();
		}
		return Client.create(config);
	}

	/**
	 * Helper method which will convert Map<String, String> to
	 * MultiValuedMap<String, String). In this project we used this method in
	 * RestApiServiceWithJAXRS.java under com.marketshare.api package.
	 */
	public MultivaluedMap<String, String> mapToMultivaluedMap(Map<String, Object> map) {
		MultivaluedMap<String, String> multivaluedMap = new MultivaluedMapImpl();
		if (map != null) {
			for (Entry<String, Object> entry : map.entrySet()) {
				multivaluedMap.add(entry.getKey(), entry.getValue().toString());
			}
		}
		return multivaluedMap;
	}

	/**
	 * Process the post request and returns response.
	 */
	private String processRequest(String apiURL, Map<String, Object> queryParams, Map<String, Object> postParams,
			String parameterMode) {

		Client client = createClient();

		try {
			ClientResponse clientResponse = null;
			WebResource webResource = client.resource(apiURL);

			switch (parameterMode) {
			case "BOTH":
				clientResponse = webResource.queryParams(mapToMultivaluedMap(queryParams))
						.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, postParams);
				break;
			case "QUERY":
				break;
			case "POST":
				clientResponse = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, postParams);
				break;
			case "GET":
				break;
			default:
				break;
			}

			return (verifyClientResponse(clientResponse));
		} catch (

		Exception e) {
			System.out.println(e.getMessage());
		} finally {
			client.destroy();
		}
		return null;
	}
}
