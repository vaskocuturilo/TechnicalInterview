package com.example.demo.model.health;

public class Components{
	private DiskSpace diskSpace;
	private Ping ping;
	private Db db;

	public DiskSpace getDiskSpace(){
		return diskSpace;
	}

	public Ping getPing(){
		return ping;
	}

	public Db getDb(){
		return db;
	}
}
