import java.awt.*;
import java.awt.image.BufferedImage;

public class Pipe{
	private BufferedImage sprite;

	private double moveSpeed;
	private int width, height;
	private double x,y;

	private boolean remove, counted;
	
	public Pipe(BufferedImage s, int holeHeight, double moveSpeed){
		this.moveSpeed = moveSpeed;
		width = 26;
		height = 298;
		x = GamePanel.WIDTH;
		y = holeHeight;
		counted = false;
		sprite = s;
	}
	
	public Rectangle getRect(){
		return new Rectangle((int) x, (int) y, width, height);
	}

	public void update(){
		x -= moveSpeed;
		if(x < -width) remove = true;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(sprite, (int) x, (int) y, null);
	}

	public Rectangle getTopBounds(){
		return new Rectangle((int) x+1, (int) y, width-3, 125);
	}
	
	public Rectangle getBottomBounds(){
		return new Rectangle((int) x+1, (int) y+177, width-3, 121);
	}
	
	public boolean shouldRemove(){
		return remove;
	}
	
	public boolean isCounted(){
		if(!counted && x < (50 - (width/2))){
			counted = true;
			return counted;
		}
		return false;
	}
}
