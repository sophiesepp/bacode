package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class WorkScorePercent extends D3Object2ParameterType1 implements IsSerializable {
	

	public WorkScorePercent() {
		// TODO Auto-generated constructor stub
	}
	
	public WorkScorePercent(int scores, int works) {
		super();
	
		
		this.scores = scores;
		this.works = works;
	
	}
	


	public int scores;
	public int works;
	
	




	

}
