package com.sophiesepp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class NgramCountry  implements IsSerializable {
	
	public NgramCountry() {
		// TODO Auto-generated constructor stub
	}
	
	public NgramCountry(String country, int counts) {
		super();
	
		
		this.country = country;
		this.counts = counts;
	}
	


	public String country;
	public int counts;
	
}
