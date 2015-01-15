import java.awt.Dimension;
import javax.swing.JLabel;

public class StatusBar extends JLabel{
	public StatusBar(int height) {
	    super();
	    super.setPreferredSize(new Dimension(super.getWidth(), height));
	    set("Ready");
	}
	public void set(String message) {
		setText(message);
		System.out.println(message);
	}
}
