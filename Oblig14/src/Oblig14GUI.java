import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Oblig14GUI extends JFrame implements ActionListener {
	
	public static int width = 700;
	public static int height = 500;
	private JList<String> list1;
	private JList<String> list2;
	private JList<String> list3;
	
	public Oblig14GUI() {
		this.setTitle("Oblig 14 Lotto");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - width) / 2);
	    int y = (int) ((dimension.getHeight() - height) / 2);
	    this.setBounds(x, y, width, height);
	    this.setLayout(new BorderLayout());
	    JButton btn = new JButton("Generer tall");
	    btn.addActionListener(this);
	    this.add(btn, BorderLayout.NORTH);
	    Container cont = new Container();
	    cont.setLayout(new GridBagLayout());
	    GridBagConstraints c = new  GridBagConstraints();
	    c.fill = GridBagConstraints.VERTICAL;
	    c.gridx = 0;
	    c.gridy = GridBagConstraints.RELATIVE;
	    c.gridheight = 2;
	    c.gridheight = 3;
	    c.ipadx = 20;
	    c.ipady = 20;
	    list1 = new JList<String>();
	    cont.add(new JLabel("Valgte Tall" , SwingConstants.CENTER),c);
	    cont.add(list1, c);
	    c.gridx = 1;
	    list2 = new JList<String>();
	    cont.add(new JLabel("Unike Tall" , SwingConstants.CENTER),c);
	    cont.add(list2, c);
	    c.gridx = 2;
	    list3 = new JList<String>();
	    cont.add(new JLabel("Sorterte Tall" , SwingConstants.CENTER),c);
	    cont.add(list3, c);
	    this.add(cont, BorderLayout.CENTER);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Random number = new Random();
		Integer[] numbers = new Integer[10];
		for (int i = 0; i < 10; i++) {
		  int n = number.nextInt(21);
		  System.out.println(n);
		  numbers[i] = n;
		}	
		UpdateGUI(numbers);
	}
	private void UpdateGUI(Integer[] tall){
		DefaultListModel<String> m = new  DefaultListModel<String>();
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(Integer s: tall){
			m.addElement(String.valueOf(s));
			if(!list.contains(s))list.add(s);
		}
		list1.setModel(m);
		m = new  DefaultListModel<String>();
		for(Integer s: list){
			m.addElement(String.valueOf(s));
		}
		list2.setModel(m);
		Collections.sort(list);
		m = new  DefaultListModel<String>();
		for(Integer s: list){
			m.addElement(String.valueOf(s));
		}
		list3.setModel(m);
	}
}
