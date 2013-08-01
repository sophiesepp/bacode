package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.ComposerKeyPercent;
import com.sophiesepp.shared.D3Object2ParameterType2;

public class ComposerKeyPercentQuery extends D3 {




	public static void queryComposerKeyPercent() {

		greetingService.showQueryComposerKeyPercent(buildQueryComposerKey(),new AsyncCallback<List<ComposerKeyPercent>>(){
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<ComposerKeyPercent> result) {

				List<D3Object2ParameterType2> object = new ArrayList<D3Object2ParameterType2>();
				
				
				for(ComposerKeyPercent s: result)
				{			
					D3Object2ParameterType2 obj = new D3Object2ParameterType2(s.key,s.counts);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType2(object,"key","counts");


				displayDataComposerKeyPercent(json);
			}
		});
	}


	public static String buildQueryComposerKey(){

		String c = Srsr.composerBox.getText();

		String query = "SELECT key AS key, count(workId) AS workId FROM (SELECT table2.workId AS workId, table2.key AS key FROM workspace.composer AS table1 JOIN (SELECT personId AS personId, key AS key, workId AS workId FROM workspace.work) AS table2 ON table1.personId=table2.personId WHERE table1.personId='";
		query += c;
		query += "')GROUP BY key ORDER BY workId DESC";
		System.out.println(query);

		return query;
	}



	public static native void displayDataComposerKeyPercent(String data) /*-{

	var obj = eval(data);
	$wnd.composerkey(obj);	



	}-*/;
}
