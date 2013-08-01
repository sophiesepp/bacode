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
import com.sophiesepp.shared.AgeKey;
import com.sophiesepp.shared.HeatmapObject;


public class AgeKeyQuery  extends Heatmap implements EntryPoint{



	static VerticalPanel agekeyPanel = new VerticalPanel();
	static HorizontalPanel agekeycontentPanel = new HorizontalPanel();
	VerticalPanel leftagekeyPanel = new VerticalPanel();
	VerticalPanel rightagekeyPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	VerticalPanel seperationPanel = new VerticalPanel();
	HorizontalPanel agekeyPanel1= new HorizontalPanel();
	HorizontalPanel  agekeyPanel2 = new HorizontalPanel();
	VerticalPanel agekeyLabel1= new VerticalPanel();
	VerticalPanel agekeyLabel2= new VerticalPanel();
	VerticalPanel agekeyLabel3= new VerticalPanel();
	VerticalPanel agekeyLabel4= new VerticalPanel();
	
	
	final Button showAgeKeyQueryButton = new Button("Run Query");
	

	final Label agekeygenreLabel = new Label("Select genre");
	final Label agekeyngramLabel = new Label("Select ngram");
	final Label agekeycountryLabel = new Label("Select country");
	

	final static SuggestBox countryAgeKey = new SuggestBox(Srsr.country);
	final static SuggestBox genreAgeKey = new SuggestBox(Srsr.genre);
	final static TextBox ngramAgeKey = new TextBox();
	private static int i = 0;	
	private static int yPixelValue = 10;
	private static HashMap<String,Integer> agekey = new HashMap<String,Integer>();
	
	

	public void onModuleLoad() {
		
		for (i=0;i<com.sophiesepp.client.Srsr.keys.length;i++){

			agekey.put(com.sophiesepp.client.Srsr.keys[i],yPixelValue);
			yPixelValue+=20;
		}

		
		agekeyPanel.addStyleName("agekeyPanel");
		agekeycontentPanel.addStyleName("agekeycontentPanel");
		leftagekeyPanel.addStyleName("leftagekeyPanel");
		rightagekeyPanel.addStyleName("rightagekeyPanel");
		agekeyPanel1.addStyleName("Panel1");
		agekeyPanel2.addStyleName("Panel2");
		
		agekeyLabel1.addStyleName("label");
		agekeyLabel2.addStyleName("label");
		agekeyLabel3.addStyleName("label");
		agekeyLabel4.addStyleName("buttonlabel");
		
		agekeygenreLabel.addStyleName("text3");
		agekeyngramLabel.addStyleName("text3");
		agekeycountryLabel.addStyleName("text3");	
		
		countryAgeKey.addStyleName("textfield1");
		genreAgeKey.addStyleName("textfield1");
		ngramAgeKey.addStyleName("textfield1");
		
		showAgeKeyQueryButton.addStyleName("button1");
		
		seperationPanel.addStyleName("seperationPanel");
		
		showAgeKeyQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AgeKeyQuery.queryAgeKey();
			}
		});
		
		
		
	
		
		
		agekeyPanel.add(RootPanel.get("heading8"));
		agekeyPanel.add(agekeycontentPanel);
		agekeyPanel.add(seperationPanel);
		
		agekeycontentPanel.add(leftagekeyPanel);
		agekeycontentPanel.add(rightagekeyPanel);
		
		leftagekeyPanel.add(agekeyPanel1);
		leftagekeyPanel.add(agekeyPanel2);
	
		
		agekeyPanel1.add(RootPanel.get("heatmapAgeKeyCanvas"));
		agekeyPanel1.add(RootPanel.get("secondlegende240"));
		
		agekeyLabel1.add(agekeygenreLabel);
		agekeyLabel1.add(genreAgeKey);
		agekeyLabel2.add(agekeyngramLabel);
		agekeyLabel2.add(ngramAgeKey);
		agekeyLabel3.add(agekeycountryLabel);
		agekeyLabel3.add(countryAgeKey);
		agekeyLabel4.add(showAgeKeyQueryButton);

		agekeyPanel2.add(agekeyLabel1);
		agekeyPanel2.add(agekeyLabel2);
		agekeyPanel2.add(agekeyLabel3);
		agekeyPanel2.add(agekeyLabel4);



		scrollPanel.setHeight("423px");
		rightagekeyPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("rightagekeyPanel"));
		




	}

	public static void queryAgeKey() {

		greetingService.showQueryAgeKey(buildQueryAgeKey(),new AsyncCallback<List<AgeKey>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<AgeKey> result){

			
				int yValue;

				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(AgeKey s: result)
				{			
					String key = s.key;
					yValue = agekey.get(key);
					
					HeatmapObject obj = new HeatmapObject((s.age*3)+40,yValue+10,s.counts/s.totalpublications);	
					object.add(obj);
					
				}
	
				String json = createJson(object);
				displayDataAgeKey(json);
			

			}
		});
	}
	
	public static String buildQueryAgeKey(){

		
		String country="";
		String genre="";
		String ngram="";
		String query = "";
		int x=0;
		int y=0;
		int z=0;
		
		if(!(countryAgeKey.getText().isEmpty()))
		{
			x=1;
			country= countryAgeKey.getText();			
		}
		if(!(genreAgeKey.getText().isEmpty()))
		{
			y=1;
			genre= genreAgeKey.getText();			
		}
		
		if(!(ngramAgeKey.getText().isEmpty()))
		{
			z=1;
			ngram= ngramAgeKey.getText();			
		}

		if((x==0) &&  (y==0) && (z==0)){
			query= "SELECT table2.publication-table1.birth AS age, table2.key AS key,count(table2.key) AS counts FROM workspace.composer AS table1 JOIN(SELECT publication AS publication,key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId=table2.personId GROUP BY age,key"; 

		}
		if((x==0) &&  (y==0) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS age, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY age,key";
		}
		if((x==1) &&  (y==0) && (z==0)){
			query= "SELECT (table1.publication-table2.birth) AS age, table1.key AS key, count(table1.key) AS counts FROM  workspace.work AS table1 JOIN(SELECT personId AS personId,birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table2 ON table1.personId = table2.personId GROUP BY age,key";
		}
		if((x==1) &&  (y==0) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS age, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY age,key";
		}
		if((x==0) &&  (y==1) && (z==0)){
			query= "SELECT table2.publication-table1.birth AS age, table2.key AS key,count(table2.key) AS counts FROM workspace.composer AS table1 JOIN(SELECT publication AS publication,key AS key,genre AS genre, personId AS personId FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.personId=table2.personId GROUP BY age,key";  
		}
		if((x==0) &&  (y==1) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS age, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key,table2.genre AS genre FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY age,key";
		}
		
		if((x==1) &&  (y==1) && (z==0)){
			query= "SELECT table2.publication-table1.birth AS age, table2.key AS key,count(table2.key) AS counts FROM (SELECT personId AS personId, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="')AS table1 JOIN(SELECT publication AS publication,key AS key,genre AS genre, personId AS personId FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.personId=table2.personId GROUP BY age,key";  
		}
		if((x==1) &&  (y==1) && (z==1)){
			query= "SELECT (table3.publication-table4.birth) AS age, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication,table2.key AS key,table2.genre AS genre FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY age,key";
		}

		
		System.out.println(query);

		return query;
	}


	public static native void displayDataAgeKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

	$wnd.zz.store.setDataSet(obj);
	}-*/;

}
