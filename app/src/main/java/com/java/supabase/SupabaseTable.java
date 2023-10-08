package com.java.supabase;

public class SupabaseTable {
	
	private String apiKey;
	private String authorize;
	private String table;
	private String url;
	
	public SupabaseTable(String apiKey,String table,String url) {
		this.apiKey = apiKey;
		this.authorize = "Bearer "+apiKey;
		this.table = table;
		this.url = url+"/rest/v1";
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setTable(String table)
	{
		this.table = table;
	}
	
	public String getTable()
	{
		return table;
	}
	
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getApiKey() {
		return apiKey;
	}
	
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}
	
	public String getAuthorize() {
		return authorize;
	}
	
	public SupabaseQuery select(String columns){
		return new SupabaseQuery(this,columns,"","GET");
	}
	
	public SupabaseQuery insert(String data){
		return new SupabaseQuery(this,"",data,"POST");
	}
	
	public SupabaseQuery update(String data){
		return new SupabaseQuery(this,"",data,"PATCH");
	}
	
	public SupabaseQuery delete(){
		return new SupabaseQuery(this,"","","DELETE");
	}
}