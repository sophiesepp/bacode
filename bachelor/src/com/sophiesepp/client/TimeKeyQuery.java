package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.HeatmapObject;
import com.sophiesepp.shared.TimeKey;

public class TimeKeyQuery extends Heatmap implements EntryPoint {
	

	

	static VerticalPanel timekeyPanel = new VerticalPanel();
	static HorizontalPanel timekeycontentPanel = new HorizontalPanel();
	VerticalPanel lefttimekeyPanel = new VerticalPanel();
	VerticalPanel righttimekeyPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();

	VerticalPanel seperationPanel = new VerticalPanel();
	HorizontalPanel timekeyPanel1= new HorizontalPanel();
	HorizontalPanel  timekeyPanel2 = new HorizontalPanel();
	VerticalPanel timekeyLabel1= new VerticalPanel();
	VerticalPanel timekeyLabel2= new VerticalPanel();
	VerticalPanel timekeyLabel3= new VerticalPanel();
	VerticalPanel timekeyLabel4= new VerticalPanel();
	
	final Button showTimeKeyQueryButton = new Button("Run Query");
	
	final Label timekeygenreLabel = new Label("Select genre");	
	final Label timekeyngramLabel = new Label("Select ngram");
	final Label timekeycountryLabel = new Label("Select country");
	

	final static SuggestBox countryTimeKey = new SuggestBox(Srsr.country);
	final static SuggestBox genreTimeKey = new SuggestBox(Srsr.genre);
	final static TextBox ngramTimeKey = new TextBox();
	private static int i = 0;
	private static int yPixelValue = 10;
	private static HashMap<String,Integer> timekey = new HashMap<String,Integer>();
	
	
	
	
	public void onModuleLoad() {
		
		
		for (i=0;i<com.sophiesepp.client.Srsr.keys.length;i++){
			
			timekey.put(com.sophiesepp.client.Srsr.keys[i],yPixelValue);
			yPixelValue+=20;
		}
		
		
		timekeyPanel.addStyleName("timekeyPanel");
		timekeycontentPanel.addStyleName("timekeycontentPanel");
		lefttimekeyPanel.addStyleName("lefttimekeyPanel");
		righttimekeyPanel.addStyleName("righttimekeyPanel");
		timekeyPanel1.addStyleName("Panel1");
		timekeyPanel2.addStyleName("Panel2");
		

		timekeyLabel1.addStyleName("label");
		timekeyLabel2.addStyleName("label");
		timekeyLabel3.addStyleName("label");
		timekeyLabel4.addStyleName("buttonlabel");
		
		timekeygenreLabel.addStyleName("text3");		
		timekeyngramLabel.addStyleName("text3");
		timekeycountryLabel.addStyleName("text3");	
		
		countryTimeKey.addStyleName("textfield1");
		genreTimeKey.addStyleName("textfield1");
		ngramTimeKey.addStyleName("textfield1");
		
		showTimeKeyQueryButton.addStyleName("button1");
		
		seperationPanel.addStyleName("seperationPanel");
		
		showTimeKeyQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TimeKeyQuery.queryTimeKey();
			}
		});
	
	
		
		
		
		
		timekeyPanel.add(RootPanel.get("heading7"));
		timekeyPanel.add(timekeycontentPanel);
		timekeyPanel.add(seperationPanel);
		
		timekeycontentPanel.add(lefttimekeyPanel);
		timekeycontentPanel.add(righttimekeyPanel);
		
		lefttimekeyPanel.add(timekeyPanel1);
		lefttimekeyPanel.add(timekeyPanel2);
		

		
		timekeyPanel1.add(RootPanel.get("heatmapTimeKeyCanvas"));	
		timekeyPanel1.add(RootPanel.get("legende240"));

		

		timekeyLabel1.add(timekeygenreLabel);
		timekeyLabel1.add(genreTimeKey);
		timekeyLabel2.add(timekeyngramLabel);
		timekeyLabel2.add(ngramTimeKey);
		timekeyLabel3.add(timekeycountryLabel);
		timekeyLabel3.add(countryTimeKey);
		timekeyLabel4.add(showTimeKeyQueryButton);

		timekeyPanel2.add(timekeyLabel1);
		timekeyPanel2.add(timekeyLabel2);
		timekeyPanel2.add(timekeyLabel3);
		timekeyPanel2.add(timekeyLabel4);
		
		scrollPanel.setHeight("423px");
		righttimekeyPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("righttimekeyPanel"));

	}
	
	public static void queryTimeKey() {

		greetingService.showQueryTimeKey(buildQueryTimeKey(),new AsyncCallback<List<TimeKey>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<TimeKey> result) {
			
				int yValue;
			
				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(TimeKey s: result)
				{			
					String key = s.key;
					yValue = timekey.get(key);
					
					HeatmapObject obj = new HeatmapObject(s.publication-1700+40,yValue+10,s.counts/s.totalpublications);	
					object.add(obj);
					
				}
	
				String json = createJson(object);
				displayDataTimeKey(json);
		
			}
		});
		
	}
	

	public static String buildQueryTimeKey(){

		String query="";
		String genre = "";
		String ngram = "";
		String country = "";
		int x = 0;
		int y = 0;
		int z = 0;

		if(!(genreTimeKey.getText().isEmpty()))
		{
			x=1;
			genre= genreTimeKey.getText();			
		}
		if(!(countryTimeKey.getText().isEmpty()))
		{
			y=1;
			country= countryTimeKey.getText();			
		}
		if(!(ngramTimeKey.getText().isEmpty()))
		{
			z=1;
			ngram= ngramTimeKey.getText();			
		}


		if((x==0) &&  (y==0) && (z==0)){
			query="SELECT publication AS publication,key AS key,count(key)AS counts FROM workspace.work GROUP BY publication,key"; 

		}
		if((x==0) &&  (y==0) && (z==1)){
			query= "SELECT table2.publication AS publication, table2.key AS key, count(table2.key) AS counts FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb GROUP BY publication,key";
		}
		if((x==1) &&  (y==0) && (z==0)){
			query="SELECT publication AS publication,key AS key,count(key)AS counts FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' GROUP BY publication,key"; 

		}
		if((x==1) &&  (y==0) && (z==1)){
			query= "SELECT table2.publication AS publication, table2.key AS key, count(table2.key) AS counts FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb GROUP BY publication,key";

		}
		if((x==0) &&  (y==1) && (z==0)){
			query= "SELECT table1.publication AS publication, table1.key AS key, count(table1.key) AS counts FROM(SELECT personId AS personId,publication AS publication,key AS key FROM workspace.work) AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table2 ON table1.personId = table2.personId GROUP BY publication,key";
		}
		if((x==0) &&  (y==1) && (z==1)){
			query= "SELECT table3.publication AS publication, table3.key AS key,count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication, table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY publication,key";
		}
		
		if((x==1) &&  (y==1) && (z==0)){
			query= "SELECT table1.publication AS publication, table1.key AS key, count(table1.key) AS counts FROM(SELECT personId AS personId,publication AS publication,key AS key,genre AS genre FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table2 ON table1.personId = table2.personId GROUP BY publication,key";
		}
		if((x==1) &&  (y==1) && (z==1)){
			query= "SELECT table3.publication AS publication, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY publication,key";
		}

		System.out.println(query);

		return query;
	}


	
	public static native void displayDataTimeKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

	$wnd.yy.store.setDataSet(obj);
	}-*/;

}
