import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

@SuppressWarnings("serial")
public class ChatbotGUI extends JFrame {

	JTextArea area;
	JTextField field;
	JScrollPane scroll;
	JPanel panel;
	JLabel label;
	Timer timer;
	boolean typing;
	
	public ChatbotGUI(ChatBot cb) {
		createAndDisplayGUI(cb);
	}
	
	private void createAndDisplayGUI(ChatBot chatbot) {
		/** Set JFrame properties **/
		setTitle("ChatbotGUI");
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400,400);
		
		/** Create and arrange JPanel **/
		panel = new JPanel();
		label = new JLabel("Enter your response here:");
		panel.setLayout(new GridLayout(2,1));
		panel.add(label);
		
		field = new JTextField();	// Create JTextField
		panel.add(field);	// Add JTextField to panel
		
		area = new JTextArea();	// Create JTextArea
		area.setEditable(false);	// Unable to edit printed text
		area.setMargin(new Insets(8,8,8,8));
		
		scroll = new JScrollPane(area);	// Make text area scrollable		
		add(scroll);
		
		add(panel,BorderLayout.SOUTH);	// Add panel to bottom
		
		/** Create Timer **/
		timer = new Timer(1, new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (!typing)	// If user is not typing
					label.setText("Waiting for response...");
			}
		});
		timer.setInitialDelay(2500);	// Wait for 2.5s before printing "waiting for response" 
		
		/** Add KeyListener to Textfield **/
		field.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				timer.stop();	// Stop timer when user is typing
				typing=true;
			}
			
			public void keyReleased(KeyEvent ke) {
				typing=false;	// User it not typing
				if (!timer.isRunning()) timer.start();	// Restart timer
			}
		});
		
		/** Add ActionListener to TextField **/
		field.addActionListener(new ActionListener() {
			// For obtaining reply from ChatBot
			ChatBot cb = chatbot;
			
			@Override
			public void actionPerformed(ActionEvent ae) {
				String message = field.getText();
				String reply = cb.respond(message);
				
				area.append("User: "+message+"\n");
				field.setText("");	// Reset text field				
				area.append("Chatbot: "+reply+"\n");// Get response from Responder
			}
		});	
	}
	
}