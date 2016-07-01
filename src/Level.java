import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Level{
	private BufferedImage bg;
	private Player player;
	private Ground ground;
	private ArrayList<Pipe> pipes;
	private BufferedImage pipe;
	private Counter counter;
	private Counter score;
	private Counter bestscore;

	private long startTime, delay;

	private Button flappybird;
	private Button getready;
	private Button gameover;
	private Button scoreboard;
	private Button newscore;
	private Button start;
	private Button exit;
	private Button retry;
	private Button menu;

	private Medal medal;

	private int state;
	private boolean highscore;
	private boolean debug;
	
	public Level(){
		state = 0; //0 = main menu; 1 = get ready; 2 = playing; 3 = dead+score

		try{
			bg = ImageIO.read(getClass().getResourceAsStream("background.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}

		ground = new Ground("ground.png", -1.2);
		ground.setPosition(0, 200);

		player = new Player("bird.png");
		player.setPosition(50, 120);

		pipes = new ArrayList<Pipe>();
		try{
			pipe = ImageIO.read(getClass().getResourceAsStream("pipe.png"));
		}
		catch(Exception e){
			e.printStackTrace();
		}

		counter = new Counter("counter.png");
		counter.setPosition(75, 30);

		score = new Counter("score.png");
		score.setPosition(112, 114);

		bestscore = new Counter("score.png");
		bestscore.setPosition(112, 135);

		flappybird = new Button("flappybird.png");
		flappybird.setPosition(GamePanel.WIDTH/2, 75);

		getready = new Button("getready.png");
		getready.setPosition(GamePanel.WIDTH/2, 100);

		gameover = new Button("gameover.png");
		gameover.setPosition(GamePanel.WIDTH/2, 75);

		scoreboard = new Button("scoreboard.png");
		scoreboard.setPosition(GamePanel.WIDTH/2, 125);

		newscore = new Button("new.png");
		newscore.setPosition(90, 117);

		start = new Button("start.png");
		start.setPosition(GamePanel.WIDTH/2-25, 150);

		exit = new Button("exit.png");
		exit.setPosition(GamePanel.WIDTH/2+25, 150);

		retry = new Button("ok.png");
		retry.setPosition(GamePanel.WIDTH/2-25, 175);

		menu = new Button("menu.png");
		menu.setPosition(GamePanel.WIDTH/2+25, 175);

		medal = new Medal("medals.png");
		medal.setPosition(29, 117);

		startTime = 0;
		delay = 1200;
		highscore = false;
		debug = false;
	}

	private void checkCollisions(){
		Rectangle p = player.getBounds();
		for(int i = 0; i < pipes.size(); i++){
			Rectangle p1 = pipes.get(i).getTopBounds();
			Rectangle p2 = pipes.get(i).getBottomBounds();
			if(p.intersects(p1) || p.intersects(p2)){
				player.setDead(true);
			}
		}
	}

	public void update(){
		if(state < 3) ground.update();
		if(state == 2 || state == 3) player.update();
		if(state == 2){
			if(player.getDead()){
				state = 3;
				if(counter.getCounter() >= 40) medal.setType(3);
				else if(counter.getCounter() >= 30) medal.setType(2);
				else if(counter.getCounter() >= 20) medal.setType(1);
				else if(counter.getCounter() >= 10) medal.setType(0);
				else medal.setType(-1);
				
				score.changeCounter(counter.getCounter());
				if(score.getCounter() > bestscore.getCounter()){
					bestscore.changeCounter(score.getCounter());
					highscore = true;
				}
			}
			else{
				counter.update();
				for(int i = 0; i < pipes.size(); i++){
					Pipe p = pipes.get(i);
					p.update();
					if(p.shouldRemove()){
						pipes.remove(i);
						i--;
					}
					if(p.isCounted()) counter.addCounter();
				}
				checkCollisions();
			}
			
			if(startTime == 0) startTime = System.currentTimeMillis();
			else{
				long elapsed = System.currentTimeMillis() - startTime;
				if(elapsed > delay){
					pipes.add(new Pipe(pipe,(int)(Math.random()*101)-100,1.2));
					startTime = System.currentTimeMillis();
				}
			}
		}
	}
	
	public void draw(Graphics2D g){
		g.drawImage(bg, 0, 0, null);
		for(int i = 0; i < pipes.size(); i++) pipes.get(i).draw(g);
		ground.draw(g);
		player.draw(g, debug);

		flappybird.draw(g, state == 0);
		start.draw(g, state == 0);
		exit.draw(g, state == 0);

		getready.draw(g, state == 1);
		counter.draw(g, state == 1 || state == 2);

		gameover.draw(g, state == 3);
		scoreboard.draw(g, state == 3);
		retry.draw(g, state == 3);
		menu.draw(g, state == 3);
		score.draw(g, state == 3);
		newscore.draw(g, state == 3 && highscore);
		bestscore.draw(g, state == 3);
		medal.draw(g, state == 3);
	}
	
	public void keyPressed(int k){
		if(k == KeyEvent.VK_SPACE){
			if(state == 1){
				player.startFlapping();
				state = 2;
			}
			else if(state == 2) player.setFlapping();
		}
	}
	
	public void keyReleased(int k){
		if(k == KeyEvent.VK_SPACE && state == 2) player.resetFlapping();
		if(k == KeyEvent.VK_D) debug = !debug;
	}
	
	public void mouseReleased(Point p){
		if(state == 0){
			if(start.clicked(p)){
				state = 1;
			}
			if(exit.clicked(p)){
				System.exit(1);
			}
		}
		if(player.getDead()){
			if(retry.clicked(p)){
				player.reset();
				counter.resetCounter();
				pipes = new ArrayList<Pipe>();
				startTime = 0;
				highscore = false;
				state = 1;
			}
			if(menu.clicked(p)){
				player.reset();
				counter.resetCounter();
				pipes = new ArrayList<Pipe>();
				startTime = 0;
				highscore = false;
				state = 0;
			}
		}
	}
}
