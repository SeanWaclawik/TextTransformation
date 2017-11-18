import java.io.*;
import java.util.*;
import org.json.simple.*;

public class Document {

	// holds the jason text 
	private String raw_text;
	private String strip_text;

	// holds the n-grams
	private ArrayList<String> terms;
	private ArrayList<String> bigrams;
	private ArrayList<String> trigrams;

	private List<String> stops = ["a","the"]; 
	

	// Constructor
	public Document(String fname) {
		
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