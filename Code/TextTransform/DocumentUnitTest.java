package TextTransform;

import static org.junit.Assert.*;

import java.util.*;

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
		String expectedText = new String("Hello World Book\nbanana\n\napple bub");
		
		if (doc!=null){
			if(!doc.getText().equals(expectedText)){
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
		fail("Not yet implemented");
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
		fail("Not yet implemented");
	}

	@Test
	public void testTrigrams() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreate_json() {
		fail("Not yet implemented");

	}

	@Test
	public void testGetText() {
		fail("Not yet implemented");
	}

	@Test 
	public void testGetStops() {
		fail("Not yet implemented");
	}

}
