package com.saucedemo.pages;

import com.microsoft.playwright.Page;

public class InventoryPage {

	private Page page;

	public InventoryPage(Page page) {
		this.page = page;
	}

	// 3. page actions/methods:
	public String getInventoryPageTitle() {
		String title =  page.title();
		System.out.println("page title: " + title);
		return title;
	}

	public String getInventoryPageURL() {
		String url =  page.url();
		System.out.println("page url : " + url);
		return url;
	}
}