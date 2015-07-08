import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener{
	private static Level level;
	public static final int WIDTH = 144, HEIGHT = 256,SCALE = 3;
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;

	private BufferedImage image;
	private Graphics2D g;

	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setFocusable(true);
		requestFocus();
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			thread.start();
		}
	}
	public void run(){
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		running = true;
		level = new Level();

		long start, elapsed, wait;
		while(running){
			start = System.currentTimeMillis();

			update();
			draw();
			drawToScreen();

			elapsed = System.currentTimeMillis()-start;

			wait = targetTime - elapsed;

			if(wait < 0) wait = 0;
			try{
				Thread.sleep(wait);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void update(){
		level.update();
	}
	private void draw(){
		level.draw(g);
	}
	private void drawToScreen(){
		Graphics g2 = getGraphics();
		g2.drawImage(image,0,0,WIDTH*SCALE,HEIGHT*SCALE,null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key){}
	public void keyPressed(KeyEvent key){
		try{
			int k = key.getKeyCode();
			level.keyPressed(k);
		}catch(Exception e){}
	}
	public void keyReleased(KeyEvent key){
		try{
			int k = key.getKeyCode();
			level.keyReleased(k);
		}catch(Exception e){}
	}

	public void mousePressed(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){
		try{
			Point p = e.getPoint();
			level.mouseReleased(p);
		}catch(Exception f){}
	}

}
