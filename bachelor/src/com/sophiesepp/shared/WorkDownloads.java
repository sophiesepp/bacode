package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class WorkDownloads extends D3Object2ParameterType2 implements IsSerializable {
	
	public WorkDownloads() {
		// TODO Auto-generated constructor stub
	}
	
	public WorkDownloads(String work, int downloads) {
		super();
	
		this.work = work;
		this.downloads = downloads;
	
	}
	


	public String work;
	public int downloads;
	


	
}
