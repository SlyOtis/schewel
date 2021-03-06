import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Oblig13Lager extends Container implements ListSelectionListener{
	
	private Oblig13StatusBar status;
	private Oblig13Model model = new Oblig13Model();
	private boolean loaded = true;
	private JList list = new JList<String>(model);
	private int selected;
	
	Oblig13Lager(Oblig13StatusBar statusbar){
		status = statusbar;
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		this.setLayout(new BorderLayout());
		this.add(list, BorderLayout.CENTER);
		if(new File("butikk1.lager").exists())loadDefaultList();
		else loaded = false;
	}
	public void updateList(){
		
	}
	public void saveList(){
		if(loaded){
			try {
				String path = model.path + "/" + model.name + ".lager";
				File f = new File(path);
				if(f.exists())f.delete(); 
				FileOutputStream f_out = new FileOutputStream(model.path + "/" + model.name + ".lager");
				ObjectOutputStream obj_out = new ObjectOutputStream (f_out);
				obj_out.writeObject(model);
				status.set(model.name + " lagret til " + model.path);
				f_out.close();
				obj_out.close();
			} catch (IOException e) {
				status.set("Feil ved lagring", e);
			}
		}else{
			status.set("Ingen liste lastet, s� kan ikke lagre");
		}
	}
	public void openList(){
		status.set("�pner lager");
		try{
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				FileInputStream f_in = new 	FileInputStream(fc.getSelectedFile().getAbsolutePath());
				ObjectInputStream obj_in = 	new ObjectInputStream (f_in);
				model = (Oblig13Model) obj_in.readObject();
				status.set(model.name + " �pnet");
				f_in.close();
				obj_in.close();
				updateList();
				loaded = true;
			}else{
				status.set("�pning av liste avbrutt");
			}
		}catch(IOException | ClassNotFoundException e){
			status.set("Feil ved henting av liste", e);
		}
	}
	public void addItem(){
		if(loaded){
			try{
				String navn = "Redline Carbon Classic Cold", 
						produsent = "Madshus", type = "Ski", spesifik, antall = "1";
				String[] args = {"110cm","78kg"};
				JOptionPane.showMessageDialog(null, "Det vil n� komme 4 dialogbokser for � legge til en ny vare. \n"
						+ "Viktig � ha samsvarende info for at listen skal oppdatere seg. \n"
						+ "- Navn p� varen \n - Produsent av varen \n - Varegruppe \n - Spesifikasjoner \n - Antall av denne eksakte varen");
				navn = JOptionPane.showInputDialog("Skriv inn navn p� varen du skal legge til. \n (Ski blir lagt til om tom)");
				if(navn.length()<1){
					System.out.println(navn);
					navn = "Redline Carbon Classic Cold";
					produsent = "Madshus";
					type = "Ski";
				}else{
					produsent = JOptionPane.showInputDialog("Skriv inn navn p� produsenten av varen. \n (Madshus blir lagt til om tom)");
					if(produsent.length()<1)produsent = "Madshus";
					type = JOptionPane.showInputDialog("Skriv hvilken varegruppe tilh�rer den nye varen. \n (Ski blir lagt til om tom)");
					if(type.length()<1) type = "Ski";
					spesifik = JOptionPane.showInputDialog("Spesifikasjoner for varen. (Adskilt med ',')");
					if(spesifik.length()>1)args = spesifik.split(",");
					antall = JOptionPane.showInputDialog("Skrive in antall varer av denne typen. (1 om tom)");
					if(antall.length()<1) antall = "1";
				}
				model.addElement(new Oblig13Vare(produsent,type,navn,args,antall));
				status.set(antall + " " + navn  + " " + type.toLowerCase() + " fra " + produsent + " lagt til" );
				updateList();
			}catch(Exception e){
				status.set("Kunne ikke legge til vare",e);
			}
		}else{
			status.set("Ingen liste valgt, for � lage ny liste trykk ny liste");
		}
	}
	public void removeItem(){
		if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Sikker p� at du vil selge ?")){
			model.remove(selected);
		}
	}
	public void newList(){
		try{
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			if(JFileChooser.APPROVE_OPTION == fc.showSaveDialog(this)){
				String navn = JOptionPane.showInputDialog("Navn p� liste. (butikk1 om tom)");
				if(navn.isEmpty()) navn = "butikk1";
				model = new Oblig13Model(fc.getSelectedFile().getAbsolutePath(), navn);
				status.set("Listen " + navn + " ble opprettet");
				loaded = true;
				saveList();
			}else{
				status.set("Ny liste avbrutt");
			}
		}catch(Exception e){
			status.set(e);
		}
		
	}
	public void loadDefaultList(){
		status.set("Laster standard lager");
		try{
			FileInputStream f_in = new 	FileInputStream("butikk1.lager");
			ObjectInputStream obj_in = 	new ObjectInputStream (f_in);
			model = (Oblig13Model) obj_in.readObject();
			status.set(model.name + " �pnet");
		}catch(IOException | ClassNotFoundException e){
			status.set("Feil ved henting av liste", e);
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		selected = arg0.getFirstIndex();
		status.set("Valgt vare: " + arg0.toString());
	}
	public class Oblig13Vare implements Serializable{
		public String[] args;
		public String produsent, type, navn, antall;
		@Override
		public String toString(){
			return navn;
		}
		Oblig13Vare(String _produsent,String _type, String _navn, String[] _args, String _antall){
			produsent = _produsent;
			type = _type;
			navn = _navn;
			args = _args;
			antall = _antall;
		}
	}
	class Oblig13Model extends DefaultListModel<String> implements Serializable{
		String path, name;
		ArrayList<Oblig13Vare> varer;
		Oblig13Model(){
			varer = new ArrayList<Oblig13Lager.Oblig13Vare>();
		}
		Oblig13Model(String _path, String _name){
			path = _path;
			name = _name;
			varer = new ArrayList<Oblig13Lager.Oblig13Vare>();
		}
		public void addElement(Oblig13Vare vare){
			this.addElement(vare.produsent + " " + vare.navn 
					+ "(" + vare.antall + ")" + vare.type);
		}
	}
}
