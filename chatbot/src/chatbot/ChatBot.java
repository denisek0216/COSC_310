package chatbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatBot {
	public ArrayList<String> profanityFilter = new ArrayList<String>();
	public ArrayList<Responder> responders = new ArrayList<Responder>();

	/*
	 * Loads textfiles located in a fixed directory textfiles/.. and creates
	 * responders which are then added to the arraylist.
	 */
	public void load() {
		try {
			BufferedReader brQuestions = new BufferedReader(new FileReader("textfiles/questions.txt"));
			BufferedReader brResponses = new BufferedReader(new FileReader("textfiles/responses.txt"));
			BufferedReader brProfanity = new BufferedReader(new FileReader("textfiles/profanity.txt"));

			String questions = brQuestions.readLine();
			String responses = brResponses.readLine();
			String profanity = brProfanity.readLine();

			// initialize Responders
			while (questions != null && responses != null) {
				String[] q = questions.split(",");
				String[] r = responses.split(",");

				responders.add(new Responder(q, r));
				questions = brQuestions.readLine();
				responses = brResponses.readLine();
			}

			// add profanity words to arraylist
			while (profanity != null) {
				profanityFilter.add(profanity.toLowerCase());
				profanity = brProfanity.readLine();
			}
			brQuestions.close();
			brResponses.close();
			brProfanity.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	/*
	 * Returns a responder based on the input string. The responder with the most
	 * matches will be returned. Returns: Responder that contains the most amount of
	 * matches within its strings.
	 */
	public Responder getResponder(String input) {
		int max = 0;
		Responder select = null;
		for (Responder r : responders) {
			if (r.check(input) > max) {
				max = r.check(input);
				select = r;
			}
		}
		if (max <=1) //if no matches are found
			System.out.println("Sorry we dont understand you just yet! Feel free to ask another question!");
		return select;
	}

	/*
	 * Prints out a response onto the console.
	 */
	public void respond(String input) {
		String[] temp = input.split(" ");
		for (String s : temp) {
			if (profanityFilter.contains(s)) { // cusswords filter
				System.out.println("Please stop cussing.");
				return;
			}
		}
		Responder r = getResponder(input);
		if (r != null)
			System.out.println(r.respond());

	}

	public static void main(String[] args) {
		ChatBot cb = new ChatBot();
		cb.load();

		Scanner in = new Scanner(System.in);
		System.out.println("Ask a question:");
		String input = in.nextLine();
		cb.respond(input);
		while (input.toLowerCase() != "exit") {
			input = in.nextLine();
			cb.respond(input);
		}

		in.close();
	}
}
