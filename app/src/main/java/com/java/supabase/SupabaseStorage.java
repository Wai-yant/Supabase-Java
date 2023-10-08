package com.java.supabase;
import java.io.File;

public class SupabaseStorage {
	
	private String bucket;
	private String url;
	private String key;
	private String authorize;
	private File file;
	private String fileUrl;
	
	public SupabaseStorage(String bucket, String url, String key) {
		this.bucket = bucket;
		this.url = url+"/storage/v1";
		this.key = key;
		this.authorize = "Bearer "+key;
	}
	
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	
	public String getBucket() {
		return bucket;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}
	
	public String getAuthorize() {
		return authorize;
	}
	
	public void setFile(String filePath){
		this.file = new File(filePath);
	}
	
	public File getFile(){
		return file;
	}
	
	public void setFileUrl(String fileUrl){
		this.fileUrl = fileUrl;
	}
	
	public String getFileUrl(){
		return fileUrl;
	}
	
	public SupabaseStorageApi upload(String filePath){
		setFile(filePath);
		return new SupabaseStorageApi(this,SupabaseMethods.UPLOAD);
	}
	
	public SupabaseStorageApi delete(String fileUrl){
		setFileUrl(fileUrl);
		return new SupabaseStorageApi(this,SupabaseMethods.DELETE);
	}
	
}