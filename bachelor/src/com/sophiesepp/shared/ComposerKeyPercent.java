package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class ComposerKeyPercent extends D3Object2ParameterType2 implements IsSerializable {
	

	public  ComposerKeyPercent() {
		// TODO Auto-generated constructor stub
	}
	
	public  ComposerKeyPercent(String key, int counts) {
		super();
	
		this.key = key;
		this.counts = counts;
	}
	


	public String key;
	public int counts;
	
	
}
