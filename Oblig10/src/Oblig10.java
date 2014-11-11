import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Oblig10 extends JFrame implements ActionListener{
	
	JLabel display_image, display_name, display_score, rate_info, search_info, sort_info;
	JTextField rate_input, search_field;
	Oblig10List search_list;
	JButton add_button, rate_submit, open_scv;
	JComboBox<String> sort_opt;
	
	Font font_head = new Font("Verdana", Font.BOLD, 16);
	Font font_text = new Font("Verdana", Font.BOLD, 12);
	
	int image_width = 300;
	int image_height = 450;
	String[] sort_alt = {"Etter ID","Alfabetisk","Høyest score", "Lavest score","Flest stemmer"};
	String csv_path = "brus.csv";
	
	Oblig10Brus curr_brus;
	Oblig10NyBrus ny_brus;
	
	public Oblig10(){
		this.setTitle("Brusautomaten");
		this.setSize(1000, 600);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dim.getWidth() - this.getWidth()) / 2);
	    int y = (int) ((dim.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		
		initUI();
		initBrus();
	}
	private void initBrus(){
		curr_brus = search_list.brus.get(0);
		updateDisplay();
	}
	private void initUI(){
		Container cont = this.getContentPane();
		JPanel search = new JPanel(new BorderLayout());
		JPanel display = new JPanel(new BorderLayout());
		JPanel display_info = new JPanel();
		JPanel rate = new JPanel(new BorderLayout());
		JPanel search_btns = new JPanel(new GridLayout(1,2));
		JPanel search_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		search_info = new JLabel("Søk etter brus:");
		search_field = new JTextField();
		search_field.addActionListener(this);
		search_field.setPreferredSize(new Dimension(300,20));
		search_panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		search_panel.add(search_info);
		search_panel.add(search_field);
		
		search_list = new Oblig10List(csv_path, image_width, image_height);
		add_button = new JButton("Legg til ny brus");
		add_button.addActionListener(this);
		open_scv = new JButton("Åpne som scv");
		open_scv.addActionListener(this);
		search_list.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		search_list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try{
					curr_brus = search_list.brus.get(search_list.getSelectedIndex());
					updateDisplay();					
				}catch(Exception ex){
					System.out.println("update error, nothing serious");
				}
			}
		});
		search.add(search_panel,BorderLayout.PAGE_START);
		search.add(search_list, BorderLayout.CENTER);
		search_btns.add(add_button);
		search_btns.add(open_scv);
		search.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		search.add(search_btns, BorderLayout.PAGE_END);
		
		display_info.setLayout(new BoxLayout(display_info, BoxLayout.PAGE_AXIS));
		display_info.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		display_info.add(Box.createHorizontalGlue());
		display_info.setSize(this.getWidth()/3, this.getHeight());
		display_name = new JLabel("Brus navn");
		display_score = new JLabel("Score: 999");
		display_image = new JLabel();
		display_image.setSize(image_width, image_height);
		display_image.setBackground(Color.blue);
		display_image.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		display_name.setFont(font_head);
		display_score.setFont(font_text);
		display_name.setAlignmentX(CENTER_ALIGNMENT);
		display_score.setAlignmentX(CENTER_ALIGNMENT);
		display_image.setAlignmentX(CENTER_ALIGNMENT);
		display_info.add(display_name);
		display_info.add(display_score);
		display_info.add(display_image);
		
		rate.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		rate_info = new JLabel("Gi brusen en score mellom 1 og 100");
		rate_submit = new JButton("Stem");
		rate_submit.addActionListener(this);
		rate_input = new JTextField();
		rate_info.setFont(font_text);
		rate.add(rate_info,BorderLayout.NORTH);
		rate.add(rate_input,BorderLayout.CENTER);
		rate.add(rate_submit, BorderLayout.EAST);
		
		display.add(display_info, BorderLayout.CENTER);
		display.add(rate, BorderLayout.SOUTH);
		
		cont.setLayout(new BorderLayout());
		cont.add(search, BorderLayout.CENTER);
		cont.add(display, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	public void updateDisplay(){
		display_name.setText(curr_brus.brusmerke);
		display_score.setText(Double.toString(curr_brus.poengsum));
		display_image.setIcon(curr_brus.getImage(image_width, image_height));
        display_image.updateUI();
	}
	public void updateBruss(){
		
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==search_field){
			search_list.searchData(search_field.getText());
		}
		if(e.getSource()==add_button){
			ny_brus = new Oblig10NyBrus(search_list.brus.size() + 1);
			ny_brus.input_submit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean allGood = true;
					for (int i = 0; i < search_list.brus.size(); i++) {
						if(ny_brus.input_brusmerke.getText() == search_list.brus.get(i).brusmerke){
							JOptionPane.showMessageDialog(null, "Det finnes allerede en brus med det navnet");
							allGood = false;
						}
					}
					Image image = null;
			        try {
			            URL ur = new URL(ny_brus.input_url.getText());
			            image = ImageIO.read(ur);
			        } catch (IOException ex) {
			        	JOptionPane.showMessageDialog(null, "Får ikke leste bilde urlen");
			        	allGood = false;
			        }
			        if(allGood){
			        	search_list.addBrus(ny_brus.input_id, ny_brus.input_brusmerke.getText(), ny_brus.input_url.getText());
			        	ny_brus.dispose();
			        }
				}
			});
			ny_brus.toFront();
			ny_brus.setVisible(true);
		}
		if(e.getSource()==rate_submit){
			if(numberOnly(rate_input.getText())){
				double i = Double.parseDouble(rate_input.getText());
				if(i<=100&&i>0){
					search_list.addStemme(i,curr_brus);
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "må være et tall mellom 1 og 100");
				System.out.println(rate_input.getText() + " må være tall");
			}
		}
		if(e.getSource()==open_scv){
			try {
				Desktop.getDesktop().open(new File(search_list.csv_path));
			} catch (IOException ex) {
				System.out.println(ex.getMessage());				
			}
		}
	}
	public static boolean numberOnly(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
}
