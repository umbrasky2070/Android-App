import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;





public class Yard  extends JFrame{
	PaintThread paintThread = new PaintThread();
	
	public static final int X = 30;
	public static final int Y = 30;
	public static final int BLOCK_SIZE = 15;
	public  Font fontgameover = new Font("ËÎÌå",Font.BOLD,50);
	
	Image offImage = null;
	
	private boolean gameover = false;
	
	private int score = 0;
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	Snake s = new Snake(this);
	egg e = new egg();

	public void launch(){
		setLocation(300, 300);
		setSize(X*BLOCK_SIZE, Y*BLOCK_SIZE);
		setVisible(true);
		
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		
		this.addKeyListener(new KeyMonitor());
		new Thread(paintThread).start();
		
	}
	
	public static void main(String[] args){
		new Yard().launch();
	}
	
	public void paint(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, X*BLOCK_SIZE, Y*BLOCK_SIZE);
		g.setColor(Color.BLACK);
		for(int i=0;i<X;i++){
			g.drawLine(BLOCK_SIZE*i,0,BLOCK_SIZE*i,BLOCK_SIZE*Y);
		}
		for(int i=0;i<Y;i++){
			g.drawLine(0, BLOCK_SIZE*i, BLOCK_SIZE*X, BLOCK_SIZE*i);
		}
		g.setColor(Color.YELLOW);
		g.drawString("Score:"+score, 10, 50);
		if(gameover){
			g.setFont(fontgameover);
			g.drawString("ÓÎÏ·½áÊø", 120, 120);
			paintThread.pause();
		}
		g.setColor(c);
		
		s.draw(g);
		e.draw(g);
		s.eat(e);
	}
	
	public void update(Graphics g){
		if(offImage ==null){
			offImage = createImage(BLOCK_SIZE*X, BLOCK_SIZE*Y);
		}
		Graphics goff = offImage.getGraphics();
		paint(goff);
		g.drawImage(offImage, 0, 0, null);	
	}
	
	public class PaintThread implements Runnable{
		private boolean running = true;
		private boolean pause = false;
		
		public void run() {
			while(running){
				if(pause) continue;
				else  repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			}
		}
		private void restart() {
			pause = false;
			s= new Snake(Yard.this);
			gameover = false;
		}
		
		public void pause(){
			pause = true;
		}
		public void gameover() {
			running = true;
			
		}
	}
	private class KeyMonitor extends KeyAdapter{
		public void keyReleased(KeyEvent e){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_F2){
				paintThread.restart();
			}
			s.keyReleased(e);
		}
	}
	public void stop() {
		gameover = true;
		
	}
	
}
