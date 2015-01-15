import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JMenuBar{
	
	boolean hasStatusBar = false;
	StatusBar status;
	Brett brett;
	
	Menu(Brett nytt_brett, StatusBar statusbar){
		hasStatusBar = true;
		status = statusbar;
		brett = nytt_brett;
		build_menu();
	}
	Menu(Brett nytt_brett){build_menu();}
	private void build_menu(){
		
		JMenu menu_file = new JMenu("Fil");
		JMenu menu_level = new JMenu("Vansklighetsgrad");
		JMenuItem file_nyttspill = new JMenuItem("Nytt Spill");
		JMenuItem file_slett = new JMenuItem("Slett");
		JMenuItem level_lett = new JMenuItem("Lett");
		JMenuItem level_middels = new JMenuItem("Middels");
		JMenuItem level_vansklig = new JMenuItem("Vansklig");
		
		
		menu_file.setMnemonic(KeyEvent.VK_F);
		menu_level.setMnemonic(KeyEvent.VK_F);
		file_nyttspill.setToolTipText("Fjerner alt på brettet og starter et nytt spill");
		file_slett.setToolTipText("Resetter og tømmer brettet for å starte på nytt");
		
		file_nyttspill.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				brett.nyttspill();
		    }
		});
		file_slett.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				brett.slettbrett();
		    }
		});
		level_lett.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				brett.setLevel(35);
				brett.nyttspill();
		    }
		});
		level_middels.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				brett.setLevel(25);
				brett.nyttspill();
		    }
		});
		level_vansklig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				brett.setLevel(12);
				brett.nyttspill();
				
			}
		});
		
		menu_file.add(file_nyttspill);
		menu_file.add(file_slett);
		menu_level.add(level_lett);
		menu_level.add(level_middels);
		menu_level.add(level_vansklig);
		this.add(menu_file);
		this.add(menu_level);
	}
	private void print(String message){
		if(hasStatusBar){
			status.set(message);
		}else{
			System.out.println(message);
		}
	}
}