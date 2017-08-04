package tr.org.kamp.linux.agarioclone.view;

import javax.swing.JFrame;
/**
 * 
 * @author eray
 *
 */

public class GameFrame extends JFrame{
	
	/**
	 * Part of swing
	 * Game frame is created
	 */
	public GameFrame(){
		setTitle("Agario Clone");
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
