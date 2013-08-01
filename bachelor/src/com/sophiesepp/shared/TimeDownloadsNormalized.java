package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeDownloadsNormalized extends D3Object2ParameterType1 implements IsSerializable {
	

	public  TimeDownloadsNormalized() {
		// TODO Auto-generated constructor stub
	}
	
	public  TimeDownloadsNormalized(int publication, int downloads) {
		super();
	
		this.publication = publication;
		this.downloads = downloads;
	}
	


	public int publication;
	public int downloads;
	


}
