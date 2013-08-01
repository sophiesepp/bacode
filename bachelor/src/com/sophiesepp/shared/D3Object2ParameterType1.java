package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class D3Object2ParameterType1 implements D3Object, IsSerializable {
	
	public  D3Object2ParameterType1() {
		// TODO Auto-generated constructor stub
	}
	
	public  D3Object2ParameterType1(int x,
			int y) {
		super();
	
		
		this.x = x;
		this.y = y;
	}
	


	public int x;
	public int y;
	

	

}