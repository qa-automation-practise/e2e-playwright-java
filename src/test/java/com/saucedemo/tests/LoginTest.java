package com.saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	@Test(priority = 1)
	public void SuccessLoginTest() {
		Assert.assertTrue(loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim()));
		//Test
	}
}