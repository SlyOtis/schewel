import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;
public class Eple extends Oblig9GrafikkElement {
	Random rand = new Random();
	Rectangle position;
	public Eple(int start_x, int start_y, int start_size, Color col){
		x = start_x;
		y = start_y;
		size = start_size;
		color = col;
		position = new Rectangle(x, y, size, size);
	}
	public void plasserEple(int max_x, int max_y) {

        x = (int) (rand.nextInt(max_x) +  1);
        y = (int) (rand.nextInt(max_y) +  1);
        position = new Rectangle(x, y, size, size);
    }
	public boolean sjekkKollisjon(Rectangle head){
		if(position.intersects(head)){
			System.out.println("du fikk eplet");
			return true;
		}	
		else{
			return false;
		}
	}
}
