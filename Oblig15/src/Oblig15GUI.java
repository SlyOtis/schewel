import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import twitter4j.TwitterException;

public class Oblig15GUI extends JFrame implements ActionListener{
	
	static int frame_width = 700;
	static int frame_height = 500;
	static int status_height = 20;
	static int msginput_height = 60;
	public Oblig15MsgBoard msgboard;
	private JTextArea msgtext;
	
	public Oblig15GUI(){
		super("Oblig15 - Twitter");
		this.setSize(frame_width, frame_height);
		Container cont = this.getContentPane();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel msgpane = new JPanel(new BorderLayout());
		JButton send = new JButton("Tweet");
		msgtext = new JTextArea();
		send.setPreferredSize(new Dimension(this.getWidth()/8,msginput_height));
		msgtext.setPreferredSize(new Dimension(this.getWidth()*(7/8),msginput_height));
		send.addActionListener(this);
		msgpane.add(send,BorderLayout.LINE_END);
		msgpane.add(msgtext, BorderLayout.CENTER);
		msgpane.setPreferredSize(new Dimension(this.getWidth(), msginput_height));
		cont.add(msgpane, BorderLayout.PAGE_END);
		
		msgboard = new Oblig15MsgBoard();
		cont.add(msgboard,BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(msgtext.getText().length()<140){
			msgboard.postTweet(msgtext.getText());
		}else{
			System.out.println("M� v�re under 140 tegn");
		}
		
	}
	
}
