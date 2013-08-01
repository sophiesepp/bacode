package com.sophiesepp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Top10genres implements IsSerializable {
	

	public Top10genres() {
		// TODO Auto-generated constructor stub
	}
	
	public Top10genres(String genre,
			int counts) {
		super();
	
		this.genre = genre;
		this.counts = counts;
	}
	


	public String genre;
	public int counts;
}
