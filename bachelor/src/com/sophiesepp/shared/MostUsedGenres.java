package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class MostUsedGenres extends D3Object3Parameter implements IsSerializable {
	

	public MostUsedGenres() {
		// TODO Auto-generated constructor stub
	}
	
	public MostUsedGenres(int publication, String genre,
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
