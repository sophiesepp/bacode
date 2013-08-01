package com.sophiesepp.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.WorkDownloads;

public class WorkDownloadsPercentQuery extends D3{
	


	public static void queryWorkDownloadsPercent() {

		greetingService.showQueryWorkDownloadsPercent(buildQueryWorkDownloadsPercent(),	new AsyncCallback<List<WorkDownloads>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<WorkDownloads> result) {
			
				String json = "[";

				int a=0;
				int b=0;
				int c=0;
				int d=0;
				int e=0;
				int f=0;
				int g=0;
				int h=0;
				int j=0;
				int k=0;

				for(WorkDownloads s: result)
				{
					if(s.downloads<10000){
						a++;	
					}
					if(s.downloads>10000 && s.downloads<20000){
						b++;
					}
					if(s.downloads>20000 && s.downloads<30000){
						c++;
					}
					if(s.downloads>30000 && s.downloads<40000){
						d++;
					}
					if(s.downloads>40000 && s.downloads<50000){
						e++;
					}
					if(s.downloads>50000 && s.downloads<60000){
						f++;
					}
					if(s.downloads>60000 && s.downloads<70000){
						g++;
					}
					if(s.downloads>70000 && s.downloads<80000){
						h++;
					}

					if(s.downloads>80000 && s.downloads<90000){
						j++;
					}
					if(s.downloads>90000){
						k++;
					}

				}
				json += "{\"downloads\":\" < 10000\",\"works\":\""+a+"\"},";
				json += "{\"downloads\":\" 10.000 - 20.000\",\"works\":\""+b+"\"},";
				json += "{\"downloads\":\" 20.000 - 30.000\",\"works\":\""+c+"\"},";
				json += "{\"downloads\":\" 30.000 - 40.000\",\"works\":\""+d+"\"},";
				json += "{\"downloads\":\" 40.000 - 50.000\",\"works\":\""+e+"\"},";
				json += "{\"downloads\":\" 50.000 - 60.000\",\"works\":\""+f+"\"},";
				json += "{\"downloads\":\" 60.000 - 70.000\",\"works\":\""+g+"\"},";
				json += "{\"downloads\":\" 70.000 - 80.000\",\"works\":\""+h+"\"},";
				json += "{\"downloads\":\" 80.000 - 90.000\",\"works\":\""+j+"\"},";
				json += "{\"downloads\":\" 90.000 - 100.000\",\"works\":\""+k+"\"}";
				json += "]";

				
				displayDataWorkDownloadsPercent(json);
			}
		});
	}


	public static String buildQueryWorkDownloadsPercent(){

		String query ="SELECT workId AS workId, downloads AS downloads FROM workspace.work";
		return query;
	}
	
	public static native void displayDataWorkDownloadsPercent(String data) /*-{

	var obj = eval(data);
	$wnd.workdownloads(obj);	



}-*/;

	
}
