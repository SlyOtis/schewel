import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class Brett extends JPanel{
	
	boolean hasStatusBar = false;
	StatusBar status;
	public int brett_rows, brett_colms, brett_cell_size, brett_numbers;
	GridLayout brett_layout;
	Dimension pref;
	Hashtable<String,Felt> cells = new Hashtable<String,Felt>();
	int[][] matrix;
	long seed = (System.nanoTime() % 100000);
	Random generator = new Random(seed);
	boolean first = true;
	
	Brett(int rows, int colms, int cell_size, int numbers, StatusBar statusbar){
		brett_rows = rows;
		brett_colms = colms;
		brett_cell_size = cell_size;
		status = statusbar;
		hasStatusBar = true;
		brett_numbers = numbers;
	}
	Brett(int rows, int colms, int cell_size, int numbers){ 
		brett_rows = rows;
		brett_colms = colms;
		brett_cell_size = cell_size;
		brett_numbers = numbers;
	}
	public void setLevel(int lvl){
		brett_numbers = lvl;
	}
	public void nyttspill(){
		
        print("Starter nytt spill");
        print("Resetter brettet");
        
        if(first)first = false;
        else matrix = new int[brett_rows][brett_colms];
        
        for(Felt field:cells.values()){
        	field.setText("");
        	field.setEditable(true);
        	field.isUsed(false);
        }
        
        print("Tildeler " + brett_numbers + " tall");
        int clustercount = 0;
        int clusters = brett_colms;
        int i = 0;
        
        while(i<brett_numbers){
        	if(clustercount==clusters)clustercount = 0;
        	
        	int tall = rnd(9);
    		int[] cor = getCordinates(rnd(3, 0), rnd(3, 0), clustercount);
    		int x = cor[0];
    		int y = cor[1];
    		print("Prøver med " + tall + " i " + x + ", " + y);
    		
    		if(matrix[x][y] == 0){
    			if(!nrExists(x, y, clustercount, tall)){
    				setValue(x, y, tall);
    				i += 1;
        			print((brett_numbers - i) + " tall igjen å finne");
    			}
    		}
    		
        	clustercount += 1;
        }
        print("Tall tildelt");
        print("Klar til å spille");
	}
	public void solve(){
		
		ArrayList<Felt> above = new ArrayList<Felt>();
        for(Felt field:cells.values()){
        	if(!field.isUsed()) above.add(field);
        }
        
        print("Bytter telle algorytme");
        int tall = 1;
        
        while(above.size()> 1){
        	
        	Felt curr = above.get(above.size()-1);
    		int x = curr.x;
    		int y = curr.y;
    		
    		print("Prøver med " + tall + " i " + x + ", " + y);
    		
    		if(!nrExists(x, y, getCluster(x,y), tall)){
				setValue(x, y, tall);
				above.remove(above.size()-1);
			}
    		
    		if(tall==9)tall = 1;
    		else tall += 1;
        }
	}
	public void slettbrett(){
		print("Tømmer spill brettet");
		for(Felt field:cells.values()){
			if(!field.Used){
				field.setText("");
			}
		}
		print("Spillet er tømt");
	}
	private void setValue(int x, int y, int value){
		print("Legger til tall " + value + " på " + x + ", " + y);
		matrix[x][y] = value;
		Felt celle = cells.get(x + "," + y);
		print("Updating cell value " + String.valueOf(value));
		celle.setText(String.valueOf(value));
		celle.setEditable(false);
		celle.isUsed(true); 
	}
	private boolean nrExists(int colm, int row, int cluster, int val){
		boolean exists = false;
		try{
			print("Checking rows for " + val);
			
			for(int i=0;i<(brett_rows);i++){
				if(matrix[colm][i] == val){
					print(val + " eksisterer på colonne " + colm);
					exists = true;
					return exists;
				}
			}
			
			for(int i=0;i<(brett_colms);i++){
				if(matrix[i][row] == val){
					print(val + " eksisterer på rad " + row);
					exists = true;
					return exists;
				}
			}
			
			if(!exists){
				return checkCluster(cluster, val);
			}
			
		}catch(Exception e){
			print(e.getMessage() + " error, " + colm + ", " + row);
		}
		return exists;
	}
	private int getCluster(int pos_x, int pos_y){
		print("Konverterer posisjon til kluster");
		int cluster = 0;
		if(pos_x<3){
			cluster = 0;
		}else{
			if(pos_x>6){
				cluster = 2;
			}else{
				cluster = 1;
			}
		}
		if(pos_y<3){
			return cluster;
		}else{
			if(pos_y>6){
				switch(cluster){
				case 0: return 6;
				case 1: return 7;
				case 2: return 8;
				}
			}else{
				switch(cluster){
				case 0: return 3;
				case 1: return 4;
				case 2: return 5;
				}
			}
		}
		return 0;
	}
	private int[] getCordinates(int pos_x, int pos_y, int cluster){
		
		int x = 0;
		int y = 0;
		
		switch(cluster){
			case 0: x = 0;
					y = 0;
			break;
			case 1: x = 3;
					y = 0;
			break;
			case 2: x = 6;
					y = 0;
			break;
			case 3: x = 0;
					y = 3;
			break;
			case 4: x = 3;
					y = 3;
			break;
			case 5: x = 6;
					y = 3;
			break;
			case 6: x = 0;
					y = 6;
			break;
			case 7: x = 3;
					y = 6;
			break;
			case 8: x = 6;
					y = 6;
			break;
		}
		int[] rtpos = {x + pos_x,y = y + pos_y};
		return rtpos;
	}
	private boolean checkCluster(int cluster, int value){
		
		int x = 0;
		int y = 0;
		
		print("Skjekker klynge " + cluster);
		
		switch(cluster){
			case 0: x = 0;
					y = 0;
			break;
			case 1: x = 3;
					y = 0;
			break;
			case 2: x = 6;
					y = 0;
			break;
			case 3: x = 0;
					y = 3;
			break;
			case 4: x = 3;
					y = 3;
			break;
			case 5: x = 6;
					y = 3;
			break;
			case 6: x = 0;
					y = 6;
			break;
			case 7: x = 3;
					y = 6;
			break;
			case 8: x = 6;
					y = 6;
			break;
		}
		
		for(int r = x;r<x+3;r++){
			for(int c = y;c<y+3;c++){
				if(matrix[r][c] == value){
					print(value + " finnes i klynge på " + r + ", " + c);
					return true;
				}
			}
		}
		return false;
	}
	public void build_brett(){
		
		print("Bygger brett");
		matrix = new int[brett_rows][brett_colms];
		brett_layout = new GridLayout(brett_rows,brett_colms);
		this.setLayout(brett_layout);
		this.setAlignmentX(Brett.CENTER_ALIGNMENT);
		this.setAlignmentY(Brett.CENTER_ALIGNMENT);
		pref = new Dimension(brett_rows * brett_cell_size, brett_colms * brett_cell_size);
		this.setMinimumSize(pref);
		this.setPreferredSize(pref);
		
		for(int y=0;y<(brett_rows);y++){
			for(int x=0;x<brett_colms;x++){
				Felt box = new Felt();
				box.setHorizontalAlignment(JTextField.CENTER);
				box.setFont(new Font(box.getFont().getName(), Font.BOLD, 20));
				print("Definerer textbox event");
				box.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						inputCheck(box);
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						inputCheck(box);
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						inputCheck(box);
						
					}
				});
				
				this.add(box);
				String cor = String.valueOf(x + "," + y);
				print("Celle lagt til på " + cor);
				matrix[x][y] = 0;
				box.x = x;
				box.y = y;
				cells.put(cor, box);
			}
		}
		nyttspill();
	}
	private void inputCheck(JTextField box){
		try {
			String str = box.getText().substring(1, 2);
			if(box.getDocument().getLength()> 1){
				box.setText(box.getText().substring(1, 2));
			}
			if(!Character.isDigit(str.toCharArray()[0])){
				box.setText(" ");
				print("Må være et tall...");
			}
		} catch (Exception e1) {
		}
	}
	private int rnd(int max){
		print("Henter tilfeldig tall med " + seed);
		return generator.nextInt(max) + 1;
	}
	private int rnd(int max, int min){
		print("Henter tilfeldig tall med " + seed);
		return generator.nextInt(max - min) + min;
	}
	private void print(String message){
		if(hasStatusBar){
			status.set(message);
		}else{
			System.out.println(message);
		}
	}
	private void print(int message){
		if(hasStatusBar){
			status.set(String.valueOf(message));
		}else{
			System.out.println(message);
		}
	}
	private class Felt extends JTextField{
		private boolean Used = false;
		int x;
		int y;
		public void isUsed(boolean used){
			Used = used;
		}
		public boolean isUsed(){
			return Used;
		}
	}
}

