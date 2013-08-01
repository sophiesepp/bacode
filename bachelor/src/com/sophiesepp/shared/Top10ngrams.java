package com.sophiesepp.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Top10ngrams implements IsSerializable {
	

	public Top10ngrams() {
		// TODO Auto-generated constructor stub
	}
	
	public Top10ngrams(String ngram,
			int counts) {
		super();
	
		this.ngram = ngram;
		this.counts = counts;
	}
	


	public String ngram;
	public int counts;
}
