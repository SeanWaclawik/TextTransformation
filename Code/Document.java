import java.io.*;
import java.util.*;

import org.json.simple.*;

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

	private Map<String, ArrayList<Int>> terms;
	private Map<String, ArrayList<Int>> bigrams;
	private Map<String, ArrayList<Int>> trigrams;


	private ArrayList<String> stops;
	

	// Constructor

	Document(String fname, String stopWordsLoc) {
		
		JSONArray jFile = (JSONArray) parser.parse(new FileReader(fname));

		  	for (Object o : jFile) {
		    	JSONObject jObject = (JSONObject) o;
		    	type = (String) jObject.get("type");
		    	link = (String) jObject.get("link");
		    	raw_text = (String) jObject.get("text");
		  	}
		  
		  	boolean res = initStopWords(stopWordsLoc);  
		  	if (!res){
			  	System.err.format("Exception occered trying to read file '%s'", loc);
			  	e.printStackTrace();
		  	}

		  	strip_text = raw_text;

	}

	// Generate the list of stop words from location defined
	boolean initStopWords(String loc) {
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
		for (int i = 0; i < strip_text.length(); i++) {
			for (String str : stops) {
				if (str.contains(strip_text[i])){
					continue
				}
					
			} 
		}	
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

	




}