package com.sophiesepp.shared;


import com.google.gwt.user.client.rpc.IsSerializable;

public class AgePublicationsComposer extends D3Object2ParameterType1 implements IsSerializable {
	
	public AgePublicationsComposer() {
		// TODO Auto-generated constructor stub
	}
	
	public AgePublicationsComposer(int age,
			int counts) {
		super();
	
		
		this.age = age;
		this.counts = counts;
	}
	


	public int age;
	public int counts;
	

	


}
