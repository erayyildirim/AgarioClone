package tr.org.kamp.linux.agarioclone.windowbuilder;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import tr.org.kamp.linux.agarioclone.logic.GameLogic;
import tr.org.kamp.linux.agarioclone.model.Difficulty;

/**
 * 
 * @author eray
 * @version 1.0
 */
public class FirstPanel extends JPanel {
	private JTextField textField;
	private JPasswordField passwordField;
	JComboBox comboBox;
	private ButtonGroup buttonGroup;
	JRadioButton rdbtnEasy;
	JRadioButton rdbtnMedium;
	JRadioButton rdbtnHard;

	/**
	 * Create the panel for visual view.
	 */
	public FirstPanel() {
		setLayout(new MigLayout("", "[][grow]", "[][][][][][][][]"));
		
		JLabel lblUserName = new JLabel("User Name:");
		add(lblUserName, "cell 0 0,alignx trailing");
		
		textField = new JTextField();
		add(textField, "cell 1 0,growx");
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		add(lblPassword, "cell 0 1,alignx trailing");
		
		passwordField = new JPasswordField();
		add(passwordField, "cell 1 1,growx");
		
		JLabel lblSelectColor = new JLabel("Select Color:");
		add(lblSelectColor, "cell 0 2,alignx trailing");
		//renk seçme
		comboBox = new JComboBox();
		//renkler ekleniyor

		comboBox.addItem("BLUE");
		comboBox.addItem("GREEN");
		comboBox.addItem("PINK");
		comboBox.addItem("MAGENTA");
		comboBox.addItem("ORANGE");
		add(comboBox, "cell 1 2,growx");
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		add(lblDifficulty, "cell 0 3");
		
		rdbtnEasy = new JRadioButton("Easy");
		rdbtnEasy.setSelected(true);
		add(rdbtnEasy, "cell 1 3");
		
		rdbtnMedium = new JRadioButton("Medium");
		add(rdbtnMedium, "cell 1 4");
		
		rdbtnHard = new JRadioButton("Hard");
		add(rdbtnHard, "cell 1 5");
		//butonlar eklendı hangısının secımlı olup olmadıgını buttonGroup bilmez butonlar bılıyor(ıf else ıle bak)
		buttonGroup = new ButtonGroup();
		buttonGroup.add(rdbtnEasy);
		buttonGroup.add(rdbtnMedium);
		buttonGroup.add(rdbtnHard);
		
		/**
		 * Start button is created. When the start button pressed, listen action 
		 * and set is selectedColor
		 * Set game difficulty
		 * Get user name on text field
		 * 
		 */
		JButton btnStart = new JButton("Start");
		//starta basılıdıgında aksıyonu dınle
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Color selectedColor = Color.BLACK;
				switch(comboBox.getSelectedItem().toString()){
					case "PINK":
						selectedColor = Color.PINK;
						break;
					case "BLUE":
						selectedColor = Color.BLUE;
						break;
					case "GREEN":
						selectedColor = Color.GREEN;
						break;
					case "MAGENTA":
						selectedColor = Color.MAGENTA;
						break;
					case "ORANGE":
						selectedColor = Color.ORANGE;
						break;
					default :
						break;
					
				}
				Difficulty difficulty = Difficulty.EASY;
				
				if(rdbtnEasy.isSelected()){
					//EASY
					difficulty = Difficulty.EASY;
				}else if(rdbtnMedium.isSelected()){
					//MEDIUM
					difficulty = Difficulty.MEDIUM;
					
				}else if(rdbtnHard.isSelected()){
					//HARD
					difficulty = Difficulty.HARD;
					
				}else{
					//
				}
				
				
				//kullanıcı adı vermek ıcın textField.getText()'den kullanıcı adını ekrandan aldık
				GameLogic gameLogic = new GameLogic(textField.getText(),selectedColor, difficulty);
				gameLogic.startApplication();
				
			}
		});
		
		add(btnStart, "cell 1 6");
		/**
		 * About button
		 */
		
		JButton btnAbout = new JButton("About");
		// about'a tıklandığında algılanır.
		btnAbout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				//
				JOptionPane.showConfirmDialog(FirstPanel.this, "Bu yazilim GPL Lisansı altindadir,\n Ozgur bir ortamda ozgur yazilimlar kullanilarak,"
						+ " \n ozgur bireyler tarafından gelistirilmistir."
						+ "LYK-Java-2017","About" , JOptionPane.YES_OPTION);
				
			}
		});
		add(btnAbout, "cell 1 7");
		

	}

}
