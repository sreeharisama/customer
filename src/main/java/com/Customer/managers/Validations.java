/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: Validations.java
 * 
 * Description:  This is Manager class to create instance for validation classes. 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.managers;

import com.Customer.api.validation.*;

/**
 * Factory Manager class for Validations
 */
public class Validations {
	/**
	 * Gets instance of Customer class.
	 */
	private static volatile CustomerDataValidation m_moduleValidation = null;

	public static CustomerDataValidation CustomerDataValidation() {
		if (m_moduleValidation == null) {
			synchronized (CustomerDataValidation.class) {
				if (m_moduleValidation == null) {
					m_moduleValidation = new CustomerDataValidation();
				}
			}
		}
		return m_moduleValidation;
	}
}
