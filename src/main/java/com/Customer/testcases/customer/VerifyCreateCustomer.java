/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: VerifyCreateCustomer.java
 * 
 * Description:  This test case will verify create new customer. 
 * 
 * Created by: Sree Hari S
 * Created on: 10/06/2018
 */

package com.Customer.testcases.customer;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Customer.common.Global;
import com.Customer.managers.Validations;

public class VerifyCreateCustomer extends BaseAPITest {

	/**
	 * Constructor
	 */
	public VerifyCreateCustomer() {
		super(VerifyCreateCustomer.class, DESCRIPTION);
	}

	/**
	 * Validates customer API.
	 */
	@Test(description = DESCRIPTION)
	public void test() {
		Global.TEST_CASE_ERROR_MESSAGES = new StringBuilder();
		m_isTestPassed = Validations.CustomerDataValidation().verifyCustomerData();
		Assert.assertTrue(m_isTestPassed, getErrorMessage());
	}

	private final static String DESCRIPTION = "Validates new customer with customer api.";
}