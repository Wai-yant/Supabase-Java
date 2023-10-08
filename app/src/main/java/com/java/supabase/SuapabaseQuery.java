package com.java.supabase;

import android.app.Activity;

class SupabaseQuery{
	
	private SupabaseTable supabaseTable;
	private String columns;
	private String data;
	private String method;
	
	public SupabaseQuery(SupabaseTable supabaseTable, String columns, String data, String method) {
		this.supabaseTable = supabaseTable;
		this.columns = columns;
		this.data = data;
		this.method = method;
	}
	
	public void setSupabaseTable(SupabaseTable supabaseTable) {
		this.supabaseTable = supabaseTable;
	}
	
	public SupabaseTable getSupabaseTable() {
		return supabaseTable;
	}
	
	public void setColumns(String columns) {
		this.columns = columns;
	}
	
	public String getColumns() {
		return columns;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getData() {
		return data;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getMethod() {
		return method;
	}
	
	
	public void execute(Activity context,SupabaseListener listener){
		new SupabaseDatabaseApi(this,null).execute(context,method,listener);
	}
	
	public SupabaseFilter eq(String column,String value){
		return new SupabaseFilter(this,column,value,"eq");
	}
	
	public SupabaseFilter neq(String column,String value){
		return new SupabaseFilter(this,column,value,"neq");
	}
	
	public SupabaseFilter gt(String column,String value){
		return new SupabaseFilter(this,column,value,"gt");
	}
	
	public SupabaseFilter gte(String column,String value){
		return new SupabaseFilter(this,column,value,"gte");
	}
	
	public SupabaseFilter lt(String column,String value){
		return new SupabaseFilter(this,column,value,"lt");
	}
	
	public SupabaseFilter lte(String column,String value){
		return new SupabaseFilter(this,column,value,"lte");
	}
}
