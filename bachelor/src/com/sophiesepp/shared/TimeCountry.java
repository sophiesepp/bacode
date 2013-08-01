package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeCountry extends HeatmapObject implements IsSerializable {
	
	public TimeCountry() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeCountry(int publication, String country,
			double counts, double totalpublications) {
		super();
	
		
		this.publication = publication;
		this.country = country;
		this.counts = counts;
		this.totalpublications = totalpublications;
	}
	


	public String country;
	public int publication;
	public double counts;
	public double totalpublications;
	
	
}
