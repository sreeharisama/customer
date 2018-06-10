/**
 * Copyright (c) 2018. All rights reserved.
 * 
 * Component Name: VerifyCustomerDataForInvalidCustomerId.java
 * 
 * Description:  This test case will verify invalid customer id. 
 * 
 * Created by: Sree Hari S
 * Created on: 10/06/2018
 */

package com.Customer.testcases.customer;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Customer.common.Global;
import com.Customer.managers.Validations;

public class VerifyCustomerDataForInvalidCustomerId extends BaseAPITest {

	/**
	 * Constructor
	 */
	public VerifyCustomerDataForInvalidCustomerId() {
		super(VerifyCustomerDataForInvalidCustomerId.class, DESCRIPTION);
	}

	/**
	 * Validates customer API.
	 */
	@Test(description = DESCRIPTION)
	public void test() {
		Global.TEST_CASE_ERROR_MESSAGES = new StringBuilder();
		String invalidCustId = "123";
		m_isTestPassed = Validations.CustomerDataValidation().verifyCustomerDataById(invalidCustId);
		Assert.assertTrue(m_isTestPassed, getErrorMessage());
	}

	private final static String DESCRIPTION = "Validates invalid customer id.";
}