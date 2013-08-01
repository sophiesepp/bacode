package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class AgeDownloadsComposer extends D3Object2ParameterType1 implements IsSerializable {
	
	public AgeDownloadsComposer() {
		// TODO Auto-generated constructor stub
	}
	
	public AgeDownloadsComposer(int age,
			int downloads) {
		super();
	
		
		this.age = age;
		this.downloads = downloads;
	}
	


	public int age;
	public int downloads;
	

	

}
