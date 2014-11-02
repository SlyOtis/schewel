import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Oblig9ScoreBoard extends JPanel {
	
	int points;
	private long lastTick = 0L;
	JLabel label = new JLabel("Score");
	
	public Oblig9ScoreBoard(Color sb_color, int size){
		
		this.setSize(1000, (int)(size*1.2));
		this.setBackground(sb_color);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(10, 200, 10, 200));
		
		label.setFont(new Font("Serif", Font.PLAIN, size));
		label.setForeground(Color.white);
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label, BorderLayout.NORTH);
	}
	public void points(){
		long now = System.currentTimeMillis();
        long diff = now - lastTick;
        lastTick = now;
        points += (int) Math.round(1000000000/diff);
        
        System.out.println("gained points " + points );
        label.setText("Score: " + points);
	}
}
