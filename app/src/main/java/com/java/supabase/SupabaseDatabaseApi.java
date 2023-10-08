package com.java.supabase;

import okhttp3.HttpUrl;
import okhttp3.Request;
import android.app.Activity;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Response;
import java.io.IOException;
import okhttp3.RequestBody;
import okhttp3.MediaType;

public class SupabaseDatabaseApi {
	
	private HttpUrl.Builder builder;
	private Request.Builder request;
	private SupabaseQuery query;
	private SupabaseFilter filter;
	private OkHttpClient client;
	
	public Call call;
	
	public SupabaseDatabaseApi(SupabaseQuery query,SupabaseFilter filter){
		this.query = query;
		this.filter = filter;
		if(query!=null){
			builder = HttpUrl.parse(query.getSupabaseTable().getUrl()+"/"+query.getSupabaseTable().getTable()).newBuilder();
			}else{
			builder = HttpUrl.parse(filter.getQuery().getSupabaseTable().getUrl()+"/"+filter.getQuery().getSupabaseTable().getTable()).newBuilder();
		}
		
		if(client==null){
			client = new OkHttpClient();
		}
		
		request = new Request.Builder();
		request.addHeader("Accept","application/json");
		request.addHeader("Content-Type","application/json");
		request.addHeader("Accept-Profile","public");
		request.addHeader("Content-Profile","public");
		request.addHeader("Schema","public");
		
		
	}
	
	public void execute(final Activity context,String method,final SupabaseListener listener){
		if(method=="GET"){
			if(query!=null){
				request.addHeader("apiKey",query.getSupabaseTable().getApiKey());
				request.addHeader("Authorization",query.getSupabaseTable().getAuthorize());
				builder.addQueryParameter("select",query.getColumns());
				}else{
				request.addHeader("apiKey",filter.getQuery().getSupabaseTable().getApiKey());
				request.addHeader("Authorization",filter.getQuery().getSupabaseTable().getAuthorize());
				builder.addQueryParameter("select",filter.getQuery().getColumns());
				builder.addQueryParameter(filter.getColumn(),filter.getOperator()+"."+filter.getValue());
			}
			
		}
		
		if(method=="POST"){
			request.addHeader("apiKey",query.getSupabaseTable().getApiKey());
			request.addHeader("Authorization",query.getSupabaseTable().getAuthorize());
			RequestBody body = RequestBody.create(MediaType.parse("application/json"),query.getData());
			request.post(body);
		}
		
		if(method=="DELETE"){
			request.addHeader("apiKey",filter.getQuery().getSupabaseTable().getApiKey());
			request.addHeader("Authorization",filter.getQuery().getSupabaseTable().getAuthorize());
			builder.addQueryParameter(filter.getColumn(),filter.getOperator()+"."+filter.getValue());
			request.delete();
		}
		
		if(method=="PATCH"){
			request.addHeader("apiKey",filter.getQuery().getSupabaseTable().getApiKey());
			request.addHeader("Authorization",filter.getQuery().getSupabaseTable().getAuthorize());
			builder.addQueryParameter(filter.getColumn(),filter.getOperator()+"."+filter.getValue());
			RequestBody body = RequestBody.create(MediaType.parse("application/json"),filter.getQuery().getData());
			request.patch(body);
		}
		
		Request finalRequest = request.url(builder.build()).build();
		call = client.newCall(finalRequest);
		call.enqueue(new Callback() {
			public void onResponse(Call call, final Response response) throws IOException {
				final String responseBody = response.body().string().trim();
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onResponse(responseBody);
					}
				});
			}
			
			public void onFailure(Call call, final IOException e) {
				context.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						listener.onErrorResponse(e.getMessage());
					}
				});
			}
			
		});
	}
	
	
	
	
	
}