
package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class CountryKey extends HeatmapObject implements IsSerializable {
	

	public CountryKey() {
		// TODO Auto-generated constructor stub
	}
	
	public CountryKey(String key,
			int counts) {
		super();
	

		this.key = key;
		this.counts = counts;
	}
	

	public String key;
	public int counts;
	


}
