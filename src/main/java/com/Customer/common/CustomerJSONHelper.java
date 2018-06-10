/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: CustomerJSONHelper.java
 * 
 * Description:  This a helper class to process all JSON objects 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.common;

import java.util.HashMap;
import java.util.Map;

import com.Customer.managers.Json;
import com.google.gson.JsonObject;

/**
 * Class which will process all JSON objects of REST APIs
 */
public class CustomerJSONHelper extends BaseJSONHelper {

	// --------------------------------------------------------------------------------------------------------------------------------
	// Public Methods
	/**
	 * Method to process the client Response and returns status
	 */
	public Map<String, Object> processCutomerResponse(String clientResponse) {
		Map<String, Object> finalValues = new HashMap<String, Object>();
		if (getStatusMessage(clientResponse).compareToIgnoreCase("success") == 0
				&& getStatusCode(clientResponse).compareTo("200") == 0) {
			JsonObject jsonObject = Json.CustomerJSONHelper().getJSONDataObject(clientResponse);
			finalValues.put("NAME", validateMemberAndGetValue(jsonObject, "name"));
			finalValues.put("PHONE_NO", validateMemberAndGetValue(jsonObject, "phonenum"));
			finalValues.put("ORDER_ID", validateMemberAndGetValue(jsonObject, "orderid"));
			return finalValues;
		} else {
			Helper.skipTestCase(
					CLASS_NAME + ": customer api is failed and the error message is " + getMessage(clientResponse));
		}
		return null;
	}

	/**
	 * Method gets customer id after successful creation of the customer record
	 * 
	 * @param clientResponse
	 * @return
	 */
	public String getCustomerIdAfterCreate(String clientResponse) {
		String custId = "";
		try {
			if (getStatusMessage(clientResponse).compareToIgnoreCase("success") == 0
					&& getStatusCode(clientResponse).compareTo("200") == 0) {
				JsonObject jsonObject = Json.CustomerJSONHelper().getJSONDataObject(clientResponse);
				custId = validateMemberAndGetValue(jsonObject, "customerId");
			}
			return custId;
		} catch (Exception e) {
			Helper.printLog(CLASS_NAME, e.toString());
		}
		return null;
	}

	// -----------------------------------------------------------------------------------------------------------
	// Private Variable
	private final String CLASS_NAME = CustomerJSONHelper.class.getName();

}