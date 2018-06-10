/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: DBManager.java
 * 
 * Description:  This is Manager class to create instance for all database across project 
 * 
 * Created By : Sree Hari S
 * Created on : 09/06/2018
 */

package com.Customer.managers;

import com.Customer.api.db.CustomerDB;

public class DBManager {

	/**
	 * Gets instance of AdminDB class
	 */
	private static volatile CustomerDB m_customerDB = null;

	public static CustomerDB CustomerDB() {
		if (m_customerDB == null) {
			synchronized (CustomerDB.class) {
				if (m_customerDB == null) {
					m_customerDB = new CustomerDB();
				}
			}
		}
		return m_customerDB;
	}

}
