import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Oblig13StatusBar extends JLabel{
	public Oblig13StatusBar(int height) {
	    super.setPreferredSize(new Dimension(super.getWidth(), height));
	    super.setFont(new Font(super.getFont().getFontName(), Font.PLAIN, (height - 8)));
	    this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
	    set("Ready");
	}
	public void set(String message) {
		setText(message);
		System.out.println(message);
	}
	public void set(String message, Exception e){
		String output = message + ". Error: " + e.getMessage();
		setText(output);
		System.out.println(output);
	}
	public void set(Exception e){
		String output = "Error: " + e.getMessage();
		setText(output);
		System.out.println(output);
	}
}
