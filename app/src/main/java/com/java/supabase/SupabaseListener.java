package com.java.supabase;

public interface SupabaseListener{
	public void onResponse(String response);
	public void onErrorResponse(String message);
}
