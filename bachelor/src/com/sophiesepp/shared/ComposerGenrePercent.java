package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class ComposerGenrePercent  extends D3Object2ParameterType2 implements IsSerializable {
	

	public  ComposerGenrePercent() {
		// TODO Auto-generated constructor stub
	}
	
	public  ComposerGenrePercent(String genre, int counts) {
		super();
	
		this.genre = genre;
		this.counts = counts;
	}
	


	public String genre;
	public int counts;
	

}
