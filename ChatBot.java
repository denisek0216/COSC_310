package chatbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatBot {
	public ArrayList<Responder> responders = new ArrayList<Responder>();	
	
	public void load() {
		try {
			BufferedReader brQuestions = new BufferedReader(new FileReader("textfiles/questions.txt"));
			BufferedReader brResponses = new BufferedReader(new FileReader("textfiles/responses.txt"));
			String questions = brQuestions.readLine();
			String responses= brResponses.readLine();
			while (questions!=null && responses!=null) {
				String[] q = questions.split("|");
				String[] r = responses.split("|");	
				responders.add(new Responder(q,r));	
				questions = brQuestions.readLine();
				responses = brResponses.readLine();
			}
			brQuestions.close();
			brResponses.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public Responder getResponder(String input) {
		int max=1;	
		Responder select = null;
		for (Responder r :responders) {
			if (r.check(input)>max) {
				max = r.check(input);
				select = r;
			}
		}
		if (max==1) return responders.get(responders.size()-1); // Return last response
		return select;
	}
	public void respond(String input) {
		System.out.println(getResponder(input).respond());
	}
	public static void main(String[] args) {
		ChatBot cb = new ChatBot();
		cb.load();

		Scanner in = new Scanner(System.in);
		System.out.println("Ask a question:");
		String input = in.nextLine();
		
		cb.respond(input);
		
		in.close();
	}
}
