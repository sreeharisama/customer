package com.Customer.api.validation;

import java.util.HashMap;
import java.util.Map;

import com.Customer.common.Helper;
import com.Customer.managers.API;
import com.Customer.managers.DBManager;
import com.Customer.managers.Json;

public class CustomerDataValidation {
	/**
	 * Method to validate the customer data after creating new record
	 * 
	 * @return
	 */
	public boolean verifyCustomerData() {
		try {
			Map<String, Object> prepareCustomerInputData = buildCustomerInfo();
			String custId = API.Customer().createCustomer(prepareCustomerInputData);
			if (custId == null) {
				Helper.skipTestCase(CLASS_NAME + ":Errored while creating the customer data. Hence failed.");
			}
			Map<String, Object> expectedValues = DBManager.CustomerDB().getCustomerInfo(custId);
			return Helper.compareTwoMapObjects(prepareCustomerInputData, expectedValues,
					"Customer data is not matching.");
		} catch (Exception e) {
			Helper.printLog(CLASS_NAME + "->" + METHOD_NAME, e.toString());
		}
		return false;
	}

	/**
	 * Method to verify the customer data based on the customer id
	 * 
	 * @param custId
	 * @return
	 */
	public boolean verifyCustomerDataById(String custId) {
		try {
			String clientResponse = API.Customer().getCustomerData(custId);
			Map<String, Object> actualData = Json.CustomerJSONHelper().processCutomerResponse(clientResponse);
			if (actualData == null) {
				Helper.printLog(CLASS_NAME, "Api is failed while retrieving the customer data for the given id.");
				Helper.skipTestCase("Api processing is failed due to invalid customer id.");
			}
			Map<String, Object> expectedData = DBManager.CustomerDB().getCustomerInfo(custId);
			return Helper.compareTwoMapObjects(actualData, expectedData,
					"Customer data is matching with the database.");
		} catch (Exception e) {
			Helper.printLog(CLASS_NAME + "->" + METHOD_NAME, e.toString());
		}
		return false;
	}

	// ------------------------------------------------------------------------------------------------------------
	// Private methods
	/**
	 * Method will build the test data to create customer
	 * 
	 * @return
	 */
	private Map<String, Object> buildCustomerInfo() {
		try {
			Map<String, Object> data = new HashMap<String, Object>();
			// Note: this data can be populate using excel sheet or dynamically
			// by using time stamp
			data.put("name", "Customer1");
			data.put("phonenum", "93789403");
			data.put("orderid", "782");
			return data;
		} catch (Exception e) {
			Helper.printLog(CLASS_NAME + "->" + METHOD_NAME, e.toString());
		}
		return null;
	}

	private final String CLASS_NAME = CustomerDataValidation.class.getName();
	private String METHOD_NAME = Thread.currentThread().getStackTrace()[2].getMethodName();
}
