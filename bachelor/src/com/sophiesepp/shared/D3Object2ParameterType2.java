package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class D3Object2ParameterType2 implements D3Object, IsSerializable {
	
	public  D3Object2ParameterType2() {
		// TODO Auto-generated constructor stub
	}
	
	public  D3Object2ParameterType2(String x,
			int y) {
		super();
	
		
		this.x = x;
		this.y = y;
	}
	


	public String x;
	public int y;
	

	

}
