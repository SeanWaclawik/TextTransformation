import java.io.*;
import java.util.*;
import org.json.simple.*;

class Document {

	// holds the jason text 
	String raw_text;
	String strip_text;

	// holds the n-grams
	ArrayList<String> terms;
	ArrayList<String> bigrams;
	ArrayList<String> trigrams;

	List<String> stops = ["a","the"]; 
	

	// Constructor
	Document(String fname) {
		
		JSONArray jFile = (JSONArray) parser.parse(new FileReader(fname));

		  for (Object o : jFile) {
		    JSONObject jObject = (JSONObject) o;

		    String type = (String) jObject.get("type");
		    //System.out.println(name);

		    String link = (String) jObject.get("link");
		    //System.out.println(city);

		    String text = (String) jObject.get("text");
		    //System.out.println(job);
		  }
		



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