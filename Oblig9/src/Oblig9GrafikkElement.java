import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Oblig9GrafikkElement extends JFrame {
	int x,y,size;
	Color color;
	
	public void drawMe(Graphics g){
		g.setColor(color);
		g.fillRect(x, y, size,size);
	}
}
