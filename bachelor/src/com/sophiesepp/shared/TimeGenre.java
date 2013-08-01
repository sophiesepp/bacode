package com.sophiesepp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TimeGenre extends D3Object3Parameter implements IsSerializable {
	

	public TimeGenre() {
		// TODO Auto-generated constructor stub
	}
	
	public TimeGenre(int publication, String genre,
			int counts) {
		super();
	
		
		this.publication = publication;
		this.genre = genre;
		this.counts = counts;
	}
	


	public String genre;
	public int publication;
	public int counts;
}
