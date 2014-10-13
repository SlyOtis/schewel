package oblig1;

import javax.swing.JOptionPane;

public class Martin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// telefon.start();
		System.out.println("Dette skal komme til skjermen");
		JOptionPane.showMessageDialog(null, "Dette skal komme som en dialogbox");
		System.out.println("En ny linje som kommer til skjermen");
		System.out.println("En siste linje som kommer til skjermen");
		System.out.println("En aller siste linje som kommer til skjermen");
		
		String _name = JOptionPane.showInputDialog("Hva heter du?");
		JOptionPane.showMessageDialog(null, "det var et fint navn " + _name);
	}

}
