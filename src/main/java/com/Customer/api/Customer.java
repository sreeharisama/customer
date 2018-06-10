/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: Customer.java
 * 
 * Description:  This class will process all REST APIs related to Customer info. 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.api;

import java.util.Map;

import com.Customer.common.Global;
import com.Customer.managers.Constants;
import com.Customer.managers.Json;

/**
 * Class to process Customer APIs
 */
public class Customer extends BaseAPI {

	/**
	 * Constructor
	 */
	public Customer() {
		super(CLASS_NAME);
	}

	// -------------------------------------------------------------------------------------------------------------------------------
	// API Process methods

	public String createCustomer(Map<String, Object> postParams) {
		boolean result = processPostRequestWithPostParams(Constants.APIURL().CREATE_CUSTOMER, postParams,
				"Successfully Customer data is saved.");
		if (result) {
			return Json.CustomerJSONHelper().getCustomerIdAfterCreate(m_clientResponse);
		}
		return null;
	}

	public String getCustomerData(String id) {
		Map<String, Object> params = Global.getBasicQueryParams();
		if (processGetRequestWithQueryParams(String.format(Constants.APIURL().GET_CUSTOMER, id), params)) {
			return m_clientResponse;
		}
		return null;
	}

	// -------------------------------------------------------------------------------------------------------------------------------
	// Private Methods and variables

	/**
	 * Class Name
	 */
	private final static String CLASS_NAME = Customer.class.getName();
}
