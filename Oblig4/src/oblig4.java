import javax.swing.JOptionPane;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.io.FileInputStream;

public class oblig4 {

	public static void main(String[] args) {
		Random ran = new Random();
		String guess = null;
		int i = 0;
		int  n = ran.nextInt(10);
		Boolean first = true;
		String bruker = null;
		
		while(i!=-1){
			if(first){
				bruker = JOptionPane.showInputDialog("Skriv inn brukernavn");
				JOptionPane.showMessageDialog(null, "Du må nå gjette ett tall mellom 0 og 10, \n"
						+ "gjetter du riktig får du sammenligne med tidligere highscore");
				first = false;
			}
			guess = JOptionPane.showInputDialog("(Du har gjettet " + Integer.toString(i) + " ganger) Gjett tallet");
			if(Integer.parseInt(guess) == n){
				String score = getsetscore(bruker, i);
				JOptionPane.showMessageDialog(null,"Gratulerer du gjettet riktig, du brukte " + i 
						+ " forsøk. \n Trykk ok for å se highscore");	
				JOptionPane.showMessageDialog(null, score);
				if(JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null, "Vil du spille mere?")){
					i=-1;
				}
				else{
					first = true;
					i=0;
					n = ran.nextInt(10);
				}
			}
			else{
				first = false;
				i += 1;
				if(n<Integer.parseInt(guess)) {
					if(JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null,"Riktig tall er mindre, fortsette?")) i=0;
				}
				if(n>Integer.parseInt(guess)) {
					if(JOptionPane.OK_OPTION != JOptionPane.showConfirmDialog(null,"Riktig tall er større, fortsette?")) i=0;
				}
			}
		}

	}
	public static String getsetscore(String bruker, int score)
	{
		String path = (System.getProperty("user.dir") + "/Score.inf");
		File file = new File(path);
		String content = null;
		String highscore = null;
		List<String> players = null;
		
		if(file.exists()){
			content = read() + bruker + " : " + score + "|";
			write(content);
		}
		else{
			content = bruker + " : " + score + "|";
			write(content);
		}
		
		players = Arrays.asList(content.split("|"));
		
		for(Iterator<String> i = players.iterator(); i.hasNext(); ) {	
			if(highscore!=null){
				highscore += "\n" + i.next();
			}else highscore = i.next();
		}
		
		return highscore;
	}
	private static String read(){
		String path = (System.getProperty("user.dir") + "/Score.inf");
		String content = null;
	    File file = new File(path);
	    try {
	        FileReader reader = new FileReader(file);
	        char[] chars = new char[(int) file.length()];
	        reader.read(chars);
	        content = new String(chars);
	        reader.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return content;
	}
	public static void write(String s) {
		String path = (System.getProperty("user.dir") + "/Score.inf");
		File file = new File(path);
		file.delete();
	    FileWriter output = null;
	    
	    try {
	      output = new FileWriter(file,false);
	      output.write(s);
          output.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
}
