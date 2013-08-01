package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class MostUsedNgrams extends D3Object3Parameter implements IsSerializable {
	

	public MostUsedNgrams() {
		// TODO Auto-generated constructor stub
	}
	
	public MostUsedNgrams(int publication, String ngram,
			int counts) {
		super();
	
		
		this.publication = publication;
		this.ngram = ngram;
		this.counts = counts;
	}
	


	public String ngram;
	public int publication;
	public int counts;
	
	



}
