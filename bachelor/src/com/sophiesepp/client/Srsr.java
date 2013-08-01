package com.sophiesepp.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sophiesepp.shared.HeatmapObject;
import com.sophiesepp.shared.TimeAge;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Srsr implements EntryPoint {


	
	public static String[] composers = {"K1","K2"};
	public static String[] genres = {"Adagios","Blues","Czardas","Duets","Exercises","Folksongs","Galops","Hymns","Intermezzos","Jazz","Kritis","Landler","Medleys","National anthems","Odes","Polkas","Romances","Scherzos","Tangos","Toccatas","Fugues","Vivaces","Waltzes"};
	public static String[] countries = {"Austria","Belgium","Bulgaria","Cyprus","Czech Republic","Denmark","England","Estonia",
			"Finland","France","Germany", "Greece", "Hungary","Ireland", "Italy", "Latvia", "Lithuania",
			"Luxembourg", "Malta", "Netherlands","Poland","Portugal", "Romania", "Scotland", " Slovakia","Slovenia","Spain", "Sweden" };
	public static String[] keys ={ "C","Db","D","Eb","E","F","Gb","G","Ab","A","Bb","B"};
	
	static MultiWordSuggestOracle composer = new MultiWordSuggestOracle();  
	static MultiWordSuggestOracle genre = new MultiWordSuggestOracle();  
	static MultiWordSuggestOracle key = new MultiWordSuggestOracle();  
	static MultiWordSuggestOracle country = new MultiWordSuggestOracle();  

	final static SuggestBox composerBox = new SuggestBox(composer);
	final Button ComposerQueryButton = new Button("Show");
	final Label composerLabel = new Label("Select composer");
	



	public void onModuleLoad() {
		
		
		  	 
		initialize();
		
		
		//SuggestBoxes
		int i;
		for(i=0;i< composers.length;i++){
			composer.add(composers[i]);
		}

		for(i=0;i< genres.length;i++){
			genre.add(genres[i]);
		}
		
		for(i=0;i< keys.length;i++){
			key.add(keys[i]);
		}
		
		for(i=0;i< countries.length;i++){
			country.add(countries[i]);
		}
		

		composerBox.addStyleDependentName("composerBox");
		composerLabel.addStyleName("text3");
		ComposerQueryButton.addStyleDependentName("composerbutton");
		
		
		ComposerQueryButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				AgeDownloadsComposerQuery.queryAgeDownloadsComposer();
				AgePublicationsComposerQuery.queryAgePublicationsComposer();
				ComposerGenrePercentQuery.queryComposerGenrePercent();
				ComposerKeyPercentQuery.queryComposerKeyPercent();
				ComposerNgramQuery.queryComposerNgram();
			}
		});
		
	
	
		



		//Composing Container

		RootPanel.get("composingContainer").add(RootPanel.get("descriptionComposingContainer"));
		RootPanel.get("composingContainer").add(TimeDownloadsQuery.timedownloadsPanel);
		RootPanel.get("composingContainer").add(TimeAgeQuery.timeagePanel);
		RootPanel.get("composingContainer").add(TimeCountryQuery.timecountryPanel);
		RootPanel.get("composingContainer").add(TimePublicationsQuery.timepublicationsPanel);
		
		
		


		// Use of keys Container

		RootPanel.get("useofKeysContainer").add(KeyPercentQuery.keypercentPanel);
		RootPanel.get("useofKeysContainer").add(CountriesKeyQuery.countrieskeyPanel);
		RootPanel.get("useofKeysContainer").add(TimeKeyQuery.timekeyPanel);
		RootPanel.get("useofKeysContainer").add(AgeKeyQuery.agekeyPanel);
	
		
	

		//Percentages and numbers Container


		RootPanel.get("percentagesandnumbersContainer").add(WorkScorePercentQuery.worksPanel);
		RootPanel.get("percentagesandnumbersContainer").add(CountryDownloadsQuery.countrydownloadsPanel);
		RootPanel.get("percentagesandnumbersContainer").add(MostUsedGenresQuery.mostusedgenresPanel);
		RootPanel.get("percentagesandnumbersContainer").add(MostUsedNgramsQuery.mostusedngramsPanel);
		RootPanel.get("percentagesandnumbersContainer").add(NgramComposerQuery.ngramcomposerPanel);




		//Questions about composers Container

		RootPanel.get("questionsaboutcomposersContainer").add(composerLabel);
		RootPanel.get("questionsaboutcomposersContainer").add(composerBox);
		RootPanel.get("questionsaboutcomposersContainer").add(ComposerQueryButton);
		VerticalPanel seperationPanel = new VerticalPanel();
		seperationPanel.addStyleName("seperationPanelComposer");
		RootPanel.get("questionsaboutcomposersContainer").add(seperationPanel);
		RootPanel.get("questionsaboutcomposersContainer").add(AgeDownloadsComposerQuery.agedownloadscomposerPanel);
		RootPanel.get("questionsaboutcomposersContainer").add(AgePublicationsComposerQuery.agepublicationscomposerPanel);
		RootPanel.get("questionsaboutcomposersContainer").add(ComposerGenrePercentQuery.composergenrekeyPanel);
		RootPanel.get("questionsaboutcomposersContainer").add(ComposerNgramQuery.composerngramPanel);
		
		RootPanel.get("queryBuilderContainer").add(QueryBuilder.queryBuilderPanel);
	
	
		
	
	}



	private void initialize(){

		
		TimeAgeQuery.queryTimeAge();
		TimeCountryQuery.queryTimeCountry();
		TimePublicationsQuery.queryTimePublications();
		TimeDownloadsQuery.queryTimeDownloads();
		TimeDownloadsQuery.queryTimeDownloadsNormalized();
		TimeKeyQuery.queryTimeKey();
		AgeKeyQuery.queryAgeKey();
		CountriesKeyQuery.queryCountriesKey();
		WorkScorePercentQuery.queryWorkScorePercent();
		WorkDownloadsPercentQuery.queryWorkDownloadsPercent();
		KeyPercentQuery.queryKeyPercent();
		CountryDownloadsQuery.queryCountryDownloads();
		MostUsedGenresQuery.queryMostUsedGenres();
		MostUsedNgramsQuery.queryMostUsedNgrams();
		
	

	}





}
