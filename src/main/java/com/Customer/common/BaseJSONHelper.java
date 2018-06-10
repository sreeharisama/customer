/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: BaseJSONHelper.java
 * 
 * Description: Base API class which will holds base functions to process Jsons.
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */
package com.Customer.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BaseJSONHelper {
	// -------------------------------------------------------------------------------------
	// Public Methods
	/**
	 * This method will the message of JSON response object from API call.
	 */
	public String getMessage(String clientResponse) {
		if (clientResponse != null && clientResponse.trim().length() > 0) {
			JsonObject jsonObject = getJSONObject(clientResponse);
			return getJSONDataKeyValue(jsonObject, "message");
		}
		return "API response is null.";
	}

	/**
	 * Method to get status code
	 * 
	 * @param clientResponse
	 * @return
	 */
	public String getStatusCode(String clientResponse) {
		JsonObject jsonObject = getJSONObject(clientResponse);
		return getJSONDataKeyValue(jsonObject, "code");
	}

	/**
	 * Method to get status message
	 * 
	 * @param clientResponse
	 * @return
	 */
	public String getStatusMessage(String clientResponse) {
		JsonObject jsonObject = getJSONObject(clientResponse);
		return getJSONDataKeyValue(jsonObject, "status");
	}

	// ---------------------------------------------------------------------------------------
	// Protected Methods
	/**
	 * Method to parse the json and store to jsonobject
	 * 
	 * @param content
	 * @return
	 */
	protected JsonObject getJSONDataObject(String content) {
		JsonObject jsonDataObject = getJSONObject(content);
		JsonElement dataElement = jsonDataObject.get("data");
		return dataElement.getAsJsonObject();
	}

	protected JsonObject getJSONObject(String content) {
		JsonElement jsonElement = parseJSON(content);
		return jsonElement.getAsJsonObject();
	}

	protected JsonElement parseJSON(String content) {
		return (new JsonParser()).parse(content);
	}

	protected String getJSONDataKeyValue(JsonObject jsonObject, String columnKey) {
		JsonElement dataElement = jsonObject.get(columnKey);
		return dataElement.toString().replace("\"", "");
	}

	/**
	 * Method to validate Member and get value
	 * 
	 * @param jsonObject
	 * @param memberName
	 * @return
	 */
	protected String validateMemberAndGetValue(JsonObject jsonObject, String memberName) {
		if (jsonObject.has(memberName)) {
			return (jsonObject.get(memberName).isJsonNull()) ? "" : jsonObject.get(memberName).getAsString().trim();
		}
		Helper.appendErrorMessage(String.format(
				"Failed while processing JSONObject in \"%s\". MemberName '%s' is not avaiable in JSON object: %s",
				CLASS_NAME, memberName, jsonObject.toString()));
		return null;
	}

	// --------------------------------------------------------------------------------------------------------------------------------
	// Private variables
	private final String CLASS_NAME = BaseJSONHelper.class.getName();
}
