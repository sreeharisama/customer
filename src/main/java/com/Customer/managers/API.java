/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: API.java
 * 
 * Description:  This is Manager class to create instance for all class across project 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.managers;

import com.Customer.api.*;
import com.core.RestAPI;
import com.core.RestAPIImplementation;

/**
 * Factory Manager class for APIs
 */
public class API {

	/**
	 * Gets instance of Customer info class.
	 */
	private static volatile Customer m_customer = null;

	public static Customer Customer() {
		if (m_customer == null) {
			synchronized (Customer.class) {
				if (m_customer == null) {
					m_customer = new Customer();
				}
			}
		}
		return m_customer;
	}

	/**
	 * Gets instance of Rest api service
	 */
	private static volatile RestAPI m_restAPIServiceWithJAXRS = null;

	public static RestAPI getRestApiInstanceUsingJAXRS() {
		if (m_restAPIServiceWithJAXRS == null) {
			synchronized (RestAPIImplementation.class) {
				if (m_restAPIServiceWithJAXRS == null) {
					m_restAPIServiceWithJAXRS = new RestAPIImplementation();
				}
			}
		}
		return m_restAPIServiceWithJAXRS;
	}
}
