package com.sophiesepp.client;

import java.util.ArrayList;
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
import com.sophiesepp.shared.D3Object2ParameterType1;
import com.sophiesepp.shared.TimePublications;

public class TimePublicationsQuery extends D3 implements EntryPoint{
	


	static VerticalPanel  timepublicationsPanel = new VerticalPanel();
	static HorizontalPanel timepublicationscontentPanel = new HorizontalPanel();
	VerticalPanel lefttimepublicationsPanel = new VerticalPanel();
	VerticalPanel righttimepublicationsPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	
	VerticalPanel seperationPanel = new VerticalPanel();
	VerticalPanel timepublicationsPanel1 = new VerticalPanel();
	HorizontalPanel timepublicationsPanel2 = new HorizontalPanel();
	VerticalPanel timepublicationsLabel1= new VerticalPanel();
	VerticalPanel timepublicationsLabel2= new VerticalPanel();
	VerticalPanel timepublicationsLabel3= new VerticalPanel();
	VerticalPanel timepublicationsLabel4= new VerticalPanel();
	VerticalPanel timepublicationsLabel5= new VerticalPanel();
	
	final Button showTimePublicationsQueryButton = new Button("Run Query");
	
	
	final Label timepublicationscountryLabel = new Label("Select country");
	final Label timepublicationsgenreLabel = new Label("Select genre");	
	final Label timepublicationskeyLabel = new Label("Select key");
	final Label timepublicationsngramLabel = new Label("Select ngram");
	
	
	final static SuggestBox countryTimePublications = new SuggestBox(Srsr.country);
	final static SuggestBox genreTimePublications = new SuggestBox(Srsr.genre);
	final static SuggestBox keyTimePublications = new SuggestBox(Srsr.key);
	final static TextBox ngramTimePublications = new TextBox();
	
