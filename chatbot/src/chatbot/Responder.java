package chatbot;

import java.util.ArrayList;

public class Responder{
	private ArrayList<String> questions = new ArrayList<String>();
	private ArrayList<String> responses = new ArrayList<String>();
	private String[] sentences = {"In my opinion ", "I believe it is ", "According to reliable sources(myself) it should be ", "Since you're asking me, it is "};
	public Responder(String[] q, String[] a) {
		for (String question: q) {
			questions.add(question);
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
			String temp = word.toLowerCase();
			temp = temp.replaceAll("[^a-zA-Z ]", "");
			if (questions.contains(temp)) {
				count++;
			}
		}
		return count;
	}
	
	// Returns a string randomly selected from the array of responses.
	public String respond() {
		int rand = (int)(Math.random()*responses.size());
		String response = responses.get(rand);
		rand = (int)(Math.random()*3);
		String temp = sentences[rand] + response;
		return temp;
	}
}
