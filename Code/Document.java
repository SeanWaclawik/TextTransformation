import java.io.*;
import java.util.*;

import org.json.simple.*;

class Document {

	// holds the jason text 
	String raw_text;
	String strip_text;
	// holds input data from the file
	String name;
	String type;
	String link;
	

	// holds the n-grams
	ArrayList<String> terms;
	ArrayList<String> bigrams;
	ArrayList<String> trigrams;

	List<String> stops = ["a","the"]; 
	

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


	void find_terms() {


	}

	void bigrams() {



	}

	void trigrams() {

	}

	void create_json() {


	}

	




}