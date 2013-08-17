package com.sophiesepp.server;

import com.sophiesepp.client.GreetingService;
import com.sophiesepp.shared.AgeDownloadsComposer;
import com.sophiesepp.shared.AgeKey;
import com.sophiesepp.shared.AgePublicationsComposer;
import com.sophiesepp.shared.ComposerGenrePercent;
import com.sophiesepp.shared.ComposerKeyPercent;
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
import com.sophiesepp.shared.Top10genres;
import com.sophiesepp.shared.Top10ngrams;
import com.sophiesepp.shared.WorkScorePercent;
import com.sophiesepp.shared.WorkDownloads;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonWriter;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonGenerator;
import com.google.api.client.json.JsonParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.bigquery.Bigquery;
import com.google.api.services.bigquery.Bigquery.Jobs.Get;
import com.google.api.services.bigquery.Bigquery.Jobs.Insert;
import com.google.api.services.bigquery.model.GetQueryResultsResponse;
import com.google.api.services.bigquery.model.Job;
import com.google.api.services.bigquery.model.JobConfiguration;
import com.google.api.services.bigquery.model.JobConfigurationQuery;
import com.google.api.services.bigquery.model.JobReference;
import com.google.api.services.bigquery.model.Table;
import com.google.api.services.bigquery.model.TableCell;
import com.google.api.services.bigquery.model.TableDataList;
import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableRow;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.appengine.api.datastore.Entity;
import com.google.common.hash.Hashing;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.ShortBlob;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;
import java.security.GeneralSecurityException;

import org.mortbay.util.ajax.JSON;





/**
 * The server side implementation of the RPC service.
 */

