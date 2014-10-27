import javax.imageio.ImageIO;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class Oblig8 extends JFrame{
	
	static ArrayList<Image> sprites = new ArrayList<Image>();
	
	Boolean bot = true;
	Boolean doGame = false;
	int player1 = 0;
	int player2 = 0;
	int points1 = 0;
	int points2 = 0;
	String currPlay = null;
	
	JButton btn = new JButton();
	JLabel score = new JLabel("0 / 0");
	JPanel arena = new JPanel();
	JLabel h1 = new JLabel();
	JLabel h2 = new JLabel();
	String message;
	
	public Oblig8(){
		this.setTitle("Rock, Paper, Scissors");
		this.setBackground(new Color(255,255,255));
		this.setResizable(false);
		this.setSize(650, 500);
		this.setVisible(true);
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contPane = this.getContentPane();
		contPane.setLayout(new BoxLayout(contPane, BoxLayout.Y_AXIS));
		
		JPanel top = new JPanel(new BorderLayout());
		top.setSize(650,100);
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
		top.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		JLabel player = new JLabel("Player 1");
		JLabel oponent = new JLabel("Data");
		
		player.setMaximumSize(new Dimension(216,50));
		player.setAlignmentX(JLabel.LEFT_ALIGNMENT);
	    player.setHorizontalAlignment(JLabel.LEFT);
	    
	    score.setMaximumSize(new Dimension(200,50));
		score.setAlignmentX(JLabel.CENTER_ALIGNMENT);
	    score.setHorizontalAlignment(JLabel.CENTER);
	    
	    oponent.setMaximumSize(new Dimension(216,50));
	    oponent.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
	    oponent.setHorizontalAlignment(JLabel.RIGHT);
	    
		top.add(player);
		top.add(score);
		top.add(oponent);
		contPane.add(top);
		
		arena.setLayout(new BorderLayout());
		arena.setSize(650, 350);
		arena.setBackground(new Color(255,255,255));
		arena.setVisible(true);
		h1.setMaximumSize(new Dimension(325,350));
		h2.setMaximumSize(new Dimension(325,350));
		arena.add(h1,BorderLayout.WEST);
		arena.add(h2,BorderLayout.EAST);
		contPane.add(arena);
		
		currPlay = "Player 1";
		
		JPanel buttom = new JPanel();
		buttom.setLayout(new BorderLayout());
		buttom.setMaximumSize(new Dimension(650,50));
		btn.setText(currPlay + " velg trekk");
		btn.addActionListener(new eventHandler());
		btn.setMaximumSize(new Dimension(650,50));
		buttom.add(btn,BorderLayout.SOUTH);
		contPane.add(buttom);
		
		if(JOptionPane.showOptionDialog(null, "Velg motstander", "Velg hvem du kjemper mot", JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Spiller", "Data"}, "Data") == 0){
			bot=false;
			oponent.setText("Player 2");
		}
		
		File[] files = new File("./sprites").listFiles();
		try {
		    for(int i = 0;i<files.length;i++){
		    	sprites.add(ImageIO.read(files[i]));
		    }
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	public void paintHands(){
		Image hand1 = null, hand2 = null;
		switch(player1){
			case 0: hand1 = sprites.get(2);
				switch(player2){
					case 0: results(Wins.UAVGJORT);
							hand2 = sprites.get(3);
					break;
					case 1: results(Wins.PLAYER1);
							hand2 = sprites.get(5);
					break;
					case 2: results(Wins.PLAYER2);
							hand2 = sprites.get(1);
					break;
				}
			break;
			case 1: hand1 = sprites.get(4);
				switch(player2){
				case 0: results(Wins.PLAYER2);
						hand2 = sprites.get(3);
				break;
				case 1: results(Wins.UAVGJORT);
						hand2 = sprites.get(5);
				break;
				case 2: results(Wins.PLAYER1);
						hand2 = sprites.get(1);
				break;
			}
			break;
			case 2: hand1 = sprites.get(0);
				switch(player2){
				case 0: results(Wins.PLAYER1);
						hand2 = sprites.get(3);
				break;
				case 1: results(Wins.PLAYER2);
						hand2 = sprites.get(5);
				break;
				case 2: results(Wins.UAVGJORT);
						hand2 = sprites.get(1);
				break;
			}
			break;
		}
		h1.setIcon(new ImageIcon(hand1));
		h2.setIcon(new ImageIcon(hand2));
		arena.validate();
		JOptionPane.showMessageDialog(null, message);
		btn.setEnabled(true);
	}
	enum Wins{UAVGJORT,PLAYER1,PLAYER2};
	public void results(Wins res){
		if(res==Wins.UAVGJORT){
			message = "Uavgjort!";
		}
		if(res==Wins.PLAYER1){
			message = "Gratulerer Player 1 du vant!";
			points1 +=1;
			score.setText(points1 + " / " + points2);
		}
		if(res==Wins.PLAYER2){
			message = "Gratulerer Player 2 du vant!";
			points2 +=1;
			score.setText(points1 + " / " + points2);
		}
	}
	
	class eventHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int n = JOptionPane.showOptionDialog(null, "Velg trekk",  currPlay + " velg trekk", JOptionPane.YES_NO_OPTION, 
					JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Stein", "Saks", "Papir"}, "Data");
			if(currPlay == "Player 1"){
				if(bot)btn.setEnabled(false);
				else{
					currPlay = "Player 2";
					btn.setText(currPlay + " velg trekk");
				}
				player1 = n;
			}
			else{
				currPlay = "Player 1";
				btn.setText(currPlay + " velg trekk");
				player2 = n;
				doGame = true;
			}
			
			if(bot){
				Random rand = new Random();
				player2 = rand.nextInt(2) + 0;
				paintHands();
			}
			else{
				if(doGame){
					paintHands();
					doGame = false;
				}
			}
		}
		
	}
	class playerUI extends JPanel{
		CardLayout gl2 = new CardLayout();
		JButton btn = new JButton();
		JLabel playerName = new JLabel();
		
		public playerUI(String name){
			this.setVisible(true);
			this.setSize(300, 100);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			playerName.setText(name);
			btn.setText("Spill");
			btn.addActionListener(new eventHandler());
			this.add(playerName);
			this.add(btn);
	}
}
}

