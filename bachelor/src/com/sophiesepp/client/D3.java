package com.sophiesepp.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.sophiesepp.shared.D3Object2ParameterType1;
import com.sophiesepp.shared.D3Object2ParameterType2;
import com.sophiesepp.shared.D3Object3Parameter;


public class D3 {

	protected final static GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	
	
	protected static String createJson2ParameterType1(List<D3Object2ParameterType1> object, String parameter1,String parameter2){
				
		String json = "[";

		for(D3Object2ParameterType1 o: object)
		{
			
			json += "{\""+parameter1+"\":\""+o.x+"\",\""+parameter2+"\":\""+o.y+"\"},";
		}

		json += "]";
		
		return json;
	}
	
	
	protected static String createJson2ParameterType2(List<D3Object2ParameterType2> object, String parameter1,String parameter2){
		
		String json = "[";

		for(D3Object2ParameterType2 o: object)
		{
			
			json += "{\""+parameter1+"\":\""+o.x+"\",\""+parameter2+"\":\""+o.y+"\"},";
		}

		json += "]";
		
		return json;
	}
	
	
	protected static String createJson3Parameter(List<D3Object3Parameter> object, String parameter1,String parameter2,String parameter3){
		
		String json = "[";

		for(D3Object3Parameter o: object)
		{
			
			json += "{\""+parameter1+"\":\""+o.x+"\",\""+parameter2+"\":\""+o.y+"\",\""+parameter3+"\":\""+o.z+"\"},";
		}

		json += "]";
		
		return json;
	}
}