	public void onModuleLoad() {
		

		timepublicationsPanel.addStyleName("timepublicationsPanel");
		timepublicationscontentPanel.addStyleName("timepublicationscontentPanel");
		lefttimepublicationsPanel.addStyleName("lefttimepublicationsPanel");
		righttimepublicationsPanel.addStyleName("righttimepublicationsPanel");
		timepublicationsPanel1.addStyleName("Panel1");
		timepublicationsPanel2.addStyleName("timepublicationsPanel2");
		timepublicationsLabel1.addStyleName("label");
		timepublicationsLabel2.addStyleName("label");
		timepublicationsLabel3.addStyleName("label");
		timepublicationsLabel4.addStyleName("label");
		timepublicationsLabel5.addStyleName("buttonlabel");
		
		timepublicationscountryLabel.addStyleName("text3");
		timepublicationsgenreLabel.addStyleName("text3");
		timepublicationskeyLabel.addStyleName("text3");	
		timepublicationsngramLabel.addStyleName("text3");	
		
		countryTimePublications.addStyleName("textfield1");
		genreTimePublications.addStyleName("textfield1");
		keyTimePublications.addStyleName("textfield1");
		ngramTimePublications.addStyleName("textfield1");
		
		showTimePublicationsQueryButton.addStyleName("button1");
		
		seperationPanel.addStyleName("seperationPanel");
		
		

		showTimePublicationsQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				TimePublicationsQuery.queryTimePublications();
			}
		});
		

		
	
		
		
		timepublicationsPanel.add(RootPanel.get("heading4"));
		timepublicationsPanel.add(timepublicationscontentPanel);
		timepublicationsPanel.add(seperationPanel);
		
		timepublicationscontentPanel.add(lefttimepublicationsPanel);
		timepublicationscontentPanel.add(righttimepublicationsPanel);
		
		lefttimepublicationsPanel.add(timepublicationsPanel1);
		lefttimepublicationsPanel.add(timepublicationsPanel2);
	

		
		timepublicationsPanel1.add(RootPanel.get("linechartTimePublications"));


		
		timepublicationsLabel1.add(timepublicationsgenreLabel);
		timepublicationsLabel1.add(genreTimePublications);
		timepublicationsLabel2.add(timepublicationskeyLabel);
		timepublicationsLabel2.add(keyTimePublications);
		timepublicationsLabel3.add(timepublicationsngramLabel);
		timepublicationsLabel3.add(ngramTimePublications);
		timepublicationsLabel4.add(timepublicationscountryLabel);
		timepublicationsLabel4.add(countryTimePublications);
		timepublicationsLabel5.add(showTimePublicationsQueryButton);

		timepublicationsPanel2.add(timepublicationsLabel1);
		timepublicationsPanel2.add(timepublicationsLabel2);
		timepublicationsPanel2.add(timepublicationsLabel3);
		timepublicationsPanel2.add(timepublicationsLabel4);
		timepublicationsPanel2.add(timepublicationsLabel5);
		
		
		scrollPanel.setHeight("410px");
		righttimepublicationsPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("righttimepublicationsPanel"));
		
		
		






	}

	public static void queryTimePublications() {

		greetingService.showQueryTimePublications(buildQueryTimePublications(),new AsyncCallback<List<TimePublications>>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(List<TimePublications> result) {

				List<D3Object2ParameterType1> object = new ArrayList<D3Object2ParameterType1>();
				
				
				for(TimePublications s: result)
				{			
					D3Object2ParameterType1 obj = new D3Object2ParameterType1(s.publication,s.counts);	
					object.add(obj);
					
				}
				
				String json = createJson2ParameterType1(object,"publication","counts");


				displayDataTimePublications(json);
			}
		});
	}

	public static String buildQueryTimePublications(){

		String country="";
		String genre="";
		String key="";
		String ngram="";
		String query = "";
		int x=0;
		int y=0;
		int z=0;
		int n=0;

		if(!(countryTimePublications.getText().isEmpty()))
		{
			x=1;
			country= countryTimePublications.getText();			
		}
		if(!(genreTimePublications.getText().isEmpty()))
		{
			y=1;
			genre= genreTimePublications.getText();			
		}
		if(!(keyTimePublications.getText().isEmpty()))
		{
			z=1;
			key= keyTimePublications.getText();			
		}
		if(!(ngramTimePublications.getText().isEmpty()))
		{
			n=1;
			ngram= ngramTimePublications.getText();			
		}

		if((x==0) &&  (y==0) && (z==0) && (n==0)){
			query="SELECT publication AS publication,count(publication) AS counts FROM workspace.work GROUP BY publication ORDER BY publication ASC";

		}
		if((x==0) &&  (y==0) && (z==0) && (n==1)){
			query= "SELECT table2.publication AS publication,count(table2.publication) AS counts FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb GROUP BY publication ORDER BY publication ASC";


		}
		if((x==1) && (y==0) && (z==0) && (n==0)){
			query= "SELECT table1.publication AS publication, count(table1.publication) AS counts FROM(SELECT personId AS personId,publication AS publication FROM workspace.work) AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+= "') AS table2 ON table1.personId = table2.personId GROUP BY publication ORDER BY publication ASC";
		}
		if((x==1) &&  (y==0) && (z==0) && (n==1)){
			query= "SELECT table3.publication AS publication, count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY publication ORDER BY publication ASC";

		}
		if((x==0) &&  (y==1) && (z==0) && (n==0)){
			query="SELECT publication AS publication,count(publication) AS counts FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' GROUP BY publication ORDER BY publication ASC";
		}
		if((x==0) &&  (y==1) && (z==0) && (n==1)){
			query= "SELECT table3.publication AS publication,count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="') AS table3 JOIN (SELECT personId AS personb, birth AS birth FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication ORDER BY publication ASC";

		}
		if((x==0) &&  (y==0) && (z==1) && (n==0)){
			query="SELECT publication AS publication,count(publication) AS counts FROM workspace.work WHERE key='";
			query+=key;
			query+="' GROUP BY publication ORDER BY publication ASC";

		}
		if((x==0) &&  (y==0) && (z==1) && (n==1)){
			query= "SELECT table3.publication AS publication, (table3.publication-table4.birth) AS age,count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.key='";
			query+=key;
			query+="') AS table3 JOIN (SELECT personId AS personb, birth AS birth FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,age";


		}
		if((x==1) &&  (y==1) && (z==0) && (n==0)){
			query= "SELECT table1.publication AS publication, count(table1.publication) AS counts FROM(SELECT personId AS personId,publication AS publication,genre AS genre FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="' GROUP BY publication ORDER BY publication ASC";
		}
		if((x==1) &&  (y==1) && (z==0) && (n==1)){
			query= "SELECT table3.publication AS publication, count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY publication ORDER BY publication ASC";
		}
		if((x==0) &&  (y==1) && (z==1) && (n==0)){
			query="SELECT publication AS publication,count(publication) AS counts FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' AND key='";
			query+=key;
			query+="' GROUP BY publication ORDER BY publication ASC";
		}
		if((x==0) &&  (y==1) && (z==1) && (n==1)){
			query= "SELECT table3.publication AS publication, (table3.publication-table4.birth) AS age,count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.genre='";
			query+=genre;
			query+="' AND key='";
			query+=key;
			query+="') AS table3 JOIN (SELECT personId AS personb, birth AS birth FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,age";
		}
		if((x==1) &&  (y==0) && (z==1) && (n==0)){
			query= "SELECT table1.publication AS publication, count(table1.publication) AS counts FROM(SELECT personId AS personId,publication AS publication,key AS key FROM workspace.work WHERE key='";
			query+=key;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="' GROUP BY publication ORDER BY publication ASC";
		}
		if((x==1) &&  (y==0) && (z==1) && (n==1)){
			query= "SELECT table3.publication AS publication, count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE key='";
			query+=key;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY publication ORDER BY publication ASC";
		}
		if((x==1) &&  (y==1) && (z==1) && (n==0)){
			query= "SELECT table1.publication AS publication, count(table1.publication) AS counts FROM(SELECT personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' AND key='";
			query+=key;
			query+="') AS table1 JOIN(SELECT personId AS personId, country AS country FROM workspace.composer WHERE country='";
			query+=country;
			query+="' GROUP BY publication ORDER BY publication ASC";
		}
		if((x==1) &&  (y==1) && (z==1) && (n==1)){
			query= "SELECT table3.publication AS publication, count(table3.publication-table4.birth) AS counts FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
			query+=ngram;
			query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication,genre AS genre,key AS key FROM workspace.work WHERE genre='";
			query+=genre;
			query+="' AND key='";
			query+=key;
			query+="') AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth, country AS country FROM workspace.composer WHERE country='";
			query+= country;
			query+="') AS table4 ON table3.persona = table4.personb GROUP BY publication ORDER BY publication ASC";
		}

		System.out.println(query);

		return query;
	}




	public static native void displayDataTimePublications(String data) /*-{

	var obj = eval(data);
	$wnd.timepublications(obj);	



	}-*/;

}
