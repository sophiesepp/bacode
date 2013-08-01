package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class CountriesKey extends HeatmapObject implements IsSerializable {
	

	public CountriesKey() {
		// TODO Auto-generated constructor stub
	}
	
	public CountriesKey(String country,String key,
			double counts, double totalpublications) {
		super();

		this.country = country;
		this.key = key;
		this.counts = counts;
		this.totalpublications = totalpublications;
	}
	

	public String country;
	public String key;
	public double counts;
	public double totalpublications;
	
	

}