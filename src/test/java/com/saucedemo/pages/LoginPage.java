package com.saucedemo.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

	private Page page;

    private String emailId = "//input[@data-test='username']";
    private String password = "//input[@data-test='password']";
    private String loginBtn = "//input[@data-test='login-button']";
    private String basket = "//a[@class='shopping_cart_link']";

    public LoginPage(Page page) {
        this.page = page;
    }

    public String getLoginPageTitle() {
        return page.title();
    }

    public boolean doLogin(String appUserName, String appPassword) {
        System.out.println("App creds: " + appUserName + ":" + appPassword);
        page.fill(emailId, appUserName);
        page.fill(password, appPassword);
        page.click(loginBtn);
        page.locator(basket).waitFor();
        if (page.locator(basket).isVisible()) {
            System.out.println("user is logged in successfully....");
            return true;
        } else {
            System.out.println("user is not logged in successfully....");
            return false;
        }
    }
}