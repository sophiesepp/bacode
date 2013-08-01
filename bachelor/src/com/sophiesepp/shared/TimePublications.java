package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimePublications extends D3Object2ParameterType1 implements IsSerializable {
	

	public  TimePublications() {
		// TODO Auto-generated constructor stub
	}
	
	public  TimePublications(int publication, int counts) {
		super();
	
		this.publication = publication;
		this.counts = counts;
	}
	


	public int publication;
	public int counts;
	



}