@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
GreetingService {





	private static final String PROJECT_ID = "681130105244";
	private static final String DATASET_ID = "workspace";


	private static final String RESOURCE_LOCATION ="";
	private static final String BIGQUERY_SCOPE = "https://www.googleapis.com/auth/bigquery";
	private static final HttpTransport transport = new NetHttpTransport();	
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	public static GoogleCredential credential = null;
	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();





	static String queryGermanAustrianPercent = "SELECT publication AS publication, count(downloads) AS counts FROM(SELECT table2.publication AS publication, table2.downloads AS downloads,table1.country AS country FROM (SELECT table1.country AS country, table1.personId AS personId) FROM workspace.composer AS table1 JOIN (SELECT personId AS personId, downloads AS downloads, publication AS publication FROM workspace.work) AS table2 ON table1.personId=table2.personId WHERE table2.publication='1900')GROUP BY publication,downloads,country ORDER BY downloads DESC LIMIT 100) WHERE country='Austria' OR country='GERMANY' GROUP BY publication";
	static String queryTimeAge = "SELECT publication AS publication,(publication-birth) AS age, count(publication-birth) AS counts FROM workspace.composer AS composers JOIN (SELECT publication,personId FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY publication,age";
	static String queryTimeDownloads = "SELECT publication AS publication,sum(downloads) AS downloads FROM workspace.work GROUP BY publication ORDER BY publication ASC";
	static String queryTimePublications = "SELECT publication AS publication,count(publication) AS counts FROM workspace.work GROUP BY publication ORDER BY publication ASC";
	static String queryAgeDownloadsComposer = "SELECT table2.publication-table1.birth AS age, INTEGER(ROUND(sum(downloads)/count(downloads))) AS downloads FROM workspace.composer AS table1 JOIN (SELECT publication AS publication, downloads AS downloads, personId AS personId FROM workspace.work) AS table2 ON table1.personId = table2.personId GROUP BY age";
	static String queryAgeKeyComposer = "SELECT table2.publication-table1.birth AS age, key AS key, count(key) AS counts FROM workspace.composer AS table1 JOIN (SELECT publication AS publication, key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId = table2.personId GROUP BY age, key";
	//static String queryAgeGenre = "SELECT table2.publication-table1.birth AS age, genre AS genre, count(genre) AS counts FROM workspace.composer AS table1 JOIN (SELECT publication AS publication, genre AS genre, personId AS personId FROM workspace.work) AS table2 ON table1.personId = table2.personId WHERE table1.personId='K4' GROUP BY age, key";
	static String queryCountryDownloads = "SELECT country AS country, sum(downloads) AS percent FROM (SELECT country AS country, downloads AS downloads FROM workspace.composer AS composers JOIN (SELECT personId,downloads FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY downloads,country) GROUP BY country";
	//static String query1 = "SELECT table3.publication AS publication, (table3.publication-table4.birth) AS age,count(table3.publication-table4.birth) AS hits FROM(SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='1 0 0 0') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, birth AS birth FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY publication,age";
	static String queryCountry = "SELECT table4.country AS country, count(table4.country) AS hits FROM (SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON ngrams.scoreId = scores.scoreId WHERE ngrams.ngram = '1 0 0 0') AS table1 JOIN (SELECT workId AS workb, personId AS personId,publication AS publication FROM workspace.work) AS table2 ON table1.worka = table2.workb WHERE table2.publication<1900) AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY country";
	static String queryTimeKey = "SELECT publication AS publication,key AS key,count(key)AS counts FROM workspace.work GROUP BY publication,key";
	static String queryAgeKey = "SELECT table2.publication-table1.birth AS age, table2.key AS key,count(table2.key) FROM workspace.composer AS table1 JOIN(SELECT publication AS publication,key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId=table2.personId GROUP BY age,key";
	static String queryCountryKey = "SELECT table1.country AS country, table2.key AS key,count(table2.key) FROM workspace.composer AS table1 JOIN(SELECT key AS key, personId AS personId FROM workspace.work) AS table2 ON table1.personId=table2.personId GROUP BY country,key";
	static String queryWorkScorePercent = "SELECT counts AS scores, count(workId) AS works FROM (SELECT table1.workId AS workId,count(table2.scoreId) AS counts FROM workspace.work AS table1 JOIN (SELECT scoreId AS scoreId, workId AS workId FROM workspace.score) AS table2 ON table1.workId=table2.workId GROUP BY workId) GROUP BY scores";
	static String queryWorkDownloadsPercent = "SELECT work AS work, downloads AS downloads FROM workspace.work";
	static String queryComposerGenrePercent = "SELECT genre AS genre, count(workId) AS workId FROM (SELECT table2.workId AS workId, table2.genre AS genre FROM workspace.composer AS table1 JOIN (SELECT personId AS personId, genre AS genre, workId AS workId FROM workspace.work) AS table2 ON table1.personId=table2.personId WHERE table1.personId='K4')GROUP BY genre ORDER BY workId DESC";
	static String queryComposerKeyPercent = "SELECT key AS key, count(workId) AS workId FROM (SELECT table2.workId AS workId, table2.key AS key FROM workspace.composer AS table1 JOIN (SELECT personId AS personId, key AS key, workId AS workId FROM workspace.work) AS table2 ON table1.personId=table2.personId WHERE table1.personId='K4')GROUP BY key ORDER BY workId DESC";
	static String queryKeyPercent = "SELECT key AS key, count(workId) AS workId FROM (SELECT table2.workId AS workId, table2.key AS key FROM workspace.composer AS table1 JOIN (SELECT personId AS personId, key AS key, workId AS workId FROM workspace.work) AS table2 ON table1.personId=table2.personId)GROUP BY key ORDER BY workId DESC";
	static String queryNgramComposer = "SELECT table4.personb AS composer, count(table4.personb) AS hits FROM (SELECT table2.personId AS persona,table2.publication AS publication FROM(SELECT scores.workId AS worka FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON ngrams.scoreId = scores.scoreId WHERE ngrams.ngram = '1 0 0 0') AS table1 JOIN (SELECT workId AS workb, personId AS personId,publication AS publication FROM workspace.work) AS table2 ON table1.worka = table2.workb) AS table3 JOIN (SELECT personId AS personb, country AS country FROM workspace.composer) AS table4 ON table3.persona = table4.personb GROUP BY composer";
	static String queryComposerNgram = "SELECT table4.ngram AS ngram, count(ngrams) AS counts FROM (SELECT scoreId AS scoreId FROM(SELECT workId AS workId FROM workspace.composer AS composers JOIN workspace.work AS works ON composers.personId = works.personId WHERE composers.composer ='K4') AS table1 JOIN(SELECT workId AS workId, scoreId AS scoreId FROM workspace.score) AS table2 ON table1.workId = table2.workId) AS table3 JOIN(SELECT scoreId AS scoreId, ngram AS ngram FROM workspace.ngram) AS table4 ON table3.scoreId = table4.scoreId GROUP BY ngram ORDER BY counts DESC LIMIT 10";

	static String queryMosedUsedGenre = "SELECT genre AS genre, count(genre) AS counts FROM workspace.work GROUP BY genre ORDER BY counts DESC LIMIT 10";
	static String queryMosedUsedNgrams = "SELECT ngram AS ngram, count(ngram) AS counts FROM workspace.ngram GROUP BY ngram ORDER BY counts DESC LIMIT 10";
	static String queryCountry1 = "SELECT publication AS publication,country AS country,count(country)AS counts FROM (SELECT personId AS personId,country AS country FROM workspace.composer WHERE country='";
	static String queryCountry2 = "') AS composers JOIN (SELECT publication,personId FROM workspace.work) AS works ON composers.personId=works.personId GROUP BY publication,country";

	public static String[] countries = {"Austria","Belgium","Bulgaria","Cyprus","Czech Republic","Denmark","England","Estonia",
		"Finland","France","Germany", "Greece", "Hungary","Ireland", "Italy", "Latvia", "Lithuania",
		"Luxembourg", "Malta", "Netherlands","Poland","Portugal", "Romania", "Scotland", " Slovakia","Slovenia","Spain", "Sweden" };

	DatastoreService service = DatastoreServiceFactory.getDatastoreService();
	int destinationtype;
	String querytimenormalization = "SELECT publication, count(publication) FROM workspace.work GROUP BY publication";
	String querycountrynormalization = "SELECT composers.country, count(works.publication) FROM (SELECT country AS country, personId AS personId FROM workspace.composer) AS composers JOIN workspace.work AS works ON composers.personId=works.personId GROUP BY composers.country"; 
	String queryagenormalization ="SELECT (works.publication-composers.birth) AS age, count(works.publication-composers.birth) AS counts FROM (SELECT birth AS birth, personId AS personId FROM workspace.composer) AS composers JOIN workspace.work AS works ON composers.personId=works.personId GROUP BY age"; 
	String timenormalizationtable = "timenormalization";
	String countrynormalizationtable = "countrynormalization";
	String agenormalizationtable = "agenormalization";




	@SuppressWarnings("deprecation")
	public static Bigquery createAuthorizedClient() {



		try {
			credential = new GoogleCredential.Builder().setTransport(transport)
					.setJsonFactory(JSON_FACTORY)
					.setServiceAccountId("681130105244-c2c2klegpa08c02nv4litb9tkrp5lm4v@developer.gserviceaccount.com")
					.setServiceAccountScopes(BIGQUERY_SCOPE)
					.setServiceAccountPrivateKeyFromP12File(new File(RESOURCE_LOCATION))
					.build();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Bigquery(transport, JSON_FACTORY, credential);
	}







	@SuppressWarnings("unchecked")
	public synchronized List<TableRow> getQueryResult(String input, String kind, int querytype, String normalizationTable, int destinationtype) throws IllegalArgumentException {

		Bigquery bigquery;
		JobReference jobId = null;
		Job completedJob = null;
		String queryNormalize = "SELECT table1.value1, table1.value2 , table1.value3/table2.value2 FROM workspace."+kind+" AS table1 JOIN workspace."+normalizationTable+" AS table2 ON table1.value1=table2.value1";
		String kind2 = Hashing.md5().hashString(queryNormalize).toString();
		bigquery = createAuthorizedClient();	
		Key entityKey = null;
		Entity requestResultEntity;
		List<TableRow> rows = new ArrayList<TableRow>();
		Boolean contains = null;
		if(destinationtype!=0){
			kind = normalizationTable;
		}

		if(querytype==0){
			contains = memcache.contains(kind);
		}
		if(querytype==1){
			contains = memcache.contains(kind2);
		}

		if(contains==true){

			if(querytype==1){
				kind= Hashing.md5().hashString(queryNormalize).toString();
			}
			System.out.println("MEMCACHE TRUE");
			System.out.println(kind);
			String m = memcache.get(kind).toString();
			final Gson gson = new Gson();
			GenericJson o = gson.fromJson(m,GenericJson.class);


			for(int i=0; i<o.size();i++){

				String index = o.get(String.valueOf(i)).toString();
				GenericJson values = gson.fromJson(index,GenericJson.class);

				String value1 = values.get("value1").toString();
				String value2 = values.get("value2").toString();

				System.out.println(value1);
				System.out.println(value2);



				TableRow row = new TableRow();
				List<TableCell> cells = new ArrayList<TableCell>();	
				TableCell cell1 = new TableCell();		
				cell1.set("v", value1);	
				TableCell cell2 = new TableCell();		
				cell2.set("v", value2);

				row.setF(cells);
				row.getF().add(0,cell1);
				row.getF().add(1,cell2);


				TableCell cell3 = new TableCell();		
				try {
					cell3.set("v", values.get("value3").toString());
				} catch (Exception e) {
					System.out.println("no value3");
				}
				
				row.getF().add(2,cell3);
				rows.add(row);

				i++;

			}
			System.out.println(rows);

		}
		else{
			System.out.println("MEMCACHE FALSE");
			if(querytype==0){
				entityKey = KeyFactory.createKey("requestResult", kind);
				System.out.println("DESTINATION:  "+destinationtype+"   input: "+input);
			}
			if(querytype==1){
				entityKey = KeyFactory.createKey("requestResult", queryNormalize);
			}


			Job job = null;

			//service.delete(EntityKey);

			try {
				requestResultEntity = service.get(entityKey);
			} catch (EntityNotFoundException e) {
				requestResultEntity = null;
			}




			if(requestResultEntity==null){

				System.out.println("new job");
				job = makeJob(kind,input,querytype,destinationtype);

				try {

					jobId = startQuery(bigquery, PROJECT_ID,job);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Poll for Query Results, return result output

				try {
					completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}



				GetQueryResultsResponse queryResult = null;
				try {
					queryResult = bigquery
							.jobs()
							.getQueryResults(PROJECT_ID,
									completedJob.getJobReference().getJobId()).execute();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				rows = queryResult.getRows();






				if(querytype==1){

					/** try {
							bigquery.tables().delete(PROJECT_ID,
										DATASET_ID, kind).execute();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/


					job = makeJob(kind2,queryNormalize,0,0);

					try {

						jobId = startQuery(bigquery, PROJECT_ID,job);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Poll for Query Results, return result output

					try {
						completedJob = checkQueryResults(bigquery, PROJECT_ID, jobId);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}



					GetQueryResultsResponse queryResult2 = null;
					try {
						queryResult2 = bigquery
								.jobs()
								.getQueryResults(PROJECT_ID,
										completedJob.getJobReference().getJobId()).execute();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}



					rows = queryResult2.getRows();

					System.out.println(rows);




				}


				JobReference l = completedJob.getJobReference();

				String completedJobId = l.getJobId();	

				String result = queryResult.toString();

				Text text = new Text(result);
				Text text2 = new Text(input);	

				Entity entity = new Entity(entityKey);

				entity.setProperty("result", text);
				entity.setProperty("query", text2);
				entity.setProperty("jobId", completedJobId);

				System.out.println(entity);

				service.put(entity);



			}

			else{	

				System.out.println("old job **");



				TableDataList tableDataList = null;
				try {
					if(querytype==0){
						tableDataList = bigquery.tabledata().list(PROJECT_ID,
								DATASET_ID, kind).execute();
					}
					if(querytype==1){
						tableDataList = bigquery.tabledata().list(PROJECT_ID,
								DATASET_ID, kind2).execute();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				rows = tableDataList.getRows();

				System.out.println("OLD JOB ROWS: "+rows);

			}




			int count = rows.get(0).getF().size();
			int i = 0;
			

			GenericJson json1 = new GenericJson();

			if(count==3){
				for (TableRow row : rows){
					System.out.println("ROW:   "+row.getF().get(2).getV());
					String number = String.valueOf(i);
					GenericJson json = new GenericJson();
					json.put("value1",String.valueOf(row.getF().get(0).getV()));
					json.put("value2",String.valueOf(row.getF().get(1).getV()));
					json.put("value3",Double.parseDouble(String.valueOf(row.getF().get(2).getV())));

					json1.put(number,json);
					i++;

				}					
			}

			if(count==2){
				for (TableRow row : rows){

					String number = String.valueOf(i);
					GenericJson json = new GenericJson();
					json.put("value1",String.valueOf(row.getF().get(0).getV()));
					json.put("value2",String.valueOf(row.getF().get(1).getV()));

					json1.put(number,json);
					i++;

				}					
			}



			String jsonstring = null;
			try {
				jsonstring = json1.toPrettyString();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			memcache.put(kind,jsonstring);	
			Object object = memcache.get(kind);
			System.out.println("MEMCACHE:"+object);


		}			


		return rows;
	}








	private Job makeJob(String kind,String query, int querytype, int destinationtype) {

		JobConfigurationQuery jobconfigurationquery = new JobConfigurationQuery();

		Table t = new Table();
		List<TableFieldSchema> fields = new ArrayList<TableFieldSchema>();



		TableReference ta = new TableReference();
		t.setTableReference(ta);


		if(destinationtype==0){
			try{
				ta.setProjectId(PROJECT_ID);
				ta.setDatasetId(DATASET_ID);
				ta.setTableId(kind);
				System.out.println("Destinationtype 0 set");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		else if(destinationtype==1){
			ta.setProjectId(PROJECT_ID);
			ta.setDatasetId(DATASET_ID);
			ta.setTableId("timenormalization");

			TableFieldSchema value1 = new TableFieldSchema();
			value1.setName("value1");
			value1.setType("integer");
			TableFieldSchema value2 = new TableFieldSchema();
			value1.setName("value2");
			value1.setType("integer");
			fields.add(value1);
			fields.add(value2);
			TableSchema schema = new TableSchema();
			schema.setFields(fields);
			t.setSchema(schema);

			System.out.println("Destinationtype 1 set");

		}
		else if(destinationtype==2){
			ta.setProjectId(PROJECT_ID);
			ta.setDatasetId(DATASET_ID);
			ta.setTableId("countrynormalization");
			System.out.println("Destinationtype 2 set");

			TableFieldSchema value1 = new TableFieldSchema();
			value1.setName("value1");
			value1.setType("string");
			TableFieldSchema value2 = new TableFieldSchema();
			value1.setName("value2");
			value1.setType("integer");
			fields.add(value1);
			fields.add(value2);
			TableSchema schema = new TableSchema();
			schema.setFields(fields);
			t.setSchema(schema);

		}
		else if(destinationtype==3){
			ta.setProjectId(PROJECT_ID);
			ta.setDatasetId(DATASET_ID);
			ta.setTableId("agenormalization");
			System.out.println("Destinationtype 3 set");

			TableFieldSchema value1 = new TableFieldSchema();
			value1.setName("value1");
			value1.setType("integer");
			TableFieldSchema value2 = new TableFieldSchema();
			value1.setName("value2");
			value1.setType("integer");
			fields.add(value1);
			fields.add(value2);
			TableSchema schema = new TableSchema();
			schema.setFields(fields);
			t.setSchema(schema);

		}


		try{
			jobconfigurationquery.setDestinationTable(ta);
			jobconfigurationquery.setWriteDisposition("WRITE_TRUNCATE");

		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};


		System.out.println("table "+query+" erstellt");
		jobconfigurationquery.setQuery(query);




		JobConfiguration jobconfiguration = new JobConfiguration();
		jobconfiguration.setQuery(jobconfigurationquery);


		JobReference jobreference = new JobReference();
		jobreference.setProjectId(PROJECT_ID);

		Job newJob = new Job();
		newJob.setConfiguration(jobconfiguration);
		newJob.setJobReference(jobreference);


		return newJob;
	}




	public static JobReference startQuery(Bigquery bigquery, String projectId,Job job) throws IOException {


		Insert insert = bigquery.jobs().insert(projectId, job);
		insert.setProjectId(projectId);
		JobReference jobId = insert.execute().getJobReference();


		System.out.println("\nJob ID of Query Job is: %s\n"+ jobId.getJobId());

		return jobId;
	}




	/**
	 * Polls the status of a BigQuery job, returns Job reference if "Done"
	 *
	 * @param bigquery an authorized BigQuery client
	 * @param projectId a string containing the current project ID
	 * @param jobId a reference to an inserted query Job
	 * @return a reference to the completed Job
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static Job checkQueryResults(Bigquery bigquery, String projectId, JobReference jobId)
			throws IOException, InterruptedException {
		// Variables to keep track of total query time
		long startTime = System.currentTimeMillis();
		long elapsedTime;

		while (true) {
			Job pollJob = bigquery.jobs().get(projectId, jobId.getJobId()).execute();
			elapsedTime = System.currentTimeMillis() - startTime;
			System.out.format("Job status (%dms) %s: %s\n", elapsedTime,
					jobId.getJobId(), pollJob.getStatus().getState());
			if (pollJob.getStatus().getState().equals("DONE")) {
				return pollJob;
			}
			// Pause execution for one second before polling job status again, to
			// reduce unnecessary calls to the BigQUery API and lower overall
			// application bandwidth.
			Thread.sleep(1000);
		}

	}

	//  only been executed once to store the sum of publications for each year which is needed for normalization
	public void queryPublicationsPerYear(){

		System.out.println("Started normalizing for years");
		String queryobject = "year";
		String query = "SELECT (publication-0) AS value1, count(publication) AS value2 FROM workspace.work GROUP BY value1"; 
		String kind = Hashing.md5().hashString(query).toString();
		getQueryResult(query,kind,0,timenormalizationtable,1);

	}



	//  only been executed once to store the sum of publications for each country which is needed for normalization
	public void queryPublicationsPerCountry(){

		System.out.println("Started normalizing for countries");
		String queryobject = "country";
		String query = "SELECT composers.country AS value1, count(works.publication) AS value2 FROM (SELECT country AS country, personId AS personId FROM workspace.composer) AS composers JOIN workspace.work AS works ON composers.personId=works.personId GROUP BY value1";
		String kind = Hashing.md5().hashString(query).toString();
		getQueryResult(query,kind,0,countrynormalizationtable,2);

	}

	//  only been executed once to store the sum of publications for each age which is needed for normalization
	public void queryPublicationsPerAge(){


		System.out.println("Started normalizing for ages");
		String queryobject = "age";
		String query = "SELECT (works.publication-composers.birth) AS value1, count(works.publication-composers.birth) AS value2 FROM (SELECT birth AS birth, personId AS personId FROM workspace.composer) AS composers JOIN workspace.work AS works ON composers.personId=works.personId GROUP BY value1"; 
		String kind = Hashing.md5().hashString(query).toString();
		getQueryResult(query,kind,0,agenormalizationtable,3);
	}


	//Methods turning results into JavaScript Objects and sending results to client

	public List<TimeAge> showQueryTimeAge(String input) throws IllegalArgumentException, IOException {


		queryPublicationsPerYear();

		// querytype 1 means normalization is needed
		int querytype = 1;

		String kind = Hashing.md5().hashString(input).toString();

		List<TableRow> rows = getQueryResult(input,kind,querytype,timenormalizationtable,0);
		System.out.println(rows);
		List<TimeAge> results = new ArrayList<TimeAge>();



		for (TableRow row : rows)
		{ 
			TimeAge timeage = new TimeAge(Integer.parseInt(String.valueOf(row.getF().get(0).getV())), Integer.parseInt(String.valueOf(row.getF().get(1).getV())), Double.parseDouble(String.valueOf(row.getF().get(2).getV())));

			results.add(timeage);

		}


		System.out.println(results);

		return results;
	}

	public List<TimeCountry> showQueryTimeCountry(String input) throws IllegalArgumentException, IOException {

		queryPublicationsPerYear();
		int querytype = 1;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,timenormalizationtable,0);
		List<TimeCountry> results = new ArrayList<TimeCountry>();

		for (TableRow row : rows)
		{ 


			TimeCountry timecountry = new TimeCountry(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),String.valueOf(row.getF().get(1).getV()),Double.parseDouble(String.valueOf(row.getF().get(2).getV())));
			results.add(timecountry);


		}

		System.out.println(results);
		return results;


	}



	public List<CountriesKey> showQueryCountriesKey(String input) throws IllegalArgumentException, IOException {


		queryPublicationsPerCountry();
		int querytype = 1;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,countrynormalizationtable,0);

		List<CountriesKey> results = new ArrayList<CountriesKey>();

		for (TableRow row : rows)
		{ 


			CountriesKey countrieskey = new CountriesKey(String.valueOf(row.getF().get(0).getV()),String.valueOf(row.getF().get(1).getV()),Double.parseDouble(String.valueOf(row.getF().get(2).getV())));
			results.add(countrieskey);


		}


		return results;

	}

	public List<TimeKey> showQueryTimeKey(String input) throws IllegalArgumentException, IOException {


		queryPublicationsPerYear();
		int querytype = 1;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,timenormalizationtable,0);

		List<TimeKey> results = new ArrayList<TimeKey>();

		for (TableRow row : rows)
		{ 

			TimeKey timekey = new TimeKey(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),String.valueOf(row.getF().get(1).getV()),Double.parseDouble(String.valueOf(row.getF().get(2).getV())));
			results.add(timekey);

		}


		return results;


	}




	public List<AgeKey> showQueryAgeKey(String input) throws IllegalArgumentException, IOException {	

		queryPublicationsPerAge();
		int querytype = 1;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,agenormalizationtable,0);
		List<AgeKey> results = new ArrayList<AgeKey>();

		for (TableRow row : rows)
		{ 

			AgeKey agekey = new AgeKey(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),String.valueOf(row.getF().get(1).getV()),Double.parseDouble(String.valueOf(row.getF().get(2).getV())));
			results.add(agekey);

		}


		return results;


	}


	public List<MostUsedGenres> showQueryMostUsedGenres(String input) throws IllegalArgumentException, IOException {


		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();

		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);


		ArrayList<Top10genres> results = new ArrayList<Top10genres>();
		for (TableRow row : rows) 
		{  Top10genres top10genre = new Top10genres(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(top10genre);
		}

		System.out.println(results);
		String genre1 = results.get(0).genre;
		String genre2 = results.get(1).genre;
		String genre3 = results.get(2).genre;
		String genre4 = results.get(3).genre;
		String genre5 = results.get(4).genre;
		String genre6 = results.get(5).genre;
		String genre7 = results.get(6).genre;
		String genre8 = results.get(7).genre;
		String genre9 = results.get(8).genre;
		String genre10 = results.get(9).genre;

		String query = "SELECT publication AS publication, genre AS genre, count(genre) AS counts FROM workspace.work WHERE genre='";
		query+= genre1+"' OR genre='"+genre2+"' OR genre='"+genre3+"' OR genre='"+genre4+"' OR genre='"+genre5+"' OR genre='"+genre6+"' OR genre='"+genre7+"' OR genre='"+genre8+"' OR genre='"+genre9+"' OR genre='"+genre10;
		query+="' GROUP BY publication, genre ORDER BY publication ASC";

		System.out.println(query);	
		String kind2 = Hashing.md5().hashString(query).toString();
		List<TableRow> rows2 = getQueryResult(query,kind2,querytype,"",destinationtype);



		List<MostUsedGenres> results2 = new ArrayList<MostUsedGenres>();
		for (TableRow row : rows2) 
		{  MostUsedGenres genres = new MostUsedGenres(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),String.valueOf(row.getF().get(1).getV()),Integer.parseInt(String.valueOf(row.getF().get(2).getV())));
		results2.add(genres);
		}

		return results2;		

	}

	public List<MostUsedNgrams> showQueryMostUsedNgrams(String input) throws IllegalArgumentException, IOException {


		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		ArrayList<Top10ngrams> results = new ArrayList<Top10ngrams>();
		for (TableRow row : rows) 
		{  Top10ngrams top10ngram = new Top10ngrams(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(top10ngram);
		}

		System.out.println(results);
		String ngram1 = results.get(0).ngram;
		String ngram2 = results.get(1).ngram;
		String ngram3 = results.get(2).ngram;
		String ngram4 = results.get(3).ngram;
		String ngram5 = results.get(4).ngram;
		String ngram6 = results.get(5).ngram;
		String ngram7 = results.get(6).ngram;
		String ngram8 = results.get(7).ngram;
		String ngram9 = results.get(8).ngram;
		String ngram10 = results.get(9).ngram;

		String query= "SELECT table2.publication AS publication,table1.ngram AS ngram,count(table1.ngram) AS counts FROM(SELECT scores.workId AS worka, ngrams.ngram AS ngram FROM workspace.ngram AS ngrams JOIN workspace.score AS scores ON  ngrams.scoreId = scores.scoreId WHERE ngrams.ngram ='";
		query+= ngram1+"' OR ngram='"+ngram2+"' OR ngram='"+ngram3+"' OR ngram='"+ngram4+"' OR ngram='"+ngram5+"' OR ngram='"+ngram6+"' OR ngram='"+ngram7+"' OR ngram='"+ngram8+"' OR ngram='"+ngram9+"' OR ngram='"+ngram10;
		query+="') AS table1 JOIN(SELECT workId AS workb, personId AS personId,publication AS publication FROM workspace.work) AS table2 ON table1.worka = table2.workb GROUP BY publication,ngram ORDER BY publication ASC";
		System.out.println(query);	
		String kind2 = Hashing.md5().hashString(query).toString();
		List<TableRow> rows2 = getQueryResult(query,kind2,querytype,"",destinationtype);


		List<MostUsedNgrams> results2 = new ArrayList<MostUsedNgrams>();
		for (TableRow row : rows2) 
		{  MostUsedNgrams genres = new MostUsedNgrams(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),String.valueOf(row.getF().get(1).getV()),Integer.parseInt(String.valueOf(row.getF().get(2).getV())));
		results2.add(genres);
		}

		return results2;		

	}







	public List<WorkScorePercent> showQueryWorkScorePercent(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);
		List<WorkScorePercent> results = new ArrayList<WorkScorePercent>();
		for (TableRow row : rows)
		{ WorkScorePercent workscorepercent = new WorkScorePercent(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(workscorepercent);
		}


		return results;

	}

	public List<WorkDownloads> showQueryWorkDownloadsPercent(String input) throws IllegalArgumentException, IOException {


		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);
		List<WorkDownloads> results = new ArrayList<WorkDownloads>();
		for (TableRow row : rows)
		{ WorkDownloads workdownloads = new WorkDownloads(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(workdownloads);
		}

		return results;	

	}

	public List<ComposerGenrePercent> showQueryComposerGenrePercent(String input) throws IllegalArgumentException, IOException {


		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<ComposerGenrePercent> results = new ArrayList<ComposerGenrePercent>();
		for (TableRow row : rows)
		{ ComposerGenrePercent composergenrepercent = new ComposerGenrePercent(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(composergenrepercent);
		}

		return results;	

	}


	public List<ComposerKeyPercent> showQueryComposerKeyPercent(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);
		List<ComposerKeyPercent> results = new ArrayList<ComposerKeyPercent>();
		for (TableRow row : rows)
		{ ComposerKeyPercent composerkeypercent = new ComposerKeyPercent(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(composerkeypercent);
		}


		return results;	

	}


	public String showQueryComposerNgram(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		System.out.print("\nQuery Results:\n------------\n");
		for (TableRow row : rows) {
			for (TableCell field : row.getF()) {
				System.out.printf("%-50s", field.getV());
			}
			System.out.println();
		}


		String results = "";

		for (TableRow row : rows)
		{ results += String.valueOf(row.getF().get(0).getV())+"\r\n";
		}

		System.out.println(results);

		return results;


	}



	public List<TimeDownloads> showQueryTimeDownloads(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<TimeDownloads> results = new ArrayList<TimeDownloads>();
		for (TableRow row : rows)
		{ TimeDownloads timedownloads = new TimeDownloads(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(timedownloads);
		}

		return results;	

	}

	public List<TimeDownloadsNormalized> showQueryTimeDownloadsNormalized(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<TimeDownloadsNormalized> results = new ArrayList<TimeDownloadsNormalized>();
		for (TableRow row : rows)
		{ TimeDownloadsNormalized timedownloads = new TimeDownloadsNormalized(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(timedownloads);
		}

		return results;	

	}


	public List<AgeDownloadsComposer> showQueryAgeDownloadsComposer(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<AgeDownloadsComposer> results = new ArrayList<AgeDownloadsComposer>();
		for (TableRow row : rows)
		{ AgeDownloadsComposer agedownloadscomposer = new AgeDownloadsComposer(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(agedownloadscomposer);
		}

		return results;	

	}


	public List<TimePublications> showQueryTimePublications(String input) throws IllegalArgumentException, IOException {


		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<TimePublications> results = new ArrayList<TimePublications>();
		for (TableRow row : rows)
		{ TimePublications timepublications = new TimePublications(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(timepublications);
		}

		return results;	

	}

	public List<AgePublicationsComposer> showQueryAgePublicationsComposer(String input) throws IllegalArgumentException, IOException {


		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<AgePublicationsComposer> results = new ArrayList<AgePublicationsComposer>();
		for (TableRow row : rows)
		{ AgePublicationsComposer agepublications = new AgePublicationsComposer(Integer.parseInt(String.valueOf(row.getF().get(0).getV())),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(agepublications);
		}

		return results;	

	}


	public List<KeyPercent> showQueryKeyPercent(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);
		List<KeyPercent> results = new ArrayList<KeyPercent>();
		for (TableRow row : rows)
		{ KeyPercent keypercent = new KeyPercent(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(keypercent);
		}

		return results;	

	}




	public List<GermanAustrianPercent> showQueryGermanAustrianPercent(String input) throws IOException{

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);
		List<GermanAustrianPercent> results = new ArrayList<GermanAustrianPercent>();
		for (TableRow row : rows)
		{ GermanAustrianPercent germanaustrianpercent = new GermanAustrianPercent(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(germanaustrianpercent);
		}

		return results;	

	}

	public List<CountryDownloads> showQueryCountryDownloads(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<CountryDownloads> results = new ArrayList<CountryDownloads>();
		for (TableRow row : rows)
		{ CountryDownloads countrydownloads = new CountryDownloads(String.valueOf(row.getF().get(0).getV()),Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
		results.add(countrydownloads);
		}

		return results;	

	}




	public List<NgramCountry> showQueryCountry(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		List<NgramCountry> results = new ArrayList<NgramCountry>();
		for (TableRow row : rows)

		{
			String givenValue = String.valueOf(row.getF().get(0).getV());
			String firstValue = "";
			if(givenValue.equals("Austria")){
				firstValue="AT";
			}
			if(givenValue.equals("Belgium")){
				firstValue="BE";
			}
			if(givenValue.equals("Bulgaria")){
				firstValue="BG";
			}
			if(givenValue.equals("Cyprus")){
				firstValue="CY";
			}
			if(givenValue.equals("Czech Republic")){
				firstValue="CZ";
			}
			if(givenValue.equals("Denmark")){
				firstValue="DK";
			}
			if(givenValue.equals("England")){
				firstValue="GB";
			}
			if(givenValue.equals("Estonia")){
				firstValue="EE";
			}
			if(givenValue.equals("Finland")){
				firstValue="FI";
			}
			if(givenValue.equals("France")){
				firstValue="FR";
			}
			if(givenValue.equals("Germany")){
				firstValue="DE";
			}
			if(givenValue.equals("Greece")){
				firstValue="GR";
			}
			if(givenValue.equals("Hungary")){
				firstValue="HU";
			}
			if(givenValue.equals("Ireland")){
				firstValue="IE";
			}
			if(givenValue.equals("Italy")){
				firstValue="IT";
			}
			if(givenValue.equals("Latvia")){
				firstValue="LV";
			}
			if(givenValue.equals("Lithuania")){
				firstValue="LT";
			}
			if(givenValue.equals("Luxembourgh")){
				firstValue="LU";
			}
			if(givenValue.equals("Malta")){
				firstValue="MT";
			}
			if(givenValue.equals("Netherlands")){
				firstValue="NL";
			}
			if(givenValue.equals("Poland")){
				firstValue="PL";
			}
			if(givenValue.equals("Portugal")){
				firstValue="PT";
			}
			if(givenValue.equals("Romania")){
				firstValue="RO";
			}
			if(givenValue.equals("Slovakia")){
				firstValue="RO";
			}
			if(givenValue.equals("Slovenia")){
				firstValue="SI";
			}
			if(givenValue.equals("Spain")){
				firstValue="ES";
			}
			if(givenValue.equals("Sweden")){
				firstValue="SE";
			}


			NgramCountry ngramcountry = new NgramCountry(firstValue,Integer.parseInt(String.valueOf(row.getF().get(1).getV())));
			results.add(ngramcountry);

		}
		System.out.println(results);
		return results;


	}




	public String showQueryNgramComposer(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		String results = "";

		for (TableRow row : rows)
		{ results += String.valueOf(row.getF().get(0).getV())+";\r\n";
		}

		System.out.println(results);

		return results;


	}



	public String showQueryMostFrequentlyUsedGenre(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);

		String results = "";

		for (TableRow row : rows)
		{ results += String.valueOf(row.getF().get(0).getV())+";\r\n";
		}

		return results;


	}




	public String showQueryBuilderResults(String input) throws IllegalArgumentException, IOException {

		int querytype = 0;
		destinationtype = 0;
		String kind = Hashing.md5().hashString(input).toString();
		List<TableRow> rows = getQueryResult(input,kind,querytype,"",destinationtype);
		String results = "Here are your results:"+";\r\n";
		for (TableRow row : rows)
		{ results += String.valueOf(row.getF().get(0).getV())+";\r\n";
		}

		return results;


	}






}