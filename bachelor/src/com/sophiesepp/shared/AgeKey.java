package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class AgeKey extends HeatmapObject implements IsSerializable {
	

	public AgeKey() {
		// TODO Auto-generated constructor stub
	}
	
	public AgeKey(int age, String key,
			double counts, double totalpublications) {
		super();
	
		
		this.age = age;
		this.key = key;
		this.counts = counts;
		this.totalpublications = totalpublications;
	}
	


	public String key;
	public int age;
	public double counts;
	public double totalpublications;
	


}
