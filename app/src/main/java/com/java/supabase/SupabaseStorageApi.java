package com.java.supabase;

import android.net.Uri;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.HashMap;
import okhttp3.Request;
import android.app.Activity;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import okhttp3.MediaType;
import android.widget.Magnifier.Builder;
import okhttp3.OkHttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import java.io.IOException;
import okhttp3.Response;

public class SupabaseStorageApi {
	
	private SupabaseStorage storage;
	private RequestBody body;
	private Request.Builder request;
	private OkHttpClient client;
	private SupabaseMethods method;
	public Call call;
	
	public SupabaseStorageApi(SupabaseStorage storage,SupabaseMethods method){
		this.storage = storage;
		this.method = method;
		
		if(client==null){
			client = new OkHttpClient();
		}
		
		if(method==SupabaseMethods.UPLOAD){
			request = new Request.Builder().url(storage.getUrl()+"/object/"+storage.getBucket()+"/"+storage.getFile().getName());
		}else{
			request = new Request.Builder().url(storage.getFileUrl());
		}
		request.addHeader("apiKey",storage.getKey());
		request.addHeader("Authorization","Bearer "+storage.getKey());
		
		if(method==SupabaseMethods.UPLOAD){
			body = new MultipartBody.Builder()
			.setType(MultipartBody.FORM)
			.addFormDataPart("file",storage.getFile().getName(),RequestBody.create(MediaType.parse("image/jpg"), storage.getFile()))
			.build();
			
			request.post(body);
			
			request.addHeader("Content-Type","image/jpg");
			request.addHeader("cache-control","3600");
			request.addHeader("x-upsert","false");
		}
		
		if(method==SupabaseMethods.DELETE){
			RequestBody body = RequestBody.create(MediaType.parse("application/json"),"{\"prefix\":[\""+Uri.parse(storage.getFileUrl()).getLastPathSegment()+"\"]}");
			request.delete(body);
		}
		
		call = client.newCall(request.build());
	}
	
	public void setOnCompleteListener(Activity context,OnCompleteListener listener){
		ChildTask task = new ChildTask();
		call.enqueue(new Callback() {
			public void onResponse(Call call, final Response response) throws IOException {
				final String responseBody = response.body().string().trim();
				HashMap<String,Object> responseMap = new Gson().fromJson(responseBody,new TypeToken<HashMap<String,Object>>(){});
				if(method==SupabaseMethods.UPLOAD){
					if(responseMap.get("code")!=null){
						task.success = false;
						task.message = responseMap.get("msg").toString();
					}else if(responseMap.get("error")!=null){
						task.success = false;
						task.message = responseMap.get("error_description").toString();
					}else{
						task.success = true;
						task.data = storage.getUrl()+"/object/public/"+storage.getBucket()+"/"+storage.getFile().getName();
					}
				}else{
					if(responseMap.get("code")!=null){
						task.success = false;
						task.message = responseMap.get("msg").toString();
					}else if(responseMap.get("error")!=null){
						task.success = false;
						task.message = responseMap.get("error_description").toString();
					}else{
						task.success = true;
						task.data = responseBody;
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