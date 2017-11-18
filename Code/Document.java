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


	// holds the n-grams
	private ArrayList<String> terms;
	private ArrayList<String> bigrams;
	private ArrayList<String> trigrams;

	private ArrayList<String> stops = ["a","the"]; 
	

	// Constructor

	Document(String fname, String stopWordsLoc) {
		
		JSONArray jFile = (JSONArray) parser.parse(new FileReader(fname));

		  for (Object o : jFile) {
		    JSONObject jObject = (JSONObject) o;

		    type = (String) jObject.get("type");

		    link = (String) jObject.get("link");

		    raw_text = (String) jObject.get("text");
		  }
		  
		  int res = initStopWords(stopWordsLoc);  

	}

	// Generate the list of stop words from location defined
	int initStopWords(String loc) {
		// try opening location
		try {
			BufferedReader reader = new BufferedReader(new fileReader(loc));
			String line;
			while ((line = reader.readLine()) != null){
				stops.add(line);
			}
			reader.close();
			return 1;
		}
		catch (Exception e) {
			System.err.format("Exception occered trying to read file '%s'", loc);
			e.printStackTrace();
			return 0;
		}
	}


	public void find_terms() {


	}

	public void bigrams() {





	}

	public void trigrams() {

	}

	public void create_json() {

		JSONObject out = new JSONObject();


		JSONObject title = new JSONObject();
		title.put("title", )



	}

	




}