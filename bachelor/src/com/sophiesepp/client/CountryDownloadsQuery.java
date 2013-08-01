package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.CountryDownloads;
import com.sophiesepp.shared.D3Object2ParameterType2;

public class CountryDownloadsQuery extends D3 implements EntryPoint{


	static VerticalPanel countrydownloadsPanel = new VerticalPanel();
	static HorizontalPanel countrydownloadscontentPanel = new HorizontalPanel();
	VerticalPanel leftcountrydownloadsPanel = new VerticalPanel();
	VerticalPanel rightcountrydownloadsPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	HorizontalPanel countrydownloadsPanel1= new HorizontalPanel(); 


	public void onModuleLoad() {

		countrydownloadsPanel.addStyleName("countrydownloadsPanel");
		countrydownloadscontentPanel.addStyleName("countrydownloadscontentPanel");
		leftcountrydownloadsPanel.addStyleName("leftcountrydownloadsPanel");
		rightcountrydownloadsPanel.addStyleName("rightcountrydownloadsPanel");
		countrydownloadsPanel1.addStyleName("countrydownloadsPanel1");
		

		countrydownloadsPanel.add(RootPanel.get("heading11"));
		countrydownloadsPanel.add(countrydownloadscontentPanel);
		
		countrydownloadscontentPanel.add(leftcountrydownloadsPanel);
		countrydownloadscontentPanel.add(rightcountrydownloadsPanel);
	
		leftcountrydownloadsPanel.add(countrydownloadsPanel1);
		countrydownloadsPanel1.add(RootPanel.get("barchartCountryDownloads"));
		

		scrollPanel.setSize("340px","365px");
		rightcountrydownloadsPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("rightcountrydownloadsPanel"));
		

	}

	public static void queryCountryDownloads() {

		greetingService.showQueryCountryDownloads(buildQueryCountryDownloads(),
				new AsyncCallback<List<CountryDownloads>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<CountryDownloads> result) {
				
				List<D3Object2ParameterType2> object = new ArrayList<D3Object2ParameterType2>();
				
				
				for(CountryDownloads s: result)
				{			
					D3Object2ParameterType2 obj = new D3Object2ParameterType2(s.country,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType2(object,"country","downloads");

				
				countrydownloads(json);
			}
		});
	}
	

	public static String buildQueryCountryDownloads(){

		String query = "SELECT country AS country, sum(downloads) AS percent FROM (SELECT country AS country, downloads AS downloads FROM workspace.composer AS composers JOIN (SELECT personId,downloads FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY downloads,country) GROUP BY country";
		return query;
	}

	public static native void countrydownloads(String data) /*-{

	var obj = eval(data);
	$wnd.countrydownloads(obj);	
	
	}-*/;	


}
