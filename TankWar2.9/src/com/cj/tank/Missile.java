package com.cj.tank;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	
	int x, y;
	Direction dir;
	
	private boolean good;
	private boolean live = true;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] missileimages = null;
	private static Map<String,Image> images = new HashMap<String,Image>();
	static	 {
		missileimages = new Image[]{
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
		tk.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif"))
	};
		images.put("D", missileimages[0]);
		images.put("L", missileimages[1]);
		images.put("LD", missileimages[2]);
		images.put("LU", missileimages[3]);
		images.put("R", missileimages[4]);
		images.put("RD", missileimages[5]);
		images.put("RU", missileimages[6]);
		images.put("U", missileimages[7]);
	}
		
	private TankClient tc;
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}
		switch(dir){
		case D: 
			g.drawImage(missileimages[0], x, y, null);
			break;
		case L: g.drawImage(missileimages[1], x, y, null);
			break;
		case LD: g.drawImage(missileimages[2], x, y, null);
			break;
		case LU: g.drawImage(missileimages[3], x, y, null);
			break;
		case R: g.drawImage(missileimages[4], x, y, null);
			break;
		case RD: g.drawImage(missileimages[5], x, y, null);
			break;
		case RU: g.drawImage(missileimages[6], x, y, null);
			break;
		case U: g.drawImage(missileimages[7], x, y, null);
			break;
		}
		
		
		move();
	}

	private void move() {
		
		
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		
		if(x < 0 || y < 0 || x > TankClient.GAME_WIDTH || y > TankClient.GAME_HEIGHT) {
			live = false;
		}		
	}

	public boolean isLive() {
		return live;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}
	
	public boolean hitTank(Tank t) {
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good != t.isGood()) {
			if(t.isGood()) {
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0) t.setLive(false);
			} else {
				t.setLive(false);
			}
			
			this.live = false;
			Explode e = new Explode(x, y, tc);
			tc.explodes.add(e);
			return true;
		}
		return false;
	}
	
	public boolean hitTanks(List<Tank> tanks) {
		for(int i=0; i<tanks.size(); i++) {
			if(hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hitWall(Wall w) {
		if(this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;
			return true;
		}
		return false;
	}
	
}
