package com.saucedemo.tests;

import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.microsoft.playwright.Page;
import com.saucedemo.factory.PlaywrightFactory;
import com.saucedemo.pages.*;

import com.saucedemo.factory.PlaywrightFactory;
import com.saucedemo.pages.LoginPage;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

	PlaywrightFactory pf;
	Page page;
	protected Properties prop;

	protected LoginPage loginPage;

	@Parameters({ "browser" })
	@BeforeTest
	public void setup(@Optional String browserName) {
		pf = new PlaywrightFactory();
		prop = pf.init_prop();

		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}

		page = pf.initBrowser(prop);
		loginPage = new LoginPage(page);
	}


	@AfterTest
	public void tearDown() {
		page.context().browser().close();
	}






}
