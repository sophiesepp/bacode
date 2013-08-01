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
import com.sophiesepp.shared.MostUsedGenres;

public class MostUsedGenresQuery extends D3 implements EntryPoint {
	

	static VerticalPanel mostusedgenresPanel = new VerticalPanel();
	static HorizontalPanel mostusedgenrescontentPanel = new HorizontalPanel();
	VerticalPanel leftmostusedgenresPanel = new VerticalPanel();
	VerticalPanel rightmostusedgenresPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();

	HorizontalPanel mostusedgenresPanel1= new HorizontalPanel(); 
	
	public void onModuleLoad() {

		mostusedgenresPanel.addStyleName("mostusedgenresPanel");
		mostusedgenrescontentPanel.addStyleName("mostusedgenrescontentPanel");
		leftmostusedgenresPanel.addStyleName("leftmostusedgenresPanel");
		rightmostusedgenresPanel.addStyleName("rightmostusedgenresPanel");
		mostusedgenresPanel1.addStyleName("mostusedgenresPanel1");
		
		
		mostusedgenresPanel.add(RootPanel.get("heading12"));
		mostusedgenresPanel.add(mostusedgenrescontentPanel);
		mostusedgenrescontentPanel.add(leftmostusedgenresPanel);
		mostusedgenrescontentPanel.add(rightmostusedgenresPanel);
		
		leftmostusedgenresPanel.add(mostusedgenresPanel1);
		
		mostusedgenresPanel1.add(RootPanel.get("multilinechartMostUsedGenres"));

		scrollPanel.setSize("340px","330px");
		rightmostusedgenresPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("rightmostusedgenresPanel"));

	}

	public static void queryMostUsedGenres() {

		greetingService.showQueryMostUsedGenres(buildQueryMostUsedGenres(),new AsyncCallback<List<MostUsedGenres>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<MostUsedGenres> result) {
				
				List<D3Object3Parameter> object = new ArrayList<D3Object3Parameter>();
				
				
				for(MostUsedGenres s: result)
				{			
					D3Object3Parameter obj = new D3Object3Parameter(s.publication,s.genre,s.counts);	
					object.add(obj);
					
				}
				
				String json = createJson3Parameter(object,"publication","genre","counts");

				displayDataMostUsedGenres(json);
			}
		});
	}


	public static String buildQueryMostUsedGenres(){

		String query = "SELECT genre AS genre, count(genre) AS counts FROM workspace.work GROUP BY genre ORDER BY counts DESC LIMIT 10";
		return query;
	}

	
	public static native void displayDataMostUsedGenres(String data) /*-{

	var obj = eval(data);
	$wnd.mostusedgenres(obj);	



	}-*/;
	

}
