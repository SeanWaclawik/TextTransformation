import java.io.*;
import java.util.*;

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

	Document(String fname, String stopWordsLoc) {
		JSONParser parser = new JSONParser();
		JSONArray jFile = new JSONArray();
		try {
			Object obj = parser.parse(new FileReader(fname));
			jFile.add(obj);

		  	for (Object o : jFile) {
		    	JSONObject jObject = (JSONObject) o;
		    	type = (String) jObject.get("type");
		    	link = (String) jObject.get("link");
		    	raw_text = (String) jObject.get("text");
		  	}
		  
		  	boolean res = initStopWords(stopWordsLoc);  
		  	if (!res){
			  	System.err.format("Exception occered trying to read file '%s'", stopWordsLoc);
		  	}

		  	strip_text = raw_text;
		  	
		  	// get the terms, bigrams, trigrams
		  	find_terms();
		  	bigrams();
		  	trigrams();
		  	
		  	// make and save the results 
		  	create_json();
		}
		
		
		catch ( IOException | ParseException e) {
			System.err.format("Exception occered trying to read input JSON file '%s'", fname);
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
			System.err.format("Exception occered trying to read file '%s'", loc);
			e.printStackTrace();
		}
		return false;
	}

	public boolean isValidWord(String word){
		if (!word.matches("[a-zA-Z0-9'-]+")){
			return false;
		}
		if (stops.contains(word)){
			return false;
		}
		return true;
	}

	// 1-grams
	public void find_terms() {
		int counter = 0;
		String[] stripped = strip_text.split("\\s+");
		for (int i=0; i<stripped.length; i++) {
			String lower = stripped[i].toLowerCase();
			if(isValidWord(lower)){
				if (terms.get(lower) == null){
					terms.put(lower, new ArrayList<Integer>());
				}
				terms.get(lower).add(counter);
				counter++;
			}
		}
	}

	public void bigrams() {
		return;
	}

	public void trigrams() {
		return;
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

		JSONObject ugrams = new JSONObject();
		Iterator<String> itr = terms.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)terms.get(term);

			ugrams.put(term, ind.toArray());
		}
		ngramsObject.put("1", ugrams);

		JSONObject bgrams = new JSONObject();
		itr = bigrams.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)bigrams.get(term);

			bgrams.put(term, ind.toArray());
		}
		ngramsObject.put("2", bgrams);

		JSONObject tgrams = new JSONObject();
		itr = trigrams.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)trigrams.get(term);

			tgrams.put(term, ind.toArray());
		}
		ngramsObject.put("3", tgrams);

		out.put("ngrams", ngramsObject);

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
			System.err.format("Exception occurred trying to create json file");
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




}