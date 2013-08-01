package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class KeyPercent extends D3Object2ParameterType2 implements IsSerializable {
	

	public  KeyPercent() {
		// TODO Auto-generated constructor stub
	}
	
	public  KeyPercent(String key, int counts) {
		super();
	
		this.key = key;
		this.counts = counts;
	}
	


	public String key;
	public int counts;
	
	
	

}
