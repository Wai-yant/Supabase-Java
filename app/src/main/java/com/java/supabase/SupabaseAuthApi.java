package com.java.supabase;

import android.app.Activity;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.supabase.Tasks;
import java.io.IOException;
import java.security.Provider;
import java.util.HashMap;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.concurrent.Task;

public class SupabaseAuthApi {
	
	private Request.Builder request;
	private OkHttpClient client;
	public Call call;
	private SupabaseMethods method;
	private String ending;
	
	public SupabaseAuthApi(SupabaseAuth auth,SupabaseMethods method){
		this.method = method;
		
		if(client==null){
			client = new OkHttpClient();
		}
		
		
		HashMap<String,Object> map = new HashMap<>();
		map.put("email",auth.getEmail());
			
		if(method==SupabaseMethods.SIGNUP){
			ending = "/signup";
			map.put("password",auth.getPassword());
		}else if(method==SupabaseMethods.SIGNIN){
			ending = "/token?grant_type=password";
			map.put("password",auth.getPassword());
		}else{
			ending = "/magiclink";
		}
		
		
		request = new Request.Builder().url(auth.getUrl()+ending);
		
		request.addHeader("apiKey",auth.getKey());
		request.addHeader("Authorization","Bearer "+auth.getKey());
		
		RequestBody body = RequestBody.create(MediaType.parse("application/json"),new Gson().toJson(map));
		
		
		request.post(body);
		
		
		call = client.newCall(request.build());
		
	}
	
	public void setOnCompleteListener(Activity context,OnCompleteListener listener){
		ChildTask task = new ChildTask();
		call.enqueue(new Callback(){
			
			public void onResponse(Call call, final Response response) throws IOException {
				final String responseBody = response.body().string().trim();
				HashMap<String,Object> responseMap = new Gson().fromJson(responseBody,new TypeToken<HashMap<String,Object>>(){});
					
				if(method==SupabaseMethods.SIGNUP){
					if(responseMap.get("id")!=null){
						task.success = true;
						task.data = new Gson().toJson(responseMap);
					}else if(responseMap.get("code")!=null){
						task.success = false;
						task.message = responseMap.get("msg").toString();
					}else{
						task.success = false;
						task.message = responseMap.toString();
					}
				}else if(method==SupabaseMethods.SIGNIN){
					if(responseMap.get("access_token")!=null){
						task.success = true;
						task.data = new Gson().toJson(responseMap);
					}else if(responseMap.get("code")!=null){
						task.success = false;
						task.message = responseMap.get("msg").toString();
					}else if(responseMap.get("error")!=null){
						task.success = false;
						task.message = responseMap.get("error_description").toString();
					}else{
						task.success = false;
						task.message = responseMap.toString();
					}
				}else{
					if(responseMap.size()==0){
						task.success = true;
						task.message = "Verification email sent!";
					}else{
						task.success = false;
						task.message = responseMap.toString();
					}
				}
				
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onComplete(task);
					}
				});	
				
					
			}
			
			public void onFailure(Call call, final IOException e) {
				task.success = false;
				task.message = e.getMessage();
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onComplete(task);
					}
				});
			}

		});
	}
	
	

}