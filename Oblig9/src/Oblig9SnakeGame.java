import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class Oblig9SnakeGame extends JFrame implements ActionListener{
	
	Color se_color = new Color(5,25,28);
	Color bk_color = new Color(255,255,255);
	Color sb_color = new Color(5,55,25);
	Color ep_color = new Color(255,1,25);
	
	boolean rnd_color = false;
	boolean in_game = true;
	
	int speed = 1;
	int start_x = 470;
	int start_y = 470;
	int se_size = 20;
	int sb_size = 16;
	int increas = 3;
	
	public directions currDir = directions.UP;
	Timer timer;
	JPanel arena = new JPanel();
	
	Oblig9SnakeElement se;
	Eple ep;
	Oblig9ScoreBoard sb;
	enum directions {LEFT,RIGHT,UP,DOWN};
	Graphics2D g;
	
	public Oblig9SnakeGame(){
		initWindow();
        initGame();
	}
	private void initGame(){
		
		se = new Oblig9SnakeElement(start_x,start_y, se_size, se_color);
		ep = new Eple(start_x-se_size,start_y + 100,se_size,ep_color);
		
		g = (Graphics2D)arena.getGraphics();
		super.paint(g);
		
		timer = new Timer(500/speed, this);
		timer.start();
		
		drawAll();
	}
	private void initWindow(){
		
		this.setSize(1000, 1000);
		this.setTitle("Slange spillet");
		this.setBackground(bk_color);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		arena.setBackground(bk_color);
		
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dim.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(new keyListener());
		
		Container contPane = this.getContentPane();
		sb = new Oblig9ScoreBoard(sb_color, sb_size);
		contPane.add(arena, BorderLayout.CENTER);
		contPane.add(sb, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	private void drawAll(){
		g.clearRect(0,0, arena.getWidth(), arena.getHeight());
		if(in_game){
			se.drawAll(g);
			ep.drawMe(g);
		}else{
			endGame(g);
		}
	}
	
	private void flytt() {
        switch(currDir){
		case LEFT:
			se.x -= se_size;
			break;
		case UP:
			se.y -= se_size;
			break;
		case RIGHT:
			se.x += se_size;
			break;
		case DOWN:
			se.y += se_size;
			break;
		}
        se.flytt();
    }
	private void endGame(Graphics g) {
        
        String msg = "Game Over";
        Font font = new Font("Helvetica", Font.BOLD, 40);
        FontRenderContext frc = new FontRenderContext(new AffineTransform(),true,true);
        g.setColor(Color.BLACK);
        g.setFont(font);
        String str = "Gratulerer med " + sb.points + " poeng";
        g.drawString(str,(int) ((arena.getWidth()/2) - (font.getStringBounds(str, frc).getWidth()/2)),300);
    }
	
	private class keyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
			case KeyEvent.VK_LEFT:
				if(currDir!=directions.RIGHT){
					currDir = directions.LEFT;
					System.out.println("snudde mot venstre");
					drawAll();
				}
				break;
			case KeyEvent.VK_UP:
				if(currDir!=directions.DOWN){
					currDir = directions.UP;
					System.out.println("snudde oppover");
					drawAll();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(currDir!=directions.LEFT){
					currDir = directions.RIGHT;
					System.out.println("snudde mot høyre");
					drawAll();
				}
				break;
			case KeyEvent.VK_DOWN:
				currDir = directions.DOWN;
				if(currDir!=directions.UP){
					System.out.println("snudde nedover");
					drawAll();
				}
				break;
			}
        }
	 }
	int counter = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		if (in_game) {
			if(se.sjekkKollisjon()){
				in_game = false;
			}
			if(ep.sjekkKollisjon(se.ledd[0])){
				ep.plasserEple(arena.getWidth(), arena.getHeight());
				se.nytt_ledd();
				sb.points();
				counter += 1;
				if(increas == counter){
					speed += 1;
					timer.setDelay(500/speed);
					counter = 0;
				}
			}
			if(se.x>arena.getWidth()||se.x<0){
				in_game = false;
			}
			if(se.y>arena.getHeight()||se.y<0){
				in_game = false;
			}
			if(!in_game) {
	            timer.stop();
	        }
            flytt();
            drawAll();
	    }
	}
}
