import javax.swing.JOptionPane;

import org.omg.CORBA.Environment;
public class Oblig3Runner {
	
	static Oblig3Funksjoner funk = new Oblig3Funksjoner();
	
	public static void main(String[] args){
		String nok = JOptionPane.showInputDialog("Skriv beløpet du ønsker å konvertere");
		if(funk.isnumber(nok)){
			String[] Val = funk.VeksleNok(Integer.parseInt(nok));
			JOptionPane.showMessageDialog(null, "Nok " + nok + "\n" + "Eur  " + Val[0]
					+ "\n" + "Yen " + Val[1]);
		}
		else{
			JOptionPane.showMessageDialog(null, "Beløpet må være et tall");
		}
		Advanced();
	}
	private static void Advanced(){
		
		Object[] Valutta = {"Euro","NOK","Yen"};
		int FraKurs = JOptionPane.showOptionDialog(null, "Velg valutta å convertere fra", "Advanced Converter", 1, 1, 
				null, Valutta, null);
		switch(FraKurs){
			case 0: Valutta = new Object[] {"NOK", "Yen"};
					break;
			case 1: Valutta = new Object[] {"Euro", "Yen"};
					break;
			case 2: Valutta = new Object[] {"Euro", "Nok"};
			
		}
		int TilKurs = JOptionPane.showOptionDialog(null, "Velg valutta å convertere til", "Advanced Converter", 1, 1, 
				null, Valutta, null);
		String val = JOptionPane.showInputDialog("Skriv beløpet du ønsker å konvertere");
		if(funk.isnumber(val)){
			String[] Veksel = funk.Veksle(Integer.parseInt(val),FraKurs,TilKurs);
			JOptionPane.showMessageDialog(null, Veksel[0] + "\n" + Veksel[1]);
		}
		else{
			JOptionPane.showMessageDialog(null, "Beløpet må være et tall");
		}
	}
}
