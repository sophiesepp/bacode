package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class D3Object3Parameter implements D3Object, IsSerializable {
	
	public  D3Object3Parameter() {
		// TODO Auto-generated constructor stub
	}
	
	public  D3Object3Parameter(int x,
			String y, int z) {
		super();
	
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	


	public int x;
	public String y;
	public int z;
	

	

}
