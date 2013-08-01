package com.sophiesepp.client;



import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	List<TimeAge> showQueryTimeAge(String input) throws IllegalArgumentException, Exception;
	List<TimeCountry> showQueryTimeCountry(String input) throws IllegalArgumentException, Exception;
	List<TimeKey> showQueryTimeKey(String input) throws IllegalArgumentException, Exception;
	List<AgeKey> showQueryAgeKey(String input) throws IllegalArgumentException, Exception;
	List<CountryKey> showQueryCountryKey(String input) throws IllegalArgumentException, Exception;
	List<CountriesKey> showQueryCountriesKey(String input) throws IllegalArgumentException, Exception;
	List<WorkScorePercent> showQueryWorkScorePercent(String input) throws IllegalArgumentException, Exception;
	List<WorkDownloads> showQueryWorkDownloadsPercent(String input) throws IllegalArgumentException, Exception;
	List<ComposerGenrePercent> showQueryComposerGenrePercent(String input) throws IllegalArgumentException, Exception;
	List<ComposerKeyPercent> showQueryComposerKeyPercent(String input) throws IllegalArgumentException, Exception;
	List<KeyPercent> showQueryKeyPercent(String input) throws IllegalArgumentException, Exception;
	List<TimeDownloads> showQueryTimeDownloads(String input) throws IllegalArgumentException, Exception;
	List<TimeDownloadsNormalized> showQueryTimeDownloadsNormalized(String input) throws IllegalArgumentException, Exception;
	List<AgeDownloadsComposer> showQueryAgeDownloadsComposer(String input) throws IllegalArgumentException, Exception;
	List<TimePublications> showQueryTimePublications(String input) throws IllegalArgumentException, Exception;
	List<AgePublicationsComposer> showQueryAgePublicationsComposer(String input) throws IllegalArgumentException, Exception;
	List<MostUsedGenres> showQueryMostUsedGenres(String input) throws IllegalArgumentException, Exception;
	List<MostUsedNgrams> showQueryMostUsedNgrams(String input) throws IllegalArgumentException, Exception;
	List<CountryDownloads> showQueryCountryDownloads(String input) throws IllegalArgumentException, Exception;
	List<GermanAustrianPercent> showQueryGermanAustrianPercent(String input) throws IllegalArgumentException, Exception;
	List<NgramCountry> showQueryCountry(String input) throws IllegalArgumentException, Exception;
	String showQueryNgramComposer(String input) throws IllegalArgumentException, Exception;
	String showQueryComposerNgram(String input) throws IllegalArgumentException, Exception;
	String showQueryBuilderResults(String input) throws IllegalArgumentException, Exception;
}
