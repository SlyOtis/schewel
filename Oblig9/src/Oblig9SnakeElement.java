import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
public class Oblig9SnakeElement extends Oblig9GrafikkElement {
	
	Rectangle[] ledd = new Rectangle[999];
	int ledd_count = 1;
	
	public Oblig9SnakeElement(int start_x, int start_y, int start_size, Color col){
		x = start_x;
		y = start_y;
		size = start_size;
		color = col;
		ledd[0] = new Rectangle(x, y, size, size);
	}
	public void nytt_ledd(){
		if(ledd_count<1){
			ledd[0] = new Rectangle(ledd[0].x-size, ledd[0].y+size, size, size);
		}
		else{
			ledd[ledd_count] = new Rectangle(ledd[0].x-size, ledd[0].y+size, size, size);
		}
		ledd_count += 1;
	}
	public void flytt(){
		for (int i = ledd_count-1; i > 0; i--) {
        	System.out.println(i + " got new location");
            ledd[i].x = ledd[i-1].x;
            ledd[i].y = ledd[i-1].y;
        }
	}
	public boolean sjekkKollisjon(){
		boolean kollisjon = false;
		for (int i = ledd_count-1; i > 0; i--) {
			if(ledd[0].intersects(new Rectangle(ledd[i].x,ledd[i].y,size,size))){
				System.out.println(i + " kolliderte");
				kollisjon = true;
			}
        }
		return kollisjon;
	}
	public void drawAll(Graphics g){
		g.setColor(color);
		for (int i = 0; i < ledd_count; i++) {
			if(i==0){
				System.out.println("Painting ledd " + i);
		        g.fillRect(x, y, size, size);
		        ledd[0].x = x;
		        ledd[0].y = y;
			}else{
				System.out.println("Painting ledd " + i);
		        g.fillRect(ledd[i].x, ledd[i].y, size, size);
			}
	    }
	}
}
