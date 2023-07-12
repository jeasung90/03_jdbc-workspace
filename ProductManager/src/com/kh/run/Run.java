package com.kh.run;


import com.kh.view.ProductMenu;

public class Run {

	public static void main(String[] args) {

		
		
		/*
		Properties prop = new Properties();
		
		try {
			prop.store(new FileOutputStream("resources/driver.properties"), "Make proper");
			prop.storeToXML(new FileOutputStream("resources/query.xml"), "Make proper");
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		new ProductMenu().mainMenu();
	}


}