import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Oblig10CSV {
	private File file;
	private ArrayList<String[]> data = new ArrayList<String[]>();
	
	public void readFile(String path){
		try{
			file = new File(path);
			System.out.println(file.getAbsolutePath());
			if(file.exists() && path.endsWith(".csv")){
				BufferedReader br = new BufferedReader(new FileReader(file));
				String row;
				while((row = br.readLine())!=null){
					String[] val = new String[row.split(";").length];
					val = row.split(";");
					System.out.println(row);
					data.add(val);
				}
				br.close();
			}
			else{
				System.out.println("File does not exists, or invalid format");
			}
		}catch(IOException ex){
			System.out.println(ex.getLocalizedMessage() + " whaat?");
		}
	}
	public String getValue(int row, int cel){
		String value;
		if(row<data.size() && row>=0){
			String[] n_row = data.get(row);
			value = n_row[cel];
		}else{
			value = row + " is out of bounderies";
		}
		System.out.println(value);
		return value;
	}
	public String[] getRow(int row){
		if(row<data.size() && row>=0){
			System.out.println(data.get(row)[0] + ", " + data.get(row)[1] + " loaded");
			return data.get(row);
		}else{
			System.out.println(row + " is out of bounderies");
			return null;
		}
	}
	public void addRow(String[] input){
		data.add(input);
	}
	public void addStemme(int row, String poeng, String stemme){
		if(row<data.size() && row>=0){
			String[] new_row;
			String[] old_row = data.get(row);
			if(old_row.length<=3){
				new_row = new String[5];
				new_row[0] = old_row[0];
				new_row[1] = old_row[1];
				new_row[2] = old_row[2];
				new_row[3] = poeng;
				new_row[4] = stemme;
				data.set(row, new_row);
			}else{
				new_row = new String[old_row.length + 1];
				new_row[0] = old_row[0];
				new_row[1] = old_row[1];
				new_row[2] = old_row[2];
				new_row[3] = poeng;
				for(int i = 4;i<new_row.length - 1;i++){
					new_row[i] = old_row[i];
				}
				new_row[old_row.length] = stemme;
				data.set(row, new_row);
			}
			System.out.println(row + " row updated");
		}else{
			System.out.println(row + " is out of bounderies");
		}
	}
	public void writeFile(String path, boolean overwrite){
        try {
        	file = new File(path);			
			if(file.exists() && path.endsWith(".csv") && overwrite){
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		        bw.write(parseData());
		        bw.close();
			}else{
				System.out.println("overskriving deaktivert, eller feil filformat");
			}
        }catch(Exception ex){
        	System.out.println(ex.getMessage());
        }
	}
	private String parseData(){
		
		String value = null;
		String newline = System.getProperty("line.separator");
		
		for(int i = 0; i<data.size();i++){
			String[] values = (data.get(i));
			for(int z = 0;z<values.length;z++){
				if(z==0){
					value += values[z];
				}else{
					value += ";" + values[z];
				}
			}
			value += newline;
		}
		return value;
	}
	public int length(){
		try{
			return data.size();
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return 0;
		}
	}
}
