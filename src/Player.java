import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Player{
	private boolean flap,resetFlap,startFlap;
	private double flapSpeed, fallSpeed, maxFallSpeed; 
	private int width;
	private int height;
	private double x,y,dx,dy;
	private double angle;

	private boolean dead;

	private BufferedImage sprite;

	private int fallHeight;
	public Player(String s){
		flapSpeed = 3.1;
		fallSpeed = 0.15;
		maxFallSpeed = 3.25;
		angle = 0;

		startFlap = false; //GamePanel state = 2, 3
		dead = false; //state = 3

		try{
			sprite = ImageIO.read(getClass().getResourceAsStream(s));
			width = sprite.getWidth();
			height = sprite.getHeight();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		if(!startFlap){
			dy = 0;
		}
		else{
			if(y <= 0) flap = false; //Top border
			if(flap && resetFlap && !dead){
				flap = false;
				resetFlap = false;
				dy = -flapSpeed-Math.random()*0.6+0.3;
			}

			dy += fallSpeed; //Gravity
			if(dy > maxFallSpeed) dy = maxFallSpeed; //Cap falling speed

			if(dy <= 0.25 && dy >= -0.25) fallHeight = (int)y; //Calculate vertex
			if(dy < 0 || y-fallHeight < 5) angle = -10; //Cap angle (top)
			else angle = 1.3*(y-fallHeight) - 16.5; //Calculate angle using distance fell
			if(angle > 50) angle = 50; //Cap angle (bottom)

		}
		if(y >= 190){
			dead = true;
			dy = 0;
			angle = 50;
		}

		x += dx;
		y += dy;
	}
	
	public void draw(Graphics2D g, boolean debug){
		int drawX = (int) (x-width/2);
		int drawY = (int) (y-height/2);
		double rot = Math.toRadians(angle);
		double locX = sprite.getWidth()/2;
		double locY = sprite.getHeight()/2;
		AffineTransform tx = AffineTransform.getRotateInstance(rot, locX, locY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		g.drawImage(op.filter(sprite, null), drawX, drawY, null);
		
		if(debug) g.drawRect((int)x+2,(int)y-10,width/2-2,height/2);
	}
	
	public void setPosition(double x,double y){
		this.x = x;
		this.y = y;
	}
	
	public void setFlapping(){
		flap = true;
	}
	public void resetFlapping(){
		resetFlap = true;
		flap = false;
	}
	public void startFlapping(){
		startFlap = flap = resetFlap = true;
	}
	public boolean getStartFlapping(){
		return startFlap;
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x+2, (int)y-10, width/2-2, height/2);
	}
	
	public void setDead(boolean dead){
		this.dead = dead;
	}
	public boolean getDead(){
		return dead;
	}
	
	public void reset(){
		setPosition(50, 120);
		startFlap = false;
		dead = false;
		dy = 0;
		angle = 0;
	}
}
