package com.sophiesepp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GermanAustrianPercent implements IsSerializable {
	

	public  GermanAustrianPercent() {
		// TODO Auto-generated constructor stub
	}
	
	public  GermanAustrianPercent(String year, int counts) {
		super();
	
		this.year = year;
		this.counts = counts;
	}
	


	public String year;
	public int counts;
}
