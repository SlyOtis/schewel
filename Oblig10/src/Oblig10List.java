import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;


public class Oblig10List extends JList<String[]> {
	private String[][] data;
	public ArrayList<Oblig10Brus> brus = new ArrayList<Oblig10Brus>();
	public Oblig10CSV csv = new Oblig10CSV();
	String csv_path;
	
	@SuppressWarnings("unchecked")
	public Oblig10List(String ny_csv_path, int display_width, int display_height){
		csv_path = ny_csv_path;
		data = new String[1][4];
		lastBrus();
		this.setListData(data);
		this.setCellRenderer(new Structure());
	}
	public void addBrus(int id, String brusmerke, String URL, double[] stemmer){
		Oblig10Brus temp_brus = new Oblig10Brus(id, brusmerke, URL, stemmer);
		String[] temp_data = new String[stemmer.length + 4];
		addData(temp_brus);
		temp_data[0] = Integer.toString(temp_brus.ID);
		temp_data[1] = temp_brus.brusmerke;
		temp_data[2] = Double.toString(temp_brus.poengsum);
		temp_data[3] = Integer.toString(temp_brus.stemmer.length);
		for(int i = 0;i<temp_data.length;i++){
			temp_data[i+4] = Double.toString(stemmer[i]);
		}
		csv.addRow(temp_data);
		updateData();
	}
	public void addBrus(int id, String brusmerke, String URL){
		Oblig10Brus temp_brus = new Oblig10Brus(id, brusmerke, URL);
		addData(temp_brus);
		csv.addRow(new String[]{Integer.toString(temp_brus.ID),temp_brus.brusmerke,temp_brus.URL});
		updateData();
	}
	public void addStemme(double stemme, Oblig10Brus curr_brus){
		curr_brus.addStemme(stemme);
		data[curr_brus.ID-1][2] = Double.toString(curr_brus.poengsum);
		data[curr_brus.ID-1][3] = Integer.toString(curr_brus.stemmer.length);
		csv.addStemme(curr_brus.ID, Double.toString(curr_brus.poengsum), 
				Double.toString(stemme));
		updateData();
	}
	public void searchData(String search_string){
		try{
			String[][] ny_data = new String[0][0];
			this.setListData(ny_data);
			this.updateUI();
			int[] ny_ids = new int[brus.size()];
			int count = 0;
			for (Oblig10Brus old_brus : brus) {
				if(Integer.toString(old_brus.ID).contains(search_string.toLowerCase())){
					ny_ids[count] = old_brus.ID;
					count += 1;
				}else{
					if(old_brus.brusmerke.toLowerCase().contains(search_string.toLowerCase()))
					{
						ny_ids[count] = old_brus.ID;
						count += 1;
					}
				}
			}
			ny_data = new String[count][4];
			System.out.println(count + " matches");
			for(int i=0;i<count;i++){
				ny_data[i][0] = Integer.toString(brus.get(ny_ids[i]-1).ID);
				ny_data[i][1] = brus.get(ny_ids[i]-1).brusmerke;
				ny_data[i][2] = Double.toString(brus.get(ny_ids[i]-1).poengsum);
				ny_data[i][3] = Integer.toString(brus.get(ny_ids[i]-1).stemmer.length);
			}
			this.setListData(ny_data);
			this.updateUI();
		}catch(Exception ex){
			System.out.println("search error, nothing major");
		}
	}
	public void updateData(){
		this.setListData(data);
		this.updateUI();
		csv.writeFile(csv_path, true);
	}
	private void lastBrus(){
		csv.readFile(csv_path);
		data = new String[csv.length()][4];
		for(int i=1;i<csv.length();i++){
			String[] ny_data = csv.getRow(i);
			if(ny_data[0] != null){
				if(ny_data.length>=5){
					double[] ny_stemmer = new double[ny_data.length - 4];
					System.out.println(ny_data.length -4);
					for(int z=4;z<ny_data.length;z++){
						ny_stemmer[z-4] = Double.parseDouble(ny_data[z]);
					}
					addData(new Oblig10Brus(Integer.parseInt(ny_data[0]), ny_data[1], 
								ny_data[2], ny_stemmer));
				}else{
					addData(new Oblig10Brus(Integer.parseInt(ny_data[0]), ny_data[1], 
							ny_data[2]));
				}
			}
			else{
				System.out.println(i + " er utenfor indexen");
			}
		}
	}
	private void addData(Oblig10Brus temp_brus){
		brus.add(temp_brus);
		data = new String[brus.size()][4];
		for (int i = 0;i<brus.size();i++) {
			data[i][0] = Integer.toString(brus.get(i).ID);
			data[i][1] = brus.get(i).brusmerke;
			data[i][2] = Double.toString(brus.get(i).poengsum);
			data[i][3] = Integer.toString(brus.get(i).stemmer.length);
		}
	}
	class Structure extends JPanel implements ListCellRenderer{
		
		JLabel id, brusmerke, poengsum, stemmer;
		
		Structure() {
			setLayout(new GridLayout(1, 4));
			
			id = new JLabel();
			id.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 0));
			brusmerke= new JLabel();
			brusmerke.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 0));
			poengsum = new JLabel();
			poengsum.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 0));
			stemmer = new JLabel();
			stemmer.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 0));
			id.setOpaque(true);
			brusmerke.setOpaque(true);
			poengsum.setOpaque(true);
			stemmer.setOpaque(true);
			
			add(id);
			add(brusmerke);
			add(poengsum);
			add(stemmer);
		}
		@SuppressWarnings("rawtypes")
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			
			String n_id = ((String[])value)[0];
			String n_name = ((String[])value)[1];
			String n_poeng = ((String[])value)[2];
			String n_stemmer = ((String[])value)[3];
			
			id.setText("#" + n_id);
			brusmerke.setText(n_name);
			poengsum.setText(n_poeng + " poeng");
			if(Integer.parseInt(n_stemmer)==1)stemmer.setText(n_stemmer + " stemme");
			else stemmer.setText(n_stemmer + " stemmer");
			
			
			if(isSelected){
				
				id.setBackground(list.getSelectionBackground());
				id.setForeground(list.getSelectionForeground());
				brusmerke.setBackground(list.getSelectionBackground());
				brusmerke.setForeground(list.getSelectionForeground());
				poengsum.setBackground(list.getSelectionBackground());
				poengsum.setForeground(list.getSelectionForeground());
				stemmer.setBackground(list.getSelectionBackground());
				stemmer.setForeground(list.getSelectionForeground());
				
			}else{
				
				id.setBackground(list.getBackground());
				id.setForeground(list.getForeground());
				brusmerke.setBackground(list.getBackground());
				brusmerke.setForeground(list.getForeground());
				poengsum.setBackground(list.getBackground());
				poengsum.setForeground(list.getForeground());
				stemmer.setBackground(list.getBackground());
				stemmer.setForeground(list.getForeground());
			}
			
			this.setEnabled(list.isEnabled());
			this.setFont(list.getFont());
			return this;
		}
	}
}

