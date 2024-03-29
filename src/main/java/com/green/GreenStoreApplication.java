package com.green;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GreenStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenStoreApplication.class, args);
		
		//Auto run this request
		Runtime rt = Runtime.getRuntime();
	      try {
	    	  rt.exec("cmd /c start chrome.exe http://localhost:8080");
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	}

}
