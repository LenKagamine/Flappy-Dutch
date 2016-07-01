import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Medal{
	private BufferedImage[] sprites;
	private int type; //-1 = none, 0 = bronze, 1 = silver, 2 = gold, 3 = platinum
	private double x, y;
	private int width, height;
	
	public Medal(String s){
		try{
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream(s));
			width = spritesheet.getWidth()/4;
			height = spritesheet.getHeight();
			sprites = new BufferedImage[4];
			for(int i = 0; i < 4; i++) sprites[i] = spritesheet.getSubimage(i*width,0,width,height);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g, boolean draw){
		if(draw && type >= 0) g.drawImage(sprites[type],(int)x,(int)y,null);
	}
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setType(int type){
		this.type = type;
	}
}
