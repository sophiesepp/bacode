package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeKey extends HeatmapObject implements IsSerializable {
	

	public TimeKey() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeKey(int publication, String key,
			double counts, double totalpublications) {
		super();
	
		
		this.publication = publication;
		this.key = key;
		this.counts = counts;
		this.totalpublications = totalpublications;
	}
	


	public String key;
	public int publication;
	public double counts;
	public double totalpublications;
	

	
	

}
