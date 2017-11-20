import java.io.*;
import java.util.*;

<<<<<<< HEAD
import org.json.*;
=======
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
>>>>>>> e6e617c11f7ed15accb1c2e7095e53b03dc76fd2

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

<<<<<<< HEAD
	private Map<String, ArrayList<Integer> > terms;
	private Map<String, ArrayList<Integer> > bigrams;
	private Map<String, ArrayList<Integer> > trigrams;


=======
	private Map<String, ArrayList<Integer>> terms;
	private Map<String, ArrayList<Integer>> bigrams;
	private Map<String, ArrayList<Integer>> trigrams;

>>>>>>> e6e617c11f7ed15accb1c2e7095e53b03dc76fd2
	private ArrayList<String> stops;
	

	// Constructor

	Document(String fname, String stopWordsLoc) {
<<<<<<< HEAD
		
		JSONArray jFile = (JSONArray) parser.parse(new FileReader(fname));
=======
		JSONParser parser = new JSONParser();
		JSONArray jFile = new JSONArray();
		try {
			Object obj = parser.parse(new FileReader(fname));
			jFile.add(obj);
>>>>>>> e6e617c11f7ed15accb1c2e7095e53b03dc76fd2

		  	for (Object o : jFile) {
		    	JSONObject jObject = (JSONObject) o;
		    	type = (String) jObject.get("type");
		    	link = (String) jObject.get("link");
		    	raw_text = (String) jObject.get("text");
		  	}
<<<<<<< HEAD
		  
		  	boolean res = initStopWords(stopWordsLoc);  
		  	if (!res){
			  	System.err.format("Exception occered trying to read file '%s'", loc);
			  	e.printStackTrace();
		  	}

		  	strip_text = raw_text;
=======

		  	stops = new ArrayList<String>();
		  
		  	boolean res = initStopWords(stopWordsLoc);  
		  	if (!res){
			  	System.err.format("Exception occurred trying to read file '%s'\n", stopWordsLoc);
		  	}

		  	strip_text = raw_text;

		  	
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
>>>>>>> e6e617c11f7ed15accb1c2e7095e53b03dc76fd2

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
<<<<<<< HEAD
		for (int i=0; i<stripped.length(); i++) {
			String[] lower = stripped[i].toLowerCase();
			if(isValidWord(lower)){
				if (terms.get(lower == null)){
					terms.put(lower, new ArrayList<Int>());
=======
		for (int i=0; i<stripped.length; i++) {
			String lower = stripped[i].toLowerCase();
			if(isValidWord(lower)){
				if (terms.get(lower) == null){
					terms.put(lower, new ArrayList<Integer>());
>>>>>>> e6e617c11f7ed15accb1c2e7095e53b03dc76fd2
				}
				terms.get(lower).add(counter);
				counter++;
			}
		}
	}

<<<<<<< HEAD
=======
	public void bigrams() {
		return;
	}

	public void trigrams() {
		return;
	}
>>>>>>> e6e617c11f7ed15accb1c2e7095e53b03dc76fd2

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

			ugrams.put(term, ind.toString());
		}
		ngramsObject.put("1", ugrams);

		JSONObject bgrams = new JSONObject();
		itr = bigrams.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)bigrams.get(term);

			bgrams.put(term, ind.toString());
		}
		ngramsObject.put("2", bgrams);

		JSONObject tgrams = new JSONObject();
		itr = trigrams.keySet().iterator();
		while(itr.hasNext()) {

			String term = itr.next();
			ArrayList<Integer> ind = (ArrayList<Integer>)trigrams.get(term);

			tgrams.put(term, ind.toString());
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






	private void toLower(String[] words){
		for(String word : words){
			word.toLowerCase();
		}
	}
	private boolean isValidWord(String word){
		if(Arrays.asList(stops).contains(word)){
			return false;
		}
		if(!Character.isLetterOrDigit(word[0])){
			return false;
		}
	}


	public void bigrams() {
		//all terms split by space
		String[] terms = strip_text.split("\\s+"); 
		//convert to lower case
		toLower(terms);
		for(int pos = 0; pos < terms.length; pos++){
			String bigram = terms[pos] + " " + terms[pos + 1];
			if(isValidWord(terms[pos]) && isValidWord(terms[pos + 1])){
				//if the bigram exists add the new index
				if(bigrams.containsKey(bigram)){
					bigrams.get(bigram).add(pos);
				}
				else{
					ArrayList<int> positions = new ArrayList<int>();
					positions.add(pos);
					bigrams.put(bigram, positions);
				}
			}
			else{
				pos++;
			}
		}



	}

	public void trigrams() {
		//all terms split by space
		String[] terms = strip_text.split("\\s+"); 
		//convert to lower case
		toLower(terms);
		for(int pos = 0; pos < terms.length; pos++){
			String trigram = terms[pos] + " " + terms[pos + 1] +
			 " " + terms[pos + 2];
			if(isValidWord(terms[pos]) && isValidWord(terms[pos + 1]) 
				&& isValidWord(terms[pos + 2])){

				//if trigram exists add the new index
				if(trigrams.containsKey(trigram)){
					trigrams.get(trigram).add(pos);
				}
				//create new trigram at current position
				else{
					ArrayList<int> positions = new ArrayList<int>();
					positions.add(pos);
					trigrams.put(trigram, positions);
				}
			}
			else{
				pos++;
			}
		}
	}


}