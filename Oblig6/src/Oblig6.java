import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Oblig6 extends JFrame {
	public Oblig6(){
		
		super("Julekalender");
		this.setSize(350,230);
	    this.setLayout(new FlowLayout());
		
		JLabel head= new JLabel("Velkommen til årets julekalender", JLabel.CENTER);
		this.add(head);
		
		JPanel body = new JPanel();
	    body.setLayout(new GridLayout(6,4));
	    
		for(int i=1;i<=24;i++){
			int luke = i;
			JButton butt = new JButton("Luke " + i);
			butt.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(luke == 24)JOptionPane.showMessageDialog(null, "JULAFTEN");
					if(luke == 23)JOptionPane.showMessageDialog(null, "Nå er det bare " + (24-luke) +" dag igjen til jul");
					if(luke <= 22)JOptionPane.showMessageDialog(null, "Nå er det bare " + (24-luke) +" dager til jul");
				}
			});
			body.add(butt);
		}
		this.add(body);
		this.setVisible(true);
	}
}
