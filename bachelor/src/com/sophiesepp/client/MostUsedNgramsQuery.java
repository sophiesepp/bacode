package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.D3Object3Parameter;
import com.sophiesepp.shared.MostUsedNgrams;

public class MostUsedNgramsQuery extends D3 implements EntryPoint {
	



	static VerticalPanel mostusedngramsPanel = new VerticalPanel();
	static HorizontalPanel mostusedngramscontentPanel = new HorizontalPanel();
	VerticalPanel leftmostusedngramsPanel = new VerticalPanel();
	VerticalPanel rightmostusedngramsPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	HorizontalPanel mostusedngramsPanel1= new HorizontalPanel(); 

	
	public void onModuleLoad() {		

		mostusedngramsPanel.addStyleName("mostusedngramsPanel");
		mostusedngramscontentPanel.addStyleName("mostusedngramscontentPanel");
		leftmostusedngramsPanel.addStyleName("leftmostusedngramsPanel");
		rightmostusedngramsPanel.addStyleName("rightmostusedngramsPanel");
		mostusedngramsPanel1.addStyleName("mostusedngramsPanel1");
	
		
		mostusedngramsPanel.add(RootPanel.get("heading13"));
		mostusedngramsPanel.add(mostusedngramscontentPanel);
		mostusedngramscontentPanel.add(leftmostusedngramsPanel);
		mostusedngramscontentPanel.add(rightmostusedngramsPanel);
		
		leftmostusedngramsPanel.add(mostusedngramsPanel1);
		
		mostusedngramsPanel1.add(RootPanel.get("multilinechartMostUsedNgrams"));

		scrollPanel.setSize("225px","330px");
		rightmostusedngramsPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("rightmostusedngramsPanel"));


	}
	
	public static void queryMostUsedNgrams() {

		greetingService.showQueryMostUsedNgrams(buildQueryMostUsedNgrams(),new AsyncCallback<List<MostUsedNgrams>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<MostUsedNgrams> result) {

				List<D3Object3Parameter> object = new ArrayList<D3Object3Parameter>();
				
				
				for(MostUsedNgrams s: result)
				{			
					D3Object3Parameter obj = new D3Object3Parameter(s.publication,s.ngram,s.counts);	
					object.add(obj);
					
				}
				
				String json = createJson3Parameter(object,"publication","ngram","counts");


				displayDataMostUsedNgrams(json);
			}
		});
	}
	

	
	public static String buildQueryMostUsedNgrams(){

		String query = "SELECT ngram AS ngram, count(ngram) AS counts FROM workspace.ngram GROUP BY ngram ORDER BY counts DESC LIMIT 10";
		return query;
	}


	public static native void displayDataMostUsedNgrams(String data) /*-{

	var obj = eval(data);
	$wnd.mostusedngrams(obj);	



	}-*/;	



}
