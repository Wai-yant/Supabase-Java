package com.java.supabase;

public class SupabaseAuth {
	  private String email;
	private String password;
	private String key;
	private String url;
	
	public SupabaseAuth(String url,String key){
		this.url = url+"/auth/v1";
		this.key = key;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public SupabaseAuthApi sign_up(String email,String password){
		setEmail(email);
		setPassword(password);
		return new SupabaseAuthApi(this,SupabaseMethods.SIGNUP);
	}
	
	public SupabaseAuthApi sign_in(String email,String password){
		setEmail(email);
		setPassword(password);
		return new SupabaseAuthApi(this,SupabaseMethods.SIGNIN);
	}
	
	public SupabaseAuthApi get_verification_code(String email){
		setEmail(email);
		return new SupabaseAuthApi(this,SupabaseMethods.VERIFICATION);
	}
}