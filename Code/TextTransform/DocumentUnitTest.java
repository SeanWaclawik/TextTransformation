package TextTransform;

import static org.junit.Assert.*;

import java.util.*;


import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.junit.Before;
import org.junit.Test;

public class DocumentUnitTest {
	String fname;
	String stopWordsLoc;
	Document uniDoc;
	
	
	@Before
	public void setUp() throws Exception {
		fname = new String("Test/testInput1.json");
		stopWordsLoc = new String("Test/stopWords.txt");
		uniDoc = new Document(fname, stopWordsLoc);
	}

	@Test
	public void testDocument() {
		Document doc = new Document(fname, stopWordsLoc);
		String expectedText = new String("Hello World Book\nbanana\n\napple bub\n");
		
		if (doc!=null){
			if(!doc.getText().equals(expectedText)){
				System.out.printf("got: '%s'\nExpected:'%s'", doc.getText(), expectedText);
				fail("testDocument: Failed to produce expected text");
			}
		}
		else{
			fail("testDocument: Failed to create object");
		}
	}

	@Test
	public void testInitStopWords() {
		ArrayList<String> expected  = new ArrayList<String>(Arrays.asList("apple"));
		ArrayList<String> res = uniDoc.getStops();

		if(!(expected.get(0).equals(res.get(0)))) {
			fail("testInitStopWords: Incorrect stop words");
		}
	}

	@Test
	public void testIsValidWord() {
		ArrayList<String> expectedSuccess = new ArrayList<String>(Arrays.asList("hello","world","book","banana","bub"));
		ArrayList<String> expectedFails = new ArrayList<String>(Arrays.asList("apple", "<>", "$%^&"));
		for (int i = 0; i < expectedFails.size(); i++){
			if (uniDoc.isValidWord(expectedFails.get(i))) {
				fail("testIsValidWord: Incorrect word validity");
			}
		}
		for (int i = 0; i < expectedSuccess.size(); i++){
			if (!uniDoc.isValidWord(expectedSuccess.get(i))) {
				fail("testIsValidWord: Incorrect word validity");
			}
		}
	}

	@Test
	public void testFind_terms() {
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("hello","world","book","banana","bub"));
		Set<String> res = uniDoc.getTerms();

		for(int i = 0; i < expected.size(); i++) {
			if(!res.contains(expected.get(i))) {
				fail("testFind_terms: Incorrect 1-grams");
			}
		}
	}

	@Test
	public void testBigrams() {
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("book banana", "world book", "hello world"));
		Set<String> res = uniDoc.getBigrams();

		for(int i = 0; i < expected.size(); i++) {
			if(!res.contains(expected.get(i))) {
				fail("testFind_terms: Incorrect 2-grams");
			}
		}
	}

	@Test
	public void testTrigrams() {
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList("world book banana", "hello world book"));
		Set<String> res = uniDoc.getTrigrams();

		for(int i = 0; i < expected.size(); i++) {
			if(!res.contains(expected.get(i))) {
				fail("testFind_terms: Incorrect 3-grams");
			}
		}
	}

	@Test
	public void testCreate_json() {
		uniDoc.create_json();
		try{
			InputStream is = JsonParsing.class.getResourceAsStream( uniDoc.getName());
		}
		catch(IOException e)
		{
			fail("Cannot open file to read");
		}
        String jsonTxt = IOUtils.toString( is );

        JSONObject json = (JSONObject) JSONSerializer.toJSON( jsonTxt );    
        if(!json.has("title")){
        	fail("Missing title");
        }
        if(!json.has("indices")){
        	fail("Missing indices");
        }
        if(!json.has("author")){
        	fail("Missing author");
        }       
        if(!json.has("metadata")){
        	fail("Missing metadata");
        }                 
        if(!json.has("ngrams")){
        	fail("Missing ngrams");
        }



		//fail("Not yet implemented");

	}

	@Test
	public void testGetText() {
		String expectedText = new String("Hello World Book\nbanana\n\napple bub\n");
		
		if (uniDoc!=null){
			if(!uniDoc.getText().equals(expectedText)){
				System.out.printf("got: '%s'\nExpected:'%s'", uniDoc.getText(), expectedText);
				fail("testGetText: Failed to get expected text");
			}
		}
		else{
			fail("testGetText: no universal object");
		}
	}

	@Test 
	public void testGetStops() {
		ArrayList<String> res = new ArrayList<String>();
		res = uniDoc.getStops();
		
		if (res != null){
			if (res.size() != 1){
				fail("testGetStops: stop words size is incorrect");
			}
			if (!res.get(0).contains("apple")){
				System.out.printf("expected: 'apple' got : %s", res.get(0));
				fail("testGetStops: stop words list is incorrect");
			}
		}
		else {
			fail("testGetStops: could not get stop words");
		}
	}

}
