import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Oblig10NyBrus extends JFrame {
	
	public JTextField input_brusmerke = new JTextField();
	public JTextField input_url = new JTextField();
	public int input_id;
	public JButton input_submit;
	
	public Oblig10NyBrus(int ny_id){
		this.setTitle("Legg til ny brus");
		this.setSize(300, 150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dim.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		
		input_id = ny_id;
		
		Container cont = this.getContentPane();
		cont.setLayout(new BorderLayout());
		JPanel inputs = new JPanel(new GridLayout(4,2));
		inputs.add(new JLabel("ID:"));
		inputs.add(new JLabel(Integer.toString(input_id)));
		inputs.add(new JLabel("Brus merke:"));
		inputs.add(input_brusmerke);
		inputs.add(new JLabel("Bilde URL:"));
		inputs.add(input_url);
		inputs.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		input_submit = new JButton("Legg til brus");
		cont.add(inputs, BorderLayout.CENTER);
		cont.add(input_submit, BorderLayout.PAGE_END);
	}
	
}
