/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: Json.java
 * 
 * Description:  Manager class for Json Helper
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.managers;

import com.Customer.common.CustomerJSONHelper;

/**
 * Manager Class for JSON Helper
 */
public class Json {

	/**
	 * Gets instance of CustomerJSONHelper
	 */
	private static volatile CustomerJSONHelper m_customerJSONHelper = null;

	public static CustomerJSONHelper CustomerJSONHelper() {
		if (m_customerJSONHelper == null) {
			synchronized (CustomerJSONHelper.class) {
				if (m_customerJSONHelper == null) {
					m_customerJSONHelper = new CustomerJSONHelper();
				}
			}
		}
		return m_customerJSONHelper;
	}
}
