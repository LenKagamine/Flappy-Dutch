import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Ground{
	private BufferedImage image;
	private int width;
	private double x, y, speed;

	public Ground(String s,double speed){
		this.speed = speed;
		try{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			width = image.getWidth();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		x += speed;
		while(x <= -width) x += width;
		while(x >= width) x -= width;
	}
	
	public void draw(Graphics2D g){
		g.drawImage(image, (int) x, (int) y, null);
		if(x < 0) g.drawImage(image, (int) x+GamePanel.WIDTH, (int) y, null);
		else g.drawImage(image, (int) x-GamePanel.WIDTH, (int) y, null);
	}
	
	public void setPosition(double x,double y){
		this.x = x;
		this.y = y;
	}
}
