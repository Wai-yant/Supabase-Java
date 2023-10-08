package com.java.supabase;

import android.app.Activity;

public class SupabaseFilter {
	
	private SupabaseQuery query;
	private String column;
	private String value;
	private String operator;
	
	public SupabaseFilter(SupabaseQuery query, String column, String value,String operator) {
		this.query = query;
		this.column = column;
		this.value = value;
		this.operator = operator;
	}
	
	public void setQuery(SupabaseQuery query) {
		this.query = query;
	}
	
	public SupabaseQuery getQuery() {
		return query;
	}
	
	public void setColumn(String column) {
		this.column = column;
	}
	
	public String getColumn() {
		return column;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setOperator(String operator){
		this.operator = operator;
	}
	
	public String getOperator(){
		return operator;
	}
	
	public void execute(Activity context,SupabaseListener listener){
		new SupabaseDatabaseApi(null,this).execute(context,getQuery().getMethod(),listener);
	}
	
}