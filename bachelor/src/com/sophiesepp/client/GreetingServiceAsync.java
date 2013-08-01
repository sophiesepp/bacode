package com.sophiesepp.client;

import java.util.List;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sophiesepp.shared.AgeDownloadsComposer;
import com.sophiesepp.shared.AgeKey;
import com.sophiesepp.shared.AgePublicationsComposer;
import com.sophiesepp.shared.ComposerGenrePercent;
import com.sophiesepp.shared.ComposerKeyPercent;
import com.sophiesepp.shared.CountryKey;
import com.sophiesepp.shared.CountryDownloads;
import com.sophiesepp.shared.GermanAustrianPercent;
import com.sophiesepp.shared.KeyPercent;
import com.sophiesepp.shared.CountriesKey;
import com.sophiesepp.shared.MostUsedGenres;
import com.sophiesepp.shared.MostUsedNgrams;
import com.sophiesepp.shared.TimeCountry;
import com.sophiesepp.shared.TimeDownloads;
import com.sophiesepp.shared.TimeDownloadsNormalized;
import com.sophiesepp.shared.TimeKey;
import com.sophiesepp.shared.TimeAge;
import com.sophiesepp.shared.NgramCountry;
import com.sophiesepp.shared.TimePublications;
import com.sophiesepp.shared.WorkScorePercent;
import com.sophiesepp.shared.WorkDownloads;


/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void showQueryTimeAge(String input, AsyncCallback<List<TimeAge>> callback)
			throws IllegalArgumentException;
	void showQueryTimeCountry(String input, AsyncCallback<List<TimeCountry>> callback)
			throws IllegalArgumentException;
	void showQueryTimeKey(String input, AsyncCallback<List<TimeKey>> callback)
			throws IllegalArgumentException;
	void showQueryAgeKey(String input, AsyncCallback<List<AgeKey>> callback)
			throws IllegalArgumentException;
	void showQueryCountryKey(String input, AsyncCallback<List<CountryKey>> callback)
			throws IllegalArgumentException;
	void showQueryCountriesKey(String input, AsyncCallback<List<CountriesKey>> callback)
			throws IllegalArgumentException;
	void showQueryWorkScorePercent(String input,AsyncCallback<List<WorkScorePercent>> callback)
			throws IllegalArgumentException;
	void showQueryWorkDownloadsPercent(String input,AsyncCallback<List<WorkDownloads>> callback)
			throws IllegalArgumentException;
	void showQueryComposerGenrePercent(String input,AsyncCallback<List<ComposerGenrePercent>> callback)
			throws IllegalArgumentException;
	void showQueryComposerKeyPercent(String input,AsyncCallback<List<ComposerKeyPercent>> callback)
			throws IllegalArgumentException;
	void showQueryKeyPercent(String input,AsyncCallback<List<KeyPercent>> callback)
			throws IllegalArgumentException;
	void showQueryTimeDownloads(String input,AsyncCallback<List<TimeDownloads>> callback)
			throws IllegalArgumentException;
	void showQueryTimeDownloadsNormalized(String input,AsyncCallback<List<TimeDownloadsNormalized>> callback)
			throws IllegalArgumentException;
	void showQueryAgeDownloadsComposer(String input,AsyncCallback<List<AgeDownloadsComposer>> callback)
			throws IllegalArgumentException;
	void showQueryTimePublications(String input,AsyncCallback<List<TimePublications>> callback)
			throws IllegalArgumentException;
	void showQueryAgePublicationsComposer(String input,AsyncCallback<List<AgePublicationsComposer>> callback)
			throws IllegalArgumentException;
	void showQueryCountryDownloads(String input,AsyncCallback<List<CountryDownloads>> callback)
			throws IllegalArgumentException;
	void showQueryGermanAustrianPercent(String input,AsyncCallback<List<GermanAustrianPercent>> callback)
			throws IllegalArgumentException;
	void showQueryCountry(String input,AsyncCallback<List<NgramCountry>> callback)
			throws IllegalArgumentException;
	void showQueryNgramComposer(String input,AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void showQueryComposerNgram(String input,AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void showQueryBuilderResults(String input,AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void showQueryMostUsedGenres(String buildQueryMostUsedGenres,
			AsyncCallback<List<MostUsedGenres>> callback);
	void showQueryMostUsedNgrams(String buildQueryMostUsedNgrams,
			AsyncCallback<List<MostUsedNgrams>> callback);
}
