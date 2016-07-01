import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Counter{
	private BufferedImage[] sprites;
	private int counter;
	private double x, y;
	private int width, height;
	
	public Counter(String s){
		counter = 0;
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(s));
			width = spritesheet.getWidth()/10;
			height = spritesheet.getHeight();
			sprites = new BufferedImage[10];
			for(int i = 0; i < 10; i++){
				sprites[i] = spritesheet.getSubimage(i*width,0,width,height);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void update(){
		x = (GamePanel.WIDTH + width*String.valueOf(counter).length())/2;
	}
	
	public void draw(Graphics2D g, boolean draw){
		if(draw){
			int len = String.valueOf(counter).length(), temp = counter;
			for(int i = 0; i < len; i++){
				g.drawImage(sprites[temp % 10], (int)(x-i*width), (int)y,null);
				temp /= 10;
			}
		}
	}
	
	public void setPosition(double x,double y){
		this.x = x;
		this.y = y;
	}

	public int getCounter(){
		return counter;
	}
	
	public void changeCounter(int counter){
		this.counter = counter;
	}
	
	public void addCounter(){
		counter++;
	}
	
	public void resetCounter(){
		counter = 0;
	}
}
