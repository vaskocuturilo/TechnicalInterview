package com.example.demo.model.health;

public class Details{
	private String database;
	private String validationQuery;
	private String path;
	private long total;
	private boolean exists;
	private int threshold;
	private long free;

	public String getDatabase(){
		return database;
	}

	public String getValidationQuery(){
		return validationQuery;
	}

	public String getPath(){
		return path;
	}

	public long getTotal(){
		return total;
	}

	public boolean isExists(){
		return exists;
	}

	public int getThreshold(){
		return threshold;
	}

	public long getFree(){
		return free;
	}
}
