package tr.org.kamp.linux.agarioclone.model;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * A Player class that produced inherited from GameObject
 * @author eray
 * @version 1.0
 *
 */

public class Player extends GameObject {

	private int speed;
	private String playerName;
	
 

	public Player(int x, int y, int radius, Color color, int speed,String playerName) {
		super(x, y, radius, color);
		this.speed = speed;
		this.playerName = playerName;

	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	//adı ekrana cızdırmek ve resım koymak -- y kordinatı yukarı dogru kuculur
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		FontMetrics fontMetrics = g2d.getFontMetrics(g2d.getFont());
		int width = fontMetrics.stringWidth(playerName);
		int nameX = getX() + (getRadius()-width)/2;
		int nameY = getY() - fontMetrics.getHeight();
		g2d.drawString(playerName, nameX, nameY);
		
//		g2d.drawImage(image, getX(), getY(), getRadius(), getRadius(), null); //resim ekleme
	}
	
	@Override
	public void setRadius(int radius) {
		// TODO Auto-generated method stub
		super.setRadius(radius);
		if(getRadius() < 5){
			setRadius(5);
		}else if(getRadius()>250){
			setRadius(250);
		}
	}

}
