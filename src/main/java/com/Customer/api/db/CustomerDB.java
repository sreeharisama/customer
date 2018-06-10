/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: CustomerDB.java
 * 
 * Description:  This class will hold all customer database related operations 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */
package com.Customer.api.db;

import java.util.List;
import java.util.Map;

public class CustomerDB extends DataBase {

	/**
	 * Method to get customer data by id
	 * 
	 * @param custId
	 * @return -> Map<String,Object>
	 */
	public Map<String, Object> getCustomerInfo(String custId) {
		return getSingleRowAsMap(String.format(GET_CUSTOMER_INFO, custId));
	}

	/**
	 * Method to get all available customer ids
	 * 
	 * @return
	 */
	public List<String> getCustomerIds() {
		return getSingleColWithMultipleRowsList(GET_CUSTOMER_IDS);
	}

	// -----------------------------------------------------------------------------------------------------------------------------------------------------
	// Private Variables
	// Query to customer details by id
	private final String GET_CUSTOMER_INFO = "SELECT NAME,PHONE_NO,ORDER_ID FROM CUSTOMER WHERE ID = %s";
	private final String GET_CUSTOMER_IDS = "SELECT ID FROM CUSTOMER";

}
