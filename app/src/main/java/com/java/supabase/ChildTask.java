package com.java.supabase;

public class ChildTask extends Tasks{
	
	public boolean success;
	public String message;
	public String data;
	
	@Override
	public boolean isSuccessful() {
		return success;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	
	@Override
	public String getData() {
		return data;
	}
	
}