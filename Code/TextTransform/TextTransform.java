package TextTransform;

import java.io.IOException;

class TextTransform {

	public static void main(String args[]) throws IOException {
		if (args.length != 2){
			throw new IOException("Invlaid number of arguments\nTwo arguments required: 1) input json file and 2) stop-words text file");
		}
		
		String fileLocation = args[0];
		String stopWordsFile = args[1];
		
		Document doc = new Document(fileLocation, stopWordsFile);
		
		System.out.print(doc.getText());
	}

}