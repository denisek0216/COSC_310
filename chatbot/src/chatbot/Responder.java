import java.util.ArrayList;

public class Responder {
	private ArrayList<String> questions = new ArrayList<String>();
	private ArrayList<String> responses = new ArrayList<String>();

	public Responder(String[] q, String[] a) {
		for (String question : q) {
			questions.add(question);
		}

		for (String response : a) {
			responses.add(response);
		}
	}

	/*
	 * Checks if the string is contained or matches questions and returns number of
	 * matches found Returns: count of matches to questions
	 */
	public int check(String q) {
		int count = 0;
		String[] match = q.split(" ");
		
		// Run words through PorterStemmer
		for (int i=0; i<match.length; i++) {
			String wLetterOnly = match[i].replaceAll("[^a-zA-Z ]", "");
			char[] wordChars = wLetterOnly.toLowerCase().toCharArray();
			
			PorterStemmer ps = new PorterStemmer(wordChars);	// Create PorterStemmer object
			ps.stem();	// Stemming process
			String processedWord = ps.toString();
			
			match[i] = processedWord;	// Replace processed word back into match
		}
		
		// Count matches
		for (String word : match) {
			if (questions.contains(word)) {
				count++;
			}
		}
		return count;
	}

	// Returns a string randomly selected from the array of responses.
	public String respond() {
		int rand = (int) (Math.random() * responses.size());
		String response = responses.get(rand);
		String temp;
		rand = (int) (Math.random() * 3);
		temp =  response;
		
		return temp;
	}
}
