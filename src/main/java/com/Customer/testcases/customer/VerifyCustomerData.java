/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: VerifyCustomerData.java
 * 
 * Description:  This test case will verify existing customer data. 
 * 
 * Created by: Sree Hari S
 * Created on: 10/06/2018
 */

package com.Customer.testcases.customer;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Customer.common.Global;
import com.Customer.managers.DBManager;
import com.Customer.managers.Validations;

public class VerifyCustomerData extends BaseAPITest {

	/**
	 * Constructor
	 */
	public VerifyCustomerData() {
		super(VerifyCustomerData.class, DESCRIPTION);
	}

	/**
	 * Validates customer API.
	 */
	@Test(description = DESCRIPTION)
	public void test() {
		Global.TEST_CASE_ERROR_MESSAGES = new StringBuilder();
		String custId = DBManager.CustomerDB().getCustomerIds().get(0);
		m_isTestPassed = Validations.CustomerDataValidation().verifyCustomerDataById(custId);
		Assert.assertTrue(m_isTestPassed, getErrorMessage());
	}

	private final static String DESCRIPTION = "Validates existing customer data by choosing customer id from the data base.";
}