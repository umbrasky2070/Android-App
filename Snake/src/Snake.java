import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Snake {
	private Node head = null;
	private Node tail = null;
	private Node n = new Node(20,15,Dir.R);
	private int size = 0;
	private Yard y;
	
	public Snake(Yard y){
		head = n;
		tail = n;
		size =1;
		this.y = y;
	}
	
	public void addHead(){
		Node node = null;
		switch(head.dir){
		case L:
			node = new Node(head.x-1,head.y,head.dir);
			break;
		case R:
			node = new Node(head.x+1,head.y,head.dir);
			break;
		case U:
			node = new Node(head.x,head.y-1,head.dir);
			break;
		case D:
			node = new Node(head.x,head.y+1,head.dir);
			break;
		}
		node.next = head;
		head.perv = node;
		head = node;
		size++;	
	}
	
	public void addTail(){
		Node node = null;
		switch(tail.dir){
		case L:
			node = new Node(tail.x+1,tail.y,tail.dir);
			break;
		case R:
			node = new Node(tail.x-1,tail.y,tail.dir);
			break;
		case U:
			node = new Node(tail.x,tail.y+1,tail.dir);
			break;
		case D:
			node = new Node(tail.x,tail.y-1,tail.dir);
			break;
		}
		tail.next = node;
		node.perv = tail;
		tail = node;
		size++;	
	}
	
	public void checkdead(){
		if(head.x<0||head.x>Yard.X||head.y<2||head.y>Yard.Y){
			y.stop();
		}
		for(Node n = head.next;n != null;n = n.next){
			if(head.x == n.x && head.y == n.y){
				y.stop();
			}
		}
	}

	private class PaintThread implements Runnable{
		public void run() {
			while(true){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
		}
	
	public void move(){
		addHead();
		deleteTail();
		checkdead();
	}
	
	private void deleteTail() {
		if(size ==0) return;
		tail = tail.perv;
		tail.next = null;
	}

	public void draw(Graphics g){
		if(size<=0) return;
		move();
		for(Node n=head;n != null;n=n.next){
			n.draw(g);
		}
	}
		
	 private class Node{
		int w = Yard.BLOCK_SIZE;
		int h = Yard.BLOCK_SIZE;
		int x,y;
		Dir dir = Dir.R;
		Node perv = null;
		Node next = null;

		public Node(int x,int y,Dir dir){
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
		
		public void draw(Graphics g){
			Color c =g.getColor();
			g.setColor(Color.BLACK);
			g.fillRect(x*Yard.BLOCK_SIZE, y*Yard.BLOCK_SIZE, w, h);
			g.setColor(c);
		}
	}
	
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key){
			case KeyEvent.VK_LEFT:
				if(head.dir != Dir.R)
					head.dir = Dir.L;
				break;
			case KeyEvent.VK_RIGHT:
				if(head.dir != Dir.L)
					head.dir = Dir.R;
				break;
			case KeyEvent.VK_UP:
				if(head.dir != Dir.D)
					head.dir = Dir.U;
				break;
			case KeyEvent.VK_DOWN:
				if(head.dir != Dir.U)
					head.dir = Dir.D;
				break;
			}
		}

		
		public void eat(egg e){
			if(this.getRect().intersects(e.getRect())){
				e.reappear();
				addHead();
				y.setScore(y.getScore()+5);
			}
		}
		
		public Rectangle getRect(){
			return new Rectangle(head.x*Yard.BLOCK_SIZE, head.y*Yard.BLOCK_SIZE, head.w, head.h);
		}
}
