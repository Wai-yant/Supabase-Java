package com.java.supabase;

public class SupabaseClient {
	
	private String url;
	private String key;
	
	public SupabaseClient(String url,String key){
		this.url = url;
		this.key = key;
	}
	
	public SupabaseTable table(String table_name){
		return new SupabaseTable(key,table_name,url);
	}
	
	public SupabaseStorage storage(String bucket_name){
		return new SupabaseStorage(bucket_name,url,key);
	}
	
	public SupabaseAuth auth(){
		return new SupabaseAuth(url,key);
	}
	
}