package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.AgeDownloadsComposer;
import com.sophiesepp.shared.D3Object2ParameterType1;


public class AgeDownloadsComposerQuery extends D3 implements EntryPoint{

	


	static VerticalPanel agedownloadscomposerPanel = new VerticalPanel();
	static HorizontalPanel agedownloadscomposercontentPanel = new HorizontalPanel();
	VerticalPanel leftagedownloadscomposerPanel = new VerticalPanel();
	VerticalPanel rightagedownloadscomposerPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	VerticalPanel seperationPanel = new VerticalPanel();
	
	public void onModuleLoad() {
		
		agedownloadscomposerPanel.addStyleName("agedownloadscomposerPanel");
		agedownloadscomposercontentPanel.addStyleName("agedownloadscomposercontentPanel");
		leftagedownloadscomposerPanel.addStyleName("leftagedownloadscomposerPanel");
		rightagedownloadscomposerPanel.addStyleName("rightagedownloadscomposerPanel");
	
		seperationPanel.addStyleName("seperationPanelComposer");

		agedownloadscomposerPanel.add(RootPanel.get("heading15"));
		agedownloadscomposerPanel.add(agedownloadscomposercontentPanel);
		agedownloadscomposerPanel.add(seperationPanel);
		agedownloadscomposercontentPanel.add(leftagedownloadscomposerPanel);
		agedownloadscomposercontentPanel.add(rightagedownloadscomposerPanel);
		
		
		leftagedownloadscomposerPanel.add(RootPanel.get("linechartAgeDownloadsComposer"));
		rightagedownloadscomposerPanel.add(scrollPanel);

		scrollPanel.setSize("475px","300px");
		scrollPanel.add(RootPanel.get("rightagedownloadscomposerPanel"));
 
	}
	
	public static void queryAgeDownloadsComposer() {

		greetingService.showQueryAgeDownloadsComposer(buildQueryAgeDownloadsComposer(),new AsyncCallback<List<AgeDownloadsComposer>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<AgeDownloadsComposer> result) {
				
				List<D3Object2ParameterType1> object = new ArrayList<D3Object2ParameterType1>();
				
				
				for(AgeDownloadsComposer s: result)
				{			
					D3Object2ParameterType1 obj = new D3Object2ParameterType1(s.age,s.downloads);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType1(object,"age","downloads");


				displayDataAgeDownloadsComposer(json);
			}
			
		});
	}
	

	public static String buildQueryAgeDownloadsComposer(){

		String composer= Srsr.composerBox.getText();	
		String query ="";

		query= "SELECT (table1.publication-table2.birth) AS age, sum(table1.downloads) AS downloads FROM(SELECT personId AS personId,publication AS publication,downloads AS downloads FROM workspace.work) AS table1 JOIN(SELECT personId AS personId, birth AS birth FROM workspace.composer WHERE personId='";
		query+=composer;
		query+= "') AS table2 ON table1.personId = table2.personId GROUP BY age ORDER BY age ASC";

		return query;
	}


	public static native void displayDataAgeDownloadsComposer(String data) /*-{

	var obj = eval(data);
	$wnd.agedownloadscomposer(obj);	



	}-*/;	


}
