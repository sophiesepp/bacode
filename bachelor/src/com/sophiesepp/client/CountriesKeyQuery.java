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
import com.sophiesepp.shared.CountriesKey;
import com.sophiesepp.shared.CountryKey;
import com.sophiesepp.shared.HeatmapObject;


public class CountriesKeyQuery extends Heatmap implements EntryPoint {


	static VerticalPanel countrieskeyPanel = new VerticalPanel();
	static HorizontalPanel countrieskeycontentPanel = new HorizontalPanel();
	VerticalPanel leftcountrieskeyPanel = new VerticalPanel();
	VerticalPanel rightcountrieskeyPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	VerticalPanel seperationPanel = new VerticalPanel();
	VerticalPanel countrieskeyPanel1= new VerticalPanel();
	HorizontalPanel  countrieskeyPanel2 = new HorizontalPanel();
	VerticalPanel countrieskeyLabel1 = new VerticalPanel();
	VerticalPanel countrieskeyLabel2 = new VerticalPanel();
	VerticalPanel countrieskeyLabel3 = new VerticalPanel();
	
	static VerticalPanel countrykeyPanel = new VerticalPanel();
	static HorizontalPanel countrykeycontentPanel = new HorizontalPanel();
	VerticalPanel leftcountrykeyPanel = new VerticalPanel();
	VerticalPanel rightcountrykeyPanel = new VerticalPanel();
	

	
	HorizontalPanel countrykeyPanel1= new HorizontalPanel();
	HorizontalPanel  countrykeyPanel2 = new HorizontalPanel();	
	VerticalPanel countrykeyLabel1 = new VerticalPanel();
	VerticalPanel countrykeyLabel2 = new VerticalPanel();
	
	final Button showCountryKeyQueryButton = new Button("Run Query");
	
	final static SuggestBox keyscountry = new SuggestBox(Srsr.country);
	final Label countryLabel = new Label("Select country");
	
	final Button showCountriesKeyQueryButton = new Button("Run Query");
	
	final Label countrieskeygenreLabel = new Label("Select genre");
	final Label countrieskeyngramLabel = new Label("Select ngram");
	
	final static SuggestBox genreCountriesKey = new SuggestBox(Srsr.genre);
	final static TextBox ngramCountriesKey = new TextBox();

	private static int i = 0;
	private static int xPixelValuecountries = 10;
	private static int yPixelValuecountries = 10;
	private static HashMap<String,Integer> countrieskeyX = new HashMap<String,Integer>();
	private static HashMap<String,Integer> countrieskeyY = new HashMap<String,Integer>();

