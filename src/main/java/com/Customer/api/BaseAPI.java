/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: BaseAPI.java
 * 
 * Description: Base API class which will holds base functions to process APIs.
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */
package com.Customer.api;

import java.util.Map;
import org.testng.Assert;

import com.Customer.common.Global;
import com.Customer.common.Helper;
import com.Customer.managers.API;
import com.Customer.managers.Json;
import com.core.RestAPI;

/**
 * Base class to process APIS
 */
public abstract class BaseAPI {

	/**
	 * Base Constructor
	 */
	public BaseAPI(String className) {
		try {
			if (m_baseURL == null) {
				m_baseURL = Global.getBaseUrl();
			}
			m_childClassName = className;
			if (m_restApiService == null) {
				m_restApiService = API.getRestApiInstanceUsingJAXRS();
			}
		} catch (Exception e) {
			Helper.printLog(m_childClassName, e.getLocalizedMessage());
		}
	}

	// -------------------------------------------------------------------------------------------

	/**
	 * Method to process get request with query parameters
	 */
	protected boolean processGetRequestWithQueryParams(String apiURL, Map<String, Object> params) {
		processGetRequest(apiURL, params);
		return verifyStatus();
	}

	/**
	 * This method will process the post request to validate warning messages.
	 * 
	 * @param apiUrl
	 *            -> API URL
	 * @param postParams
	 *            -> Post Parameters
	 * @param validationMessage
	 *            -> Validation Message.
	 * @return-> True/False.
	 */
	protected boolean processPostRequestWithPostParams(String apiUrl, Map<String, Object> postParams,
			String validationMessage) {
		return processPostRequest(apiUrl, null, postParams, "POST");
	}

	/**
	 * Method to verify client response is success or failure.
	 * 
	 * @param clientResponse
	 * @return
	 */
	protected String verifyResponseStatus() {
		String statusMessage = "";
		int status = Integer.parseInt(Json.CustomerJSONHelper().getStatusCode(m_clientResponse));
		if (status >= 200 && status < 300) {
			statusMessage = "SUCCESS";
		} else if (status >= 300 && status < 400) {
			statusMessage = String.format("Api failed: Redirection: Error code is %d", status);
			Helper.printLog(m_childClassName, String.format("API Request failed. Error Message: %s", statusMessage));
			Global.TEST_CASE_ERROR_MESSAGES
					.append(String.format("API Request failed. Error Message: %s", statusMessage));
		} else if (status >= 400 && status < 500) {
			statusMessage = String.format("Api failed: Client Error: Error code is %d", status);
			Helper.printLog(m_childClassName, String.format("API Request failed. Error Message: %s", statusMessage));
			Global.TEST_CASE_ERROR_MESSAGES
					.append(String.format("API Request failed. Error Message: %s", statusMessage));
		} else if (status >= 500) {
			statusMessage = String.format("Api failed: Internal Server Error: Error code is %d", status);
			Helper.printLog(m_childClassName, String.format("API Request failed. Error Message: %s", statusMessage));
			Global.TEST_CASE_ERROR_MESSAGES
					.append(String.format("API Request failed. Error Message: %s", statusMessage));
		} else {
			statusMessage = String.format("Different error code found. Error code is %d", status);
			Helper.printLog(m_childClassName, String.format("API Request failed. Error Message: %s", statusMessage));
			Global.TEST_CASE_ERROR_MESSAGES
					.append(String.format("API Request failed. Error Message: %s", statusMessage));
		}
		return statusMessage;
	}

	/**
	 * Holds Client response
	 */
	protected String m_clientResponse = null;

	/**
	 * Gets REST API service instance.
	 */
	protected static RestAPI m_restApiService = null;

	// ====================================================================================================================================
	/**
	 * Get API URL. Builds complete URL and returns
	 * 
	 * @param apiURL
	 *            API URL Constant.
	 * @return API URL combines base URL and API URL
	 */
	private String getAPIUrl(String apiURL) {
		return String.format("%s%s", m_baseURL, apiURL);
	}

	/**
	 * Method to verify status
	 */
	private boolean verifyStatus() {
		if (!verifyResponseStatus().equals("SUCCESS")) {
			String message = Json.CustomerJSONHelper().getMessage(m_clientResponse);
			Helper.appendErrorMessage(message + "<br />");
			Assert.assertTrue(false,
					String.format("API - %s process failed. <br />Error Message: %s", m_apiURL, message));
		}
		return true;
	}

	private boolean processPostRequest(String apiURL, Map<String, Object> queryParams, Map<String, Object> postParams,
			String mode) {
		// Build API URL
		m_apiURL = getAPIUrl(apiURL);
		// Process API URL
		m_clientResponse = m_restApiService.processPostRequest(m_apiURL, queryParams, postParams);
		return verifyStatus();
	}

	/**
	 * Method to process get request with query parameters
	 */
	private void processGetRequest(String apiURL, Map<String, Object> params) {
		m_apiURL = getAPIUrl(apiURL);
		m_clientResponse = m_restApiService.processGetRequest(apiURL, params);
	}

	/**
	 * Gets API URL path
	 */
	private static String m_apiURL = null;

	/**
	 * Gets API base URL Path
	 */
	private static String m_baseURL = null;
	/**
	 * Gets Child class name
	 */
	private String m_childClassName = null;

	// =========================================================================================================================

}
