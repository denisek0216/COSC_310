package chatbot;

import java.util.ArrayList;

public class Responder{
	boolean cussFlag = false;
	
	private ArrayList<String> questions = new ArrayList<String>();
	private ArrayList<String> responses = new ArrayList<String>();
	
	public Responder(String[] q, String[] a) {
		for (String question: q) {
			questions.add(question.toLowerCase());
			if (question.contains("fuck")|question.contains("shit")|question.contains("dick")|question.contains("cunt"))
				cussFlag=true;	// Raise cuss flag
		}
	
		for (String response: a) {
			responses.add(response);
		}
	}
	
	/*
	 * Checks if the string is contained or matches questions and returns number of matches found
	 * Returns: count of matches to questions
	 */
	public int check(String q) {
		int count = 0;
		String[] match = q.split(" ");
		for (String word: match) {
			if (questions.contains(word)) {
				count++;
			}
		}
		return count;
	}
	
	// Returns a string randomly selected from the array of responses.
	public String respond() {
		int rand = (int)Math.random()*responses.size();
		
		// Cuss exception
		if (cussFlag) {
			cussFlag=false;	// Put down cuss flag
			return "I don't like cussing. Please repeat yourself in a polite manner. :)";
		}
		return responses.get(rand);
	}
}
