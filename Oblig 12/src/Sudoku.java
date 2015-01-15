import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class Sudoku extends JFrame {
	
	int ruter_x = 9;
	int ruter_y = 9;
	int ruter_size = 50;
	int ruter_tall = 40;
	int statusbar_height = 20;
	
	Sudoku(){
		this.setTitle("Oblig 12 - Sudoku");
		this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
		Container frame = this.getContentPane();
		StatusBar status = new StatusBar(statusbar_height);
		Brett brett = new Brett(ruter_x,ruter_y,ruter_size, ruter_tall, status);
		JMenuBar menu = new Menu(brett,status);
		
		Dimension pref = new Dimension((ruter_x * ruter_size), (ruter_y * ruter_size) + 
				statusbar_height + menu.getHeight());
		
		status.set("Initializing window at " + (ruter_x * ruter_size) + ", " + ((ruter_y * ruter_size) + 
				statusbar_height + menu.getHeight()));
		
		this.setMinimumSize(pref);
		this.setSize(pref);
		this.setPreferredSize(pref);
		
		frame.add(brett, BorderLayout.CENTER);
		frame.add(menu, BorderLayout.NORTH);
		frame.add(status, BorderLayout.SOUTH);
		
		brett.build_brett();
		status.set("Klar til å spille");
	}
}
