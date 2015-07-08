import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Button{
	private BufferedImage sprite;
	private double x,y;
	private int width,height;
	public Button(String s){
		try{
			sprite = ImageIO.read(getClass().getResourceAsStream(s));
			width = sprite.getWidth();
			height = sprite.getHeight();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics2D g, boolean draw){
		if(draw) g.drawImage(sprite,(int)(x-width/2),(int)(y-height/2),null);
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,width,height);
	}
	public boolean clicked(Point p){
		Rectangle b = getBounds();
		b.width *= GamePanel.SCALE;
		b.height *= GamePanel.SCALE;
		b.x = b.x*GamePanel.SCALE - b.width/2;
		b.y = b.y*GamePanel.SCALE - b.height/2;
		return b.contains(p);
	}
}
