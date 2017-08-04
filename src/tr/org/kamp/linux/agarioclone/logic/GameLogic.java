package tr.org.kamp.linux.agarioclone.logic;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import tr.org.kamp.linux.agarioclone.model.Chip;
import tr.org.kamp.linux.agarioclone.model.Difficulty;
import tr.org.kamp.linux.agarioclone.model.Enemy;
import tr.org.kamp.linux.agarioclone.model.GameObject;
import tr.org.kamp.linux.agarioclone.model.Mine;
import tr.org.kamp.linux.agarioclone.model.Player;
import tr.org.kamp.linux.agarioclone.view.GameFrame;
import tr.org.kamp.linux.agarioclone.view.GamePanel;

/**
 * All game mechanics, controls are became created in this class, and acts like a service layer 
 * @author eray
 * @version 1.0
 */

public class GameLogic {
//player hız arttır thread uykusunu 50 yap.
	private Player player;
	private Enemy enemy;
	private ArrayList<GameObject> gameObjects;
	// ekrandan silinecek olan yemler listesi
	private ArrayList<GameObject> chipsToRemove;
	// ekrandan silinecek mayın listesi
	private ArrayList<GameObject> minesToRemove;
	// ekrandan silienecek düsman listesi
	private ArrayList<GameObject> enemiesToRemove;

	private GameFrame gameFrame;
	private GamePanel gamePanel;

	private Random random;

	private boolean isGameRunning;
	private int xTarget;
	private int yTarget;
	//player name için panelden adı verdık
	
	/**
	 * 
	 * @param playerName Name of player
	 * @param selectedColor Color of player
	 * @param difficulty	Game of difficulty
	 */
	public GameLogic(String playerName,Color selectedColor, Difficulty difficulty) {

		player = new Player(10, 10, 15, selectedColor, 4 , playerName);

		gameObjects = new ArrayList<GameObject>();
		chipsToRemove = new ArrayList<GameObject>();
		minesToRemove = new ArrayList<GameObject>();
		enemiesToRemove = new ArrayList<GameObject>();

		gameObjects.add(player);
		gameFrame = new GameFrame();
		gamePanel = new GamePanel(gameObjects);
		gamePanel.setSize(640, 480);

		random = new Random();
		switch (difficulty){
		case EASY :
			fillChips(15);
			fillMines(7);
			fillEnemies(5);
			
			break;
		case MEDIUM :
			fillChips(25);
			fillMines(10);
			fillEnemies(10);
			
			break;
			
		case HARD :
			fillChips(20);
			fillMines(20);
			fillEnemies(20);
	
			break;
			
		default :
		
		}
		

		addMouseEvent(); // mouse hareketiW
	}
	/**
	 * checkCollisions
	 */

	private synchronized void checkCollisions() {

		for (GameObject gameObject : gameObjects) {
			// Instead of just a collision check,
			// we want to check if the object completely
			// contain the other object
			// playerın yuvarlaklıgı kare oldu ve gameobjecten(mine chip) gelen
			// kare ıle karsılastırdı
			if (player.getRectangle().intersects(gameObject.getRectangle())) {
				// if(player.getRectangle().contains(gameObject.getRectangle())){
				if (gameObject instanceof Chip) { // gameObjecten uretılen Chip
													// ise

					player.setRadius(player.getRadius()
							+ gameObject.getRadius()); // player büyüdü.
					// gameObjects.remove(gameObject);

					chipsToRemove.add(gameObject);
				}
				if (gameObject instanceof Mine) {
					player.setRadius((int) player.getRadius() / 2);
					minesToRemove.add(gameObject);
				}

				if (gameObject instanceof Enemy) {
					if (player.getRadius() > gameObject.getRadius()) {
						player.setRadius(player.getRadius()
								+ gameObject.getRadius());
						enemiesToRemove.add(gameObject);

					} else if (player.getRadius() < gameObject.getRadius()) {
						gameObject.setRadius(gameObject.getRadius()
								+ player.getRadius());
						// GAME OVER...
						isGameRunning = false;
					}
				}

			}
			if (gameObject instanceof Enemy) {
				Enemy enemy = (Enemy) gameObject;

				for (GameObject gameObject2 : gameObjects) {
					if (enemy.getRectangle().intersects(
							gameObject2.getRectangle())) {
						if (gameObject2 instanceof Chip) {
							enemy.setRadius(enemy.getRadius()
									+ gameObject2.getRadius());
							chipsToRemove.add(gameObject2);
						}
						if (gameObject2 instanceof Mine) {
							enemy.setRadius(enemy.getRadius() / 2);
							minesToRemove.add(gameObject2);
						}
					}

				}
			}

		}
		// loop bitti. Silinecekler listesine attıklarımızı sildik
		gameObjects.removeAll(chipsToRemove);
		gameObjects.removeAll(minesToRemove);
		gameObjects.removeAll(enemiesToRemove);

	}
	/**
	 * Add new objects until removed objects, and removed list
	 */

	// bir yandan çıkarırken bir yandan eklemesin diye senkronize edıldı
	private synchronized void addNewObjects() {
		// silinen kadar yem oluşturduk
		fillChips(chipsToRemove.size());
		// silinen kadar mayın oluşturduk
		fillMines(minesToRemove.size());

		fillEnemies(enemiesToRemove.size());

		chipsToRemove.clear();
		minesToRemove.clear();
		enemiesToRemove.clear();

	}
	/**
	 * Move enemies acorrding to player status
	 */

	private synchronized void moveEnemies() {
		for (GameObject gameObject : gameObjects) {
			if (gameObject instanceof Enemy) {
				// Enemy nesnesiyse bunun ozellıklrnı almak ıcın cast edildi.
				Enemy enemy = (Enemy) gameObject;
				if (enemy.getRadius() < player.getRadius()) {
					// kacacak
					// iki nokta arası uzaklık alındı
					int distance = (int) Point.distance(player.getX(),
							player.getY(), enemy.getX(), enemy.getY());
					int newX = enemy.getX() + enemy.getSpeed();
					int newY = enemy.getY() + enemy.getSpeed();

					if (calculateNewDistancToEnemy(enemy, distance, newX, newY)) {
						continue;

					}

					newX = enemy.getX() - enemy.getSpeed();
					newY = enemy.getY() + enemy.getSpeed();

					if (calculateNewDistancToEnemy(enemy, distance, newX, newY)) {
						continue;

					}

					newX = enemy.getX() + enemy.getSpeed();
					newY = enemy.getY() - enemy.getSpeed();

					if (calculateNewDistancToEnemy(enemy, distance, newX, newY)) {
						continue;

					}

					newX = enemy.getX() - enemy.getSpeed();
					newY = enemy.getY() - enemy.getSpeed();

					if (calculateNewDistancToEnemy(enemy, distance, newX, newY)) {
						continue;

					}

				} else {
					// kovalayacak

					if (player.getX() > enemy.getX()) {
						enemy.setX(enemy.getX() + enemy.getSpeed());
					} else if (player.getX() < enemy.getX()) {
						enemy.setX(enemy.getX() - enemy.getSpeed());
					}

					if (player.getY() > enemy.getY()) {
						enemy.setY(enemy.getY() + enemy.getSpeed());
					} else if (player.getY() < enemy.getY()) {
						enemy.setY(enemy.getY() - enemy.getSpeed());
					}

				}

			}

		}
	}
	
	/**
	 * Get two points interjacent distance and set to for enemy new distance
	 * @param enemy
	 * @param distance
	 * @param x
	 * @param y
	 * @return
	 */

	private boolean calculateNewDistancToEnemy(Enemy enemy, int distance,
			int x, int y) {
		int newDistance = (int) Point.distance(player.getX(), player.getY(), x,
				y);
		if (newDistance > distance) {
			enemy.setX(x);
			enemy.setY(y);
			return true;
		}
		return false;
	}
	
	/**
	 * Fill chips until n with random.
	 * @param n
	 */

	// yem olusturma
	private synchronized void fillChips(int n) {
		for (int i = 0; i < n; i++) {
			// panel kadar random atama
			int x = random.nextInt(gamePanel.getWidth());
			int y = random.nextInt(gamePanel.getHeight());
			if (x >= gamePanel.getWidth()) {
				x -= 15;
			}
			if (y >= gamePanel.getWidth()) {
				y -= 15;
			}
			gameObjects.add(new Chip(x, y, 10, Color.CYAN));
		}

	}
	
	/**
	 * Fill mines until n with random
	 * This mines doesn't out of range.
	 * @param n
	 */

	private void fillMines(int n) {
				for (int i = 0; i < n; i++) {
		
					int x = random.nextInt(gamePanel.getWidth());
					int y = random.nextInt(gamePanel.getHeight());
					if (x >= gamePanel.getWidth()) {
						x -= 15;
					}
					if (y >= gamePanel.getHeight()) {
						y -= 15;
					}
	
					Mine mine = new Mine(x, y, 20, Color.GREEN);
		
					while (player.getRectangle().intersects(mine.getRectangle())) {
						x = random.nextInt(gamePanel.getWidth());
						y = random.nextInt(gamePanel.getHeight());
						if (x >= gamePanel.getWidth()) {
							x -= 15;
						}
						if (y >= gamePanel.getHeight()) {
							y -= 15;
						}
						mine.setX(x);
						mine.setY(y);
					}
		
					gameObjects.add(mine);
				}
		 	}

		

	/**
	 * Fill enemies until n with random in game panel. And set enemy radius.
	 * @param n
	 */

	private void fillEnemies(int n) {
				for (int i = 0; i < n; i++) {
					int x = random.nextInt(gamePanel.getWidth());
					int y = random.nextInt(gamePanel.getHeight());
					if (x >= gamePanel.getWidth()) {
						x -= 15;
					}
					if (y >= gamePanel.getHeight()) {
						y -= 15;
					}
					Enemy enemy = new Enemy(x, y, (random.nextInt(10) + 25), Color.RED,1);
					while (player.getRectangle().intersects(enemy.getRectangle())) {
						x = random.nextInt(gamePanel.getWidth());
						y = random.nextInt(gamePanel.getHeight());
						if (x >= gamePanel.getWidth()) {
							x -= 15;
						}
						if (y >= gamePanel.getHeight()) {
							y -= 15;
						}
						enemy.setX(x);
						enemy.setY(y);
					}
		
					gameObjects.add(enemy);
				}
			}
	/**
	 * In this section, Game is start with thread.
	 */
	private void startGame() {
		new Thread(new Runnable() {
			public void run() {
				while (isGameRunning) {
					movePlayer();
					moveEnemies();
					checkCollisions();
					addNewObjects();
					gamePanel.repaint();

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}
	/**
	 * Game Frame is visible  and start game because isGameRunning is=true
	 */
	public void startApplication() {
		gameFrame.setContentPane(gamePanel);
		gameFrame.setVisible(true);
		isGameRunning = true;
		startGame();
	}
	/**
	 * Mouse movements are detected.
	 */
	private void addMouseEvent() {
		gameFrame.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {

			}
		});

		gamePanel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

				xTarget = e.getX();
				yTarget = e.getY();

			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});

	}
	/**
	 * object follow the mouse event
	 */

	private synchronized void movePlayer() {
		if (xTarget > player.getX()) {
			player.setX(player.getX() + player.getSpeed());
		} else if (xTarget < player.getX()) {
			player.setX(player.getX() - player.getSpeed());
		}

		if (yTarget > player.getY()) {
			player.setY(player.getY() + player.getSpeed());
		} else if (yTarget < player.getY()) {
			player.setY(player.getY() - player.getSpeed());
		}

	}
}
