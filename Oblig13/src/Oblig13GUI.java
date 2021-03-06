import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileDescriptor;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class Oblig13GUI extends JFrame implements ActionListener {
	
	static int frame_width = 700;
	static int frame_height = 500;
	static int status_height = 20;
	
	Oblig13StatusBar status = new Oblig13StatusBar(status_height);
	Oblig13Lager list = new Oblig13Lager(status);
	
	Oblig13GUI(){
		this.setSize(frame_width, frame_height);
		this.setLayout(new BorderLayout());
		Container cont = new Container();
		cont.setLayout(new BoxLayout(cont, BoxLayout.X_AXIS));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton save = new JButton("Lagre");
		JButton open = new JButton("�pne");
		JButton add = new JButton("Ny Vare");
		JButton remove = new JButton("Selg");
		JButton inf = new JButton("Ny Liste");
		
		save.setActionCommand("save");
		open.setActionCommand("open");
		add.setActionCommand("add");
		remove.setActionCommand("remove");
		inf.setActionCommand("liste");
		save.addActionListener(this);
		open.addActionListener(this);
		add.addActionListener(this);
		remove.addActionListener(this);
		inf.addActionListener(this);
		
		cont.add(save);
		cont.add(open);
		cont.add(add);
		cont.add(remove);
		cont.add(inf);
		this.add(cont, BorderLayout.PAGE_START);
		this.add(list, BorderLayout.CENTER);
		this.add(status, BorderLayout.PAGE_END);
		
		this.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getActionCommand()){
			case "open": list.openList(); break;
			case "save": list.saveList(); break;
			case "add": list.addItem(); break;
			case "remove": list.removeItem(); break;
			case "liste": list.newList();				
		}
	}
}