	private static int y = 0;
	private static int yPixelValuecountry = 10;
	private static HashMap<String,Integer> countrykey = new HashMap<String,Integer>();
	
	
	public void onModuleLoad() {



		for (i=0;i<Srsr.countries.length;i++){

			countrieskeyX.put(Srsr.countries[i],xPixelValuecountries);
			xPixelValuecountries+=20;
		}
		for (i=0;i<Srsr.keys.length;i++){

			countrieskeyY.put(Srsr.keys[i],yPixelValuecountries);
			yPixelValuecountries+=20;
		}
	
		
		for (i=0;i<com.sophiesepp.client.Srsr.keys.length;i++){
			
			countrykey.put(com.sophiesepp.client.Srsr.keys[i],yPixelValuecountry);
			yPixelValuecountry+=20;
		}
	
		
		countrieskeyPanel.addStyleName("countrieskeyPanel");
		countrieskeycontentPanel.addStyleName("countrieskeycontentPanel");
		leftcountrieskeyPanel.addStyleName("leftcountrieskeyPanel");
		rightcountrieskeyPanel.addStyleName("rightcountrieskeyPanel");
		countrieskeyPanel1.addStyleName("countrieskeyPanel1");
		countrieskeyPanel2.addStyleName("countrieskeyPanel2");
		
		
		countrieskeyLabel1.addStyleName("label");
		countrieskeyLabel2.addStyleName("label");
		countrieskeyLabel3.addStyleName("buttonlabel");
		
		countrieskeygenreLabel.addStyleName("text3");
		countrieskeyngramLabel.addStyleName("text3");
		
		genreCountriesKey.addStyleName("textfield1");
		ngramCountriesKey.addStyleName("textfield1");
		
		showCountriesKeyQueryButton.addStyleName("button1");
		
		seperationPanel.addStyleName("seperationPanel");
		
		
		showCountriesKeyQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				queryCountriesKey();
			}
		});
		
		
	
		


	
		countrieskeyPanel.add(RootPanel.get("heading6"));
		countrieskeyPanel.add(countrieskeycontentPanel);
		countrieskeyPanel.add(seperationPanel);
	
		countrieskeycontentPanel.add(leftcountrieskeyPanel);
		countrieskeycontentPanel.add(rightcountrieskeyPanel);
		
		leftcountrieskeyPanel.add(countrieskeyPanel1);
		leftcountrieskeyPanel.add(countrieskeyPanel2);

	
		
		countrieskeyPanel1.add(RootPanel.get("heatmapCountriesKeyCanvas"));
		
		countrieskeyPanel2.add(countrieskeyLabel1);
		countrieskeyPanel2.add(countrieskeyLabel2);
		countrieskeyPanel2.add(countrieskeyLabel3);
		
		
		countrieskeyLabel1.add(countrieskeygenreLabel);
		countrieskeyLabel1.add(genreCountriesKey);
		countrieskeyLabel2.add(countrieskeyngramLabel);
		countrieskeyLabel2.add(ngramCountriesKey);
		countrieskeyLabel3.add(showCountriesKeyQueryButton);


		scrollPanel.setSize("274px","547px");
		rightcountrieskeyPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("rightcountrieskeyPanel"));

	
	
	}


	public static void queryCountriesKey() {

		greetingService.showQueryCountriesKey(buildQueryCountriesKey(),new AsyncCallback<List<CountriesKey>>() {
			public void onFailure(Throwable caught) {

			}

			public void onSuccess(List<CountriesKey> result) {

				int xValue;
				int yValue;

				List<HeatmapObject> object = new ArrayList<HeatmapObject>();		
				
				for(CountriesKey s: result)
				{			
					String country = s.country;
					String key = s.key;
		
					xValue = countrieskeyX.get(country);
					yValue = countrieskeyY.get(key);
					
					HeatmapObject obj = new HeatmapObject(xValue+30,yValue,(s.counts/s.totalpublications));	
					System.out.println("queryspeccounts: "+s.counts);
					System.out.println("totalcounts: "+s.totalpublications);
					
					System.out.println("Z-Koordinate: "+obj.z);
					object.add(obj);
					
				}
	
				String json = createJson(object);
				displayDataCountriesKey(json);

			}
		});
	}
	

	
	public static String buildQueryCountriesKey(){


		String query="";
		String genre = "";
		String ngram = "";
		int x = 0;
		int y = 0;

		if(!(genreCountriesKey.getText().isEmpty()))
		{
			x=1;
			genre= genreCountriesKey.getText();			
		}
		if(!(ngramCountriesKey.getText().isEmpty()))
		{
			y=1;
			ngram= ngramCountriesKey.getText();			
		}

		if((x==0) &&  (y==0) ){
			query="SELECT table1.country AS country, table2.key AS key,count(table2.key) AS counts FROM workspace.composer AS table1 JOIN(SELECT key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId=table2.personId GROUP BY country,key";

		}
		if((x==1) &&  (y==0) ){
			query="SELECT table1.country AS country, table2.key AS key,count(table2.key) AS counts FROM workspace.composer AS table1 JOIN(SELECT key AS key, genre AS genre, personId AS personId FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.personId=table2.personId GROUP BY country,key";

		}
		if((x==0) &&  (y==1) ){
			query= "SELECT table4.country AS country, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication, key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication, key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth,country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY country,key";
		}
		if((x==1) &&  (y==1) ){
			query= "SELECT table4.country AS country, table3.key AS key, count(table3.key) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication, key AS key FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication, key AS key, genre AS genre FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="') AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY country,key";
		}
		
		System.out.println(query);

		return query;
	}



	
	public static native void displayDataCountryKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

		$wnd.aa.store.setDataSet(obj);
}-*/;

	public static native void displayDataCountriesKey(String data) /*-{

	var obj = eval('('+data+')');


	// call the heatmap's store's setDataSet method in order to set static data

		$wnd.bb.store.setDataSet(obj);
}-*/;



}