import javax.swing.SwingUtilities;

public class Driver {

	public static void main(String[] args) {
		ChatBot cb = new ChatBot();
		cb.load();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ChatbotGUI(cb);
			}
		});

	}

}
