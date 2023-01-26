package com.example.demo.model;

public class HealthCheck {
	private String status;

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"status = '" + status + '\'' + 
			"}";
		}
}
