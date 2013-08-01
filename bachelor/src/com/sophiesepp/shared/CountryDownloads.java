package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class CountryDownloads extends D3Object2ParameterType2 implements IsSerializable {
	

	public  CountryDownloads() {
		// TODO Auto-generated constructor stub
	}
	
	public  CountryDownloads(String country, int downloads) {
		super();
	
		this.country = country;
		this.downloads = downloads;
	}
	


	public String country;
	public int downloads;
	
	
	
}
