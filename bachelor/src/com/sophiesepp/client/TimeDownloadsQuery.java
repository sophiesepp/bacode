package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.D3Object2ParameterType1;
import com.sophiesepp.shared.TimeDownloads;
import com.sophiesepp.shared.TimeDownloadsNormalized;

public class TimeDownloadsQuery extends D3 implements EntryPoint  {
	

	static VerticalPanel timedownloadsPanel = new VerticalPanel();
	
	static VerticalPanel timedownloadscontentPanel = new VerticalPanel();
	HorizontalPanel lefttimedownloadsPanel = new HorizontalPanel();
	VerticalPanel righttimedownloadsPanel = new VerticalPanel();
	VerticalPanel seperationPanel = new VerticalPanel();
	


	

	public void onModuleLoad() {
		
		timedownloadsPanel.add(RootPanel.get("heading1"));
		timedownloadsPanel.add(timedownloadscontentPanel);
		timedownloadsPanel.add(seperationPanel);
		
		timedownloadscontentPanel.add(lefttimedownloadsPanel);
		timedownloadscontentPanel.add(righttimedownloadsPanel);
		
		lefttimedownloadsPanel.add(RootPanel.get("linechartTimeDownloads"));
		lefttimedownloadsPanel.add(RootPanel.get("linechartTimeDownloadsNormalized"));
		
		righttimedownloadsPanel.add(RootPanel.get("righttimedownloadsPanel"));
		
			
		timedownloadsPanel.addStyleName("timedownloadsPanel");
		timedownloadscontentPanel.addStyleName("timedownloadscontentPanel");
		lefttimedownloadsPanel.addStyleName("lefttimedownloadsPanel");
		seperationPanel.addStyleName("seperationPanel");
		


	
	}

	public static void queryTimeDownloads() {

		greetingService.showQueryTimeDownloads(buildQueryTimeDownloads(),new AsyncCallback<List<TimeDownloads>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<TimeDownloads> result) {

				List<D3Object2ParameterType1> object = new ArrayList<D3Object2ParameterType1>();
				
				
				for(TimeDownloads s: result)
				{			
					D3Object2ParameterType1 obj = new D3Object2ParameterType1(s.publication,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType1(object,"publication","downloads");


				displayDataTimeDownloads(json);
			}
		});
	}
	
	public static void queryTimeDownloadsNormalized() {

		greetingService.showQueryTimeDownloadsNormalized(buildQueryTimeDownloadsNormalized(),new AsyncCallback<List<TimeDownloadsNormalized>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<TimeDownloadsNormalized> result) {

				List<D3Object2ParameterType1> object = new ArrayList<D3Object2ParameterType1>();
				
				
				for(TimeDownloadsNormalized s: result)
				{			
					D3Object2ParameterType1 obj = new D3Object2ParameterType1(s.publication,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType1(object,"publication","downloads");


				displayDataTimeDownloadsNormalized(json);
			}
		});
	}





	public static String buildQueryTimeDownloads(){

		String query ="SELECT publication AS publication,sum(downloads) AS downloads FROM workspace.work GROUP BY publication ORDER BY publication ASC";
		return query;
	}
	
	public static String buildQueryTimeDownloadsNormalized (){

		String query ="SELECT publication AS publication,INTEGER(ROUND(sum(downloads)/count(publication))) AS downloads FROM workspace.work GROUP BY publication ORDER BY publication ASC";
		return query;
	}



	public static native void displayDataTimeDownloads(String data) /*-{

	var obj = eval(data);
	$wnd.timedownloads(obj);	

	}-*/;	
	
	public static native void displayDataTimeDownloadsNormalized (String data) /*-{

	var obj = eval(data);
	$wnd.timedownloadsNormalized(obj);	



	}-*/;

}
