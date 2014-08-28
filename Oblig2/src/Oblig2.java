import javax.swing.JOptionPane;

public class Oblig2 {
	public static void main(String[] args){
		PassordDialog();	
	}
	private static void PassordDialog(){
		//Password dialog
		String passord = JOptionPane.showInputDialog("Velg et passord:");
		JOptionPane.showMessageDialog(null, "Du har tastet inn " + passord);
				
		int i = 0;
		while(i<3){
			if(JOptionPane.showInputDialog("Hva var passordet igjen? (Forsøk " + (3-i) + ")").contains(passord)){
				JOptionPane.showMessageDialog(null,"Access Granted");
				Boklaan();
			}
			else{
				JOptionPane.showMessageDialog(null,"Access Denied");
				i += 1;
			}
		}
		if(JOptionPane.showConfirmDialog(null, "Vil du fortsette til bok lån?") == JOptionPane.YES_OPTION) Boklaan();
		else PassordDialog();
	}
	private static void Boklaan(){
		//Bibliotek hvor mange uker har du lånt
		String regx = "[0-9]";
		String rented = JOptionPane.showInputDialog("Skriv antall uker boken er lånt: ");
		if(!rented.matches(regx)){
			JOptionPane.showMessageDialog(null,"Antall uker må være et tall");
			Boklaan();
		}
		else{
			int r = Integer.parseInt(rented);
			if(r>4){
				JOptionPane.showMessageDialog(null, "Lever boken tilbake");
			}
			else if(r<4){
				JOptionPane.showMessageDialog(null, "Du kan låne boken lenger");
			}
			else if(r==4){
				JOptionPane.showMessageDialog(null,"Du må levere boken tilbake i dag");
			}
		}
		if(JOptionPane.showConfirmDialog(null, "Vil du fortsette til sosiale medier?") == JOptionPane.YES_OPTION) SosialeMedier();
		else Boklaan();
	}
	private static void SosialeMedier(){
		//Hvor lenge er du på sosiale medier
		String regx = "[0-9]";
		String tid = JOptionPane.showInputDialog("Hvor lenge bruker du sosiale medier daglig? (timer)");
		if(!tid.matches(regx)){
			JOptionPane.showMessageDialog(null,"Tid må være et tall gitt i timer");
			Boklaan();
		}
		else{
			int r = Integer.parseInt(tid);
			if(r>=3){
				JOptionPane.showMessageDialog(null, "For mye");
			}
			else if(r<3){
				JOptionPane.showMessageDialog(null, "For lite");
			}
			else if(r==1){
				JOptionPane.showMessageDialog(null,"Akkurat passe");
			}
		}
		if(JOptionPane.showConfirmDialog(null, "Ønser du å avslutte?") == JOptionPane.NO_OPTION) SosialeMedier();
		else System.exit(0);
	}
}
