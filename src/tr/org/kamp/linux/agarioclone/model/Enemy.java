package tr.org.kamp.linux.agarioclone.model;

import java.awt.Color;

public class Enemy extends GameObject {
	
	private int speed;
	/**
	 * 
	 * @param x
	 * @param y
	 * @param radius
	 * @param color
	 * @param speed
	 */

	public Enemy(int x, int y, int radius, Color color, int speed) {
		super(x, y, radius, color);
		this.speed = speed;
		
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	/**
	 * Sets param between 5-250
	 * 
	 */
	
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
