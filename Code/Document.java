import java.io.*;
import java.util.*;

import org.json.simple.*;

public class Document {

	// holds the jason text 
	String raw_text;
	String strip_text;
	// holds input data from the file
	String name;
	String type;
	String link;

	String author;
	String titleIndex;


	// holds the n-grams
	private ArrayList<String> terms;
	private ArrayList<String> bigrams;
	private ArrayList<String> trigrams;

	private List<String> stops = ["a","the"]; 
	

	// Constructor

	Document(String fname, String stopWordsLoc) {
		
		JSONArray jFile = (JSONArray) parser.parse(new FileReader(fname));

		  for (Object o : jFile) {
		    JSONObject jObject = (JSONObject) o;

		    type = (String) jObject.get("type");
		    //System.out.println(name);

		    link = (String) jObject.get("link");
		    //System.out.println(city);

		    raw_text = (String) jObject.get("text");
		    //System.out.println(job);
		  }
		
		  int res = initStopWords(stopWordsLoc);
		  

	}

	// Generate the list of stop words from location defined
	int initStopWords(String loc) {
		// try opening location
		
		
		return 1;
	}


	public void find_terms() {


	}

	public void bigrams() {





	}

	public void trigrams() {

	}

	public void create_json() {

		JSONObject out = new JSONObject();

		JSONObject titleObject = new JSONObject();
		if(type == "HTML") {
			titleObject.put("title", name);
			titleObject.put("indices", titleIndex);
		}
		out.put("title", titleObject);


		JSONObject metaObject = new JSONObject();
		metaObject.put("author", author);
		out.put("metadata", metaObject);


		JSONObject ngramsObject = new JSONObject();

		JSONArray ugrams = new JSONArray();
		Iterator itr = terms.entrySet().iterator();
		while(itr.hasNext()) {

			Map.Entry pair = (Map.Entry)itr.next();

			ugrams.add(pair.getKey(), pair.getValue().toArray());
		}
		ngramsObject.put("1", ugrams);

		JSONArray bgrams = new JSONArray();
		Iterator itr = bigrams.entrySet().iterator();
		while(itr.hasNext()) {

			Map.Entry pair = (Map.Entry)itr.next();

			bgrams.add(pair.getKey(), pair.getValue().toArray());
		}
		ngramsObject.put("2", bgrams);

		JSONArray tgrams = new JSONArray();
		Iterator itr = trigrams.entrySet().iterator();
		while(itr.hasNext()) {

			Map.Entry pair = (Map.Entry)itr.next();

			tgrams.add(pair.getKey(), pair.getValue().toArray());
		}
		ngramsObject.put("3", tgrams);

		out.put("ngrams", ngramsObject);



	}

	




}