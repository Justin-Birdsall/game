package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;


public class GamePanel extends JPanel implements Runnable{
	//SCREEN SETTINGS FOR THE GAME
	final int originalTileSize = 16; //this is setting the game by 16 x 16 
	
	final int scale =3; //scales the pixels up so the char doesn't look small
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol=16;
	public final int maxScreenRow =12;
	public final int screenWidth =tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//THE WORLD SETTINGS
	public final int maxWorldCol =50;
	public final int maxWorldRow =50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	//FPS
	int FPS =60;
	TileManager tileM = new TileManager(this);
	KeyHandler keyH =new KeyHandler();
	Thread gameThread;
	public Player player = new Player(this,keyH);
	
	
	
	
	
	public GamePanel () {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void sstartGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void run() {
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double nextDrawTime= System.nanoTime()+ drawInterval;
		
		while(gameThread != null) {
			
	
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				if (remainingTime < 0) {
					remainingTime = 0;
				}
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			}
			
			catch (InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	public void update() {
		player.update();
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileM.draw(g2);
		
		player.draw(g2);
		
		g2.dispose();
	}
}