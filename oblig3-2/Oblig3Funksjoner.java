import java.sql.Array;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;
public class Oblig3Funksjoner {
	
	public boolean isnumber(String str){
		
		boolean error = false;
		
		for(int i=0; i < str.length(); i++){
			if(!Character.isDigit(str.charAt(i)))
			{
				error = true;
			}
		}
		
		if(error) return false;
		else return true; 
	}
	public String[] VeksleNok(int nok){
		DecimalFormat format = new DecimalFormat("0.00"); 
		String Eur = format.format(nok*0.12);
		String Yen = format.format(nok*17);
		String[] Result = {Eur,Yen};
		return Result;
	}
	public String[] Veksle(int val, int frakurs, int tilkurs){
		
		DecimalFormat format = new DecimalFormat("0.00");
		
		String from;
		String to;
		double[] Kurs = {0.12,1,17};
		String[] Ben = {"Eur", "Nok", "Yen"};
		
		String sum = format.format((val / Kurs[frakurs])*Kurs[tilkurs]);
		from = Ben[frakurs] + " " + val;
		to =  Ben[tilkurs] + " " + sum;
		
		String[] Result = {from,to};
		return Result;
	}
}
