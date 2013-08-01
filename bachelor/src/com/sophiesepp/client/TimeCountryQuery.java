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
import com.sophiesepp.shared.TimeCountry;

public class TimeCountryQuery extends Heatmap implements EntryPoint {
	
	

	static VerticalPanel  timecountryPanel = new VerticalPanel();
	static HorizontalPanel timecountrycontentPanel = new HorizontalPanel();
	VerticalPanel lefttimecountryPanel = new VerticalPanel();
	VerticalPanel righttimecountryPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	VerticalPanel seperationPanel = new VerticalPanel();
	
	HorizontalPanel timecountryPanel1= new HorizontalPanel();
	HorizontalPanel  timecountryPanel2 = new HorizontalPanel();
	VerticalPanel timecountryLabel1= new VerticalPanel();
	VerticalPanel timecountryLabel2= new VerticalPanel();
	VerticalPanel timecountryLabel3= new VerticalPanel();
	VerticalPanel timecountryLabel4= new VerticalPanel();
	
	private static Button showTimeCountryQueryButton = new Button("Run Query");
	
	private static Label timecountrygenreQuery = new Label("Select genre");
	private static Label timecountrykeyQuery = new Label("Select key");
	private static Label timecountryngramQuery = new Label("Select ngram");
	
	private static TextBox ngramTimeCountry = new TextBox();
	private static SuggestBox genreTimeCountry = new SuggestBox(Srsr.genre);
	private static SuggestBox keyTimeCountry = new SuggestBox(Srsr.key);
	private static int i = 0;
	private static int yPixelValue = 10;
	private static HashMap<String,Integer> timecountry = new HashMap<String,Integer>();
	

	
	public void onModuleLoad() {
			
		
		
		for (i=0;i<Srsr.countries.length;i++){
			
			timecountry.put(Srsr.countries[i],yPixelValue);
			yPixelValue+=20;
		}
		
		

		timecountryLabel1.addStyleName("label");
		timecountryLabel2.addStyleName("label");
		timecountryLabel3.addStyleName("label");
		timecountryLabel4.addStyleName("buttonlabel");

		timecountryPanel.addStyleName("timecountryPanel");
		timecountrycontentPanel.addStyleName("timecountrycontentPanel");
		lefttimecountryPanel.addStyleName("lefttimecountryPanel");
		righttimecountryPanel.addStyleName("righttimecountryPanel");
		timecountryPanel1.addStyleName("alternativePanel1");
		timecountryPanel2.addStyleName("Panel2");
		
		
		
		timecountryngramQuery.addStyleName("text3");	
		timecountrygenreQuery.addStyleName("text3");
		timecountrykeyQuery.addStyleName("text3");	
		
		ngramTimeCountry.addStyleName("textfield1");
		genreTimeCountry.addStyleName("textfield1");
		keyTimeCountry.addStyleName("textfield1");
		
		showTimeCountryQueryButton.addStyleName("button1");
		
		seperationPanel.addStyleName("seperationPanel");
		
		
		showTimeCountryQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TimeCountryQuery.queryTimeCountry();
			}
		});
		


		
		timecountryPanel.add(RootPanel.get("heading3"));
		timecountryPanel.add(timecountrycontentPanel);
		timecountryPanel.add(seperationPanel);
		
		timecountrycontentPanel.add(lefttimecountryPanel);
		timecountrycontentPanel.add(righttimecountryPanel);
	
		lefttimecountryPanel.add(timecountryPanel1);
		lefttimecountryPanel.add(timecountryPanel2);

		
		timecountryPanel1.add(RootPanel.get("heatmapTimeCountryCanvas"));	
		timecountryPanel1.add(RootPanel.get("legende560"));



		timecountryLabel1.add(timecountrygenreQuery);
		timecountryLabel1.add(genreTimeCountry);
		timecountryLabel2.add(timecountrykeyQuery);
		timecountryLabel2.add(keyTimeCountry);
		timecountryLabel3.add(timecountryngramQuery);
		timecountryLabel3.add(ngramTimeCountry);
		timecountryLabel4.add(showTimeCountryQueryButton);


		timecountryPanel2.add(timecountryLabel1);
		timecountryPanel2.add(timecountryLabel2);
		timecountryPanel2.add(timecountryLabel3);
		timecountryPanel2.add(timecountryLabel4);
		
		
		scrollPanel.setHeight("500px");
		righttimecountryPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("righttimecountryPanel"));
		
		
		
		

	}

	
	public static void queryTimeCountry() {

		greetingService.showQueryTimeCountry(buildQueryTimeCountry(),
				new AsyncCallback<List<TimeCountry>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<TimeCountry> result) {
			
				int yValue;
				
				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(TimeCountry s: result)
				{			
					String key = s.country;
					yValue = timecountry.get(key);
				
					HeatmapObject obj = new HeatmapObject(s.publication-1700+120,yValue+10,s.counts/s.totalpublications);	
					object.add(obj);
					System.out.println("queryspeccountsTIMECOUNTRY: "+s.counts);
					System.out.println("totalcountsTIMECOUNTRY: "+s.totalpublications);
					
					System.out.println("Z-KoordinateTIMECOUNTRY: "+obj.z);
					
				}
	
				String json = createJson(object);
				displayDataTimeCountry(json);
			

			}
		});
	}
	
	
	public static String buildQueryTimeCountry(){

		String ngram="";
		String genre="";
		String key="";
		String query = "";
		int x=0;
		int y=0;
		int z=0;

		if(!(ngramTimeCountry.getText().isEmpty()))
		{
			x=1;
			ngram= ngramTimeCountry.getText();			
		}
		if(!(genreTimeCountry.getText().isEmpty()))
		{
			y=1;
			genre= genreTimeCountry.getText();			
		}
		if(!(keyTimeCountry.getText().isEmpty()))
		{
			z=1;
			key= keyTimeCountry.getText();			
		}

		if((x==0) &&  (y==0) && (z==0)){
			query="SELECT publication AS publication, country AS country, count(country) AS counts FROM workspace.composer AS composers JOIN (SELECT publication,personId FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY publication,country"; 

		}
		if((x==1) &&  (y==0) && (z==0)){
			query= "SELECT table3.publication AS publication, table4.country AS country,count(table4.country) AS hits FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+= ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,country";
		}
		if((x==0) &&  (y==1) && (z==0)){
			query= "SELECT table1.publication AS publication, table2.country AS country, count(table2.country) AS hits FROM(SELECT personId AS personId,publication AS publication,genre AS genre FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer) AS table2 ON table1.personId = table2.personId GROUP BY publication,country";
		}
		if((x==0) &&  (y==0) && (z==1)){
			query= "SELECT table1.publication AS publication, table2.country AS country, count(table2.country) AS hits FROM(SELECT personId AS personId,publication AS publication,key AS key FROM workspace.work WHERE key='";
			query+=key;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer) AS table2 ON table1.personId = table2.personId GROUP BY publication,country";

		}
		if((x==1) &&  (y==1) && (z==0)){
			query= "SELECT table3.publication AS publication, table4.country AS age,count(table4.country) AS hits FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="') AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,country";
		}
		if((x==0) &&  (y==1) && (z==1)){
			query= "SELECT table1.publication AS publication, table2.country AS country, count(table2.country) AS hits FROM(SELECT personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' AND key='";
			query+=key;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer) AS table2 ON table1.personId = table2.personId GROUP BY publication,country";
		}
		if((x==1) &&  (y==0) && (z==1)){
			query= "SELECT table3.publication AS publication, table4.country AS country,count(table4.country) AS hits FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.key='";
			query+=key;
			query+="') AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,country";
		}
		if((x==1) &&  (y==1) && (z==1)){
			query= "SELECT table3.publication AS publication, table4.country AS country,count(table4.country) AS hits FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="' AND table2.key='";
			query+=key;
			query+="') AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,country";

		}

		System.out.println(query);

		return query;
	}


	public static native void displayDataTimeCountry(String data) /*-{


			var obj = eval('('+data+')');


			// call the heatmap's store's setDataSet method in order to set static data
			$wnd.xy.store.setDataSet(obj);

	}-*/;



}
