package tr.org.kamp.linux.agarioclone.windowbuilder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FirstFrame extends JFrame {

	private FirstPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public FirstFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new FirstPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//contentPane.setLayout(new BorderLayout(0, 0)); //bizim yaptıgımız formları engelledigi içi kapatıldı.
		setContentPane(contentPane);
		setTitle("Agario Clone");
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		FirstFrame frame = new FirstFrame();
		frame.pack();
		frame.setVisible(true);
		
	}

	

}
