/**
 * Copyright (c) 2018 MarketShare. All rights reserved.
 * 
 * Component Name: Constants.java
 * 
 * Description:  This is Manager class to create instance for all class across project 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.managers;

import com.Customer.constants.APIURLConstants;

/**
 * Factory Manager class for APIs
 */
public class Constants {

	/**
	 * Gets instance of APIURLConstants class.
	 */
	private static volatile APIURLConstants m_apiURLConstants = null;

	public static APIURLConstants APIURL() {
		if (m_apiURLConstants == null) {
			synchronized (APIURLConstants.class) {
				if (m_apiURLConstants == null) {
					m_apiURLConstants = new APIURLConstants();
				}
			}
		}
		return m_apiURLConstants;
	}
}
