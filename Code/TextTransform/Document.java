package TextTransform;

import java.io.*;
import java.util.*;

import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Document {

	// holds the json text 
	private String raw_text;
	private String strip_text;
	// holds input data from the file
	private String name;
	private String type;
	private String link;

	private String author;
	private String titleIndex;


	// holds the n-grams

	private Map<String, ArrayList<Integer>> terms;
	private Map<String, ArrayList<Integer>> bigrams;
	private Map<String, ArrayList<Integer>> trigrams;

	private ArrayList<String> stops;
	

	// Constructor

	public Document(String fname, String stopWordsLoc) {
		
		JSONParser parser = new JSONParser();
		JSONArray jFile = new JSONArray();

		try {
			Object obj = parser.parse(new FileReader(fname));
			jFile.add(obj);

			// Get information from input json file
		  	for (Object o : jFile) {
		    	JSONObject jObject = (JSONObject) o;
		    	type = (String) jObject.get("type");
		    	link = (String) jObject.get("link");
		    	raw_text = (String) jObject.get("text");
		  	}

		  	name = fname.substring(0,fname.length() - 5);
		  	name += ("_" + type);	  
		  	stops = new ArrayList<String>();
		  
		  	// pull stop words from indexer
		  	boolean res = initStopWords(stopWordsLoc);  
		  	if (!res){
			  	System.err.format("Exception occurred trying to read file '%s'\n", stopWordsLoc);
		  	}

		  	strip_text = raw_text;

		  	// initialize search terms maps
		  	terms = new HashMap<String, ArrayList<Integer>>();
		  	bigrams = new HashMap<String, ArrayList<Integer>>();
		  	trigrams = new HashMap<String, ArrayList<Integer>>();
		  	
		  	
		  	// get the terms, bigrams, trigrams
		  	find_terms();
		  	bigrams();
		  	trigrams();
		  	
		  	// make and save the results 
		  	create_json();
		}
		
		
		catch ( IOException | ParseException e) {
			System.err.format("Exception occurred trying to read input JSON file '%s'", fname);
			e.printStackTrace();
		}

	}

	// Generate the list of stop words from location defined
	boolean initStopWords(String loc) {
		// try opening location
		try {
			BufferedReader reader = new BufferedReader(new FileReader(loc));
			String line;
			while ((line = reader.readLine()) != null){
				stops.add(line);
			}
			reader.close();
			return true;
		}
		catch (Exception e) {
			System.err.format("Exception occurred trying to read file '%s'", loc);
			e.printStackTrace();
		}
		return false;
	}

	public boolean isValidWord(String word){
		// Regex for alphaanumeric with dashes and apostrophes allowed
		if (!word.matches("[a-zA-Z0-9'-]+")){
			return false;
		}
		// If the word is within the 50 most common words from indexing
		if (stops.contains(word)){
			return false;
		}
		return true;
	}

	// 1-grams
	public void find_terms() {
		int counter = 0;
		String[] stripped = strip_text.split("\\s+");
		// Go through everything separated by whitespace
		for (int i = 0; i < stripped.length; i++) {
			String lower = stripped[i].toLowerCase();
			if(isValidWord(lower)){
				if (terms.get(lower) == null){
					// Create new ArrayList for corresponding key
					terms.put(lower, new ArrayList<Integer>());
				}
				// Add to the ArrayList values
				terms.get(lower).add(counter);
				counter++;
			}
		}
	}

	public void bigrams() {
		//all terms split by space
		String[] terms = strip_text.split("\\s+"); 
		//convert to lower case
		//toLower(terms);
		for(int pos = 0; pos < terms.length - 1; pos++) {
			String word1 = terms[pos].toLowerCase();
			String word2 = terms[pos + 1].toLowerCase();
			String bigram = word1 + " " + word2;
			if(isValidWord(word1) && isValidWord(word2)) {
				//if the bigram exists add the new index
				if(bigrams.containsKey(bigram)) {
					bigrams.get(bigram).add(pos);
				}
				else {
					ArrayList<Integer> positions = new ArrayList<Integer>();
					positions.add(pos);
					bigrams.put(bigram, positions);
				}
			}
			// else {
			// 	pos++;
			// }
		}



	}

	public void trigrams() {
		//all terms split by space
		String[] terms = strip_text.split("\\s+"); 
		//convert to lower case
		//toLower(terms);
		for(int pos = 0; pos < terms.length - 2; pos++) {
			String word1 = terms[pos].toLowerCase();
			String word2 = terms[pos + 1].toLowerCase();
			String word3 = terms[pos + 2].toLowerCase();
			String trigram = word1 + " " + word2 + " " + word3;
			if(isValidWord(word1) && isValidWord(word2) 
				&& isValidWord(word3)) {

				//if trigram exists add the new index
				if(trigrams.containsKey(trigram)) {
					trigrams.get(trigram).add(pos);
				}
				//create new trigram at current position
				else {
					ArrayList<Integer> positions = new ArrayList<Integer>();
					positions.add(pos);
					trigrams.put(trigram, positions);
				}
			}
			// else {
			// 	pos++;
			// }
		}
	}

	public void create_json() {

		JSONObject out = new JSONObject();

		// title field
		JSONObject titleObject = new JSONObject();
		if(type == "HTML") {
			titleObject.put("title", name);
			titleObject.put("indices", titleIndex);
		}
		out.put("title", titleObject);

		// metadata field
		JSONObject metaObject = new JSONObject();
		metaObject.put("author", author);
		out.put("metadata", metaObject);


		// search terms field
		JSONObject ngramsObject = new JSONObject();

		// 1-grams
		JSONObject ugrams = new JSONObject();
		Iterator<String> itr = terms.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)terms.get(term);

			ugrams.put(term, ind.toString());
		}
		ngramsObject.put("1", ugrams);

		// bigrams
		JSONObject bgrams = new JSONObject();
		itr = bigrams.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)bigrams.get(term);

			bgrams.put(term, ind.toString());
		}
		ngramsObject.put("2", bgrams);

		// trigrams
		JSONObject tgrams = new JSONObject();
		itr = trigrams.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)trigrams.get(term);

			tgrams.put(term, ind.toString());
		}
		ngramsObject.put("3", tgrams);

		out.put("ngrams", ngramsObject);

		// write JSONObject to new json file
		try {

			String filename = name + ".json";
			File file = new File(filename);
			file.createNewFile();

			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(out.toJSONString());
			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e) {
			System.err.format("Exception occurred trying to create JSON file");
			e.printStackTrace();
			return;
		}

	}

	/*
	 * External accessor methods
	 */
	
	// return a copy of the text
	public String getText(){
		String res = new String (this.strip_text);
		return res;
	}

	// return a copy of 1-grams
	public Set<String> getTerms() {
		Set<String> res = new HashSet(this.terms.keySet());
		return res;
	}

	// return a copy of 2-grams
	public Set<String> getBigrams() {
		Set<String> res = new HashSet(this.bigrams.keySet());
		return res;
	}	

	// return a copy of 3-grams
	public Set<String> getTrigrams() {
		Set<String> res = new HashSet(this.trigrams.keySet());
		return res;
	}	

	// return a copy of stop words
	public ArrayList<String> getStops() {
		ArrayList<String> res = new ArrayList<String>(this.stops);
		return res;
	}

	public String getName(){
		String res = new String(this.name);
		return res;
	}


}