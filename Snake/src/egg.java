import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;


public class egg {
	int x,y;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	private static Random r = new Random();
	private Color color =Color.GREEN;
	
	public egg(int x,int y){
		this.x = x;
		this.y = y;
	}
	
	public egg(){
		this(r.nextInt(Yard.X),r.nextInt(Yard.Y-2)+2);
	}
	
	public void reappear(){
		this.x = r.nextInt(Yard.X);
		this.y = r.nextInt(Yard.Y-2)+2;
	}
	
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillRect(x*Yard.BLOCK_SIZE,y*Yard.BLOCK_SIZE,w,h);
		g.setColor(c);
		if(color == Color.green) 
			color = Color.red;
		else
			color = Color.green;
		
	}
	public Rectangle getRect(){
		return new Rectangle(Yard.BLOCK_SIZE*x,Yard.BLOCK_SIZE*y,w,h);
		
	}
}
