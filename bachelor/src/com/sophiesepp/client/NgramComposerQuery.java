package com.sophiesepp.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class NgramComposerQuery extends TextResults implements EntryPoint{

	

	static VerticalPanel ngramcomposerPanel = new VerticalPanel();
	static HorizontalPanel ngramcomposercontentPanel = new HorizontalPanel();
	VerticalPanel leftngramcomposerPanel = new VerticalPanel();
	VerticalPanel rightngramcomposerPanel = new VerticalPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	HorizontalPanel ngramcomposerPanel1= new HorizontalPanel(); 
	VerticalPanel  ngramcomposerPanel2 = new VerticalPanel();
	VerticalPanel ngramcomposerLabel1 = new VerticalPanel();
	VerticalPanel ngramcomposerLabel2 = new VerticalPanel();
	
	final static TextArea dataNgramComposer = new TextArea();
	final static Label ngramLabel = new Label("Ngram");
	final static TextBox ngramBoxComposer = new TextBox();
	
	final Button showNgramComposerQueryButton = new Button("Run Query");
	
	public void onModuleLoad() {	
		
		ngramLabel.addStyleName("text3");
		ngramBoxComposer.addStyleName("ngramBox");
		
		ngramcomposerLabel1.addStyleName("label");
		ngramcomposerLabel2.addStyleName("buttonlabel");

		ngramcomposerPanel.addStyleName("ngramcomposerPanel");
		ngramcomposercontentPanel.addStyleName("ngramcomposercontentPanel");
		leftngramcomposerPanel.addStyleName("leftngramcomposerPanel");
		rightngramcomposerPanel.addStyleName("rightngramcomposerPanel");
		ngramcomposerPanel1.addStyleName("ngramcomposerPanel1");
		ngramcomposerPanel2.addStyleName("ngramcomposerPanel2");
		
		
		showNgramComposerQueryButton.addStyleName("NgramComposerQueryButton");
		

		showNgramComposerQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				NgramComposerQuery.queryNgramComposer();
			}
		});
		
		dataNgramComposer.setWidth("600px");
		dataNgramComposer.setVisibleLines(20);
		dataNgramComposer.setEnabled(false);
		
	
		
		ngramcomposerPanel.add(RootPanel.get("heading14"));
		ngramcomposerPanel.add(ngramcomposercontentPanel);
		
		ngramcomposercontentPanel.add(leftngramcomposerPanel);
		ngramcomposercontentPanel.add(rightngramcomposerPanel);
	
		leftngramcomposerPanel.add(ngramcomposerPanel1);
		leftngramcomposerPanel.add(ngramcomposerPanel2);
		
		ngramcomposerLabel1.add(ngramLabel);
		ngramcomposerLabel1.add(ngramBoxComposer);
		ngramcomposerLabel2.add(showNgramComposerQueryButton);

		ngramcomposerPanel1.add(ngramcomposerLabel1);
		ngramcomposerPanel1.add(ngramcomposerLabel2);
		ngramcomposerPanel2.add(dataNgramComposer);
		
		scrollPanel.setSize("225px","550px");
		rightngramcomposerPanel.add(scrollPanel);
		scrollPanel.add(RootPanel.get("rightngramcomposerPanel"));


	}
	
	public static void queryNgramComposer() {

		greetingService.showQueryNgramComposer(buildQueryNgramComposer(),
				new AsyncCallback<String>(){
			public void onFailure(Throwable caught) {


			}

			public void onSuccess(String result) {

				dataNgramComposer.getElement().setInnerText(result);
			}
		});
	}

	


	public static String buildQueryNgramComposer(){

		String ngramComposer = ngramBoxComposer.getText();

		 String query = "SELECT table3.ngram AS ngram, count(table3.ngram) AS counts FROM (SELECT ngram AS ngram, scoreId AS scoreId FROM workspace.ngram) AS table3 JOIN(SELECT scoreId FROM(SELECT works.workId AS workId FROM (SELECT personId AS personId FROM workspace.composer WHERE personId='";
		 query += ngramComposer;
		 query +="') AS composers JOIN EACH(SELECT workId AS workId,personId AS personId FROM workspace.work) AS works ON composers.personId=works.personId) AS table1 JOIN (SELECT scoreId AS scoreId, workId AS workId FROM workspace.score) AS table2 ON table1.workId=table2.workId) AS table4 ON table3.scoreId=table4.scoreId GROUP BY ngram DESC LIMIT 10";
		System.out.println(query);

		return query;
	}

}
