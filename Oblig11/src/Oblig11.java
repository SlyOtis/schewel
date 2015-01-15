import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

public class Oblig11 extends JFrame {
	
	int font_size = 25;
	int NrofWords = 0;
	JComboBox<String> lang1;
	JComboBox<String> lang2;
	JLabel info;
	JLabel info2;
	Translater later;
	
	Oblig11()
	  {
	    super("Translater Oblig11");
	    this.setPreferredSize(new Dimension(650, 400));
	    this.setResizable(false);
	    setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    Container cont = this.getContentPane();
	    SpringLayout layout = new SpringLayout();
	    cont.setLayout(layout);
	    JTextArea input = new JTextArea();
		JLabel info = new JLabel();
		JLabel info2 = new JLabel();
		JTextArea output = new JTextArea();
		lang1 = new JComboBox<String>();
		lang2 = new JComboBox<String>();
		
		LastOrdlister();
		
		info2.setFont(new Font("Serif", Font.PLAIN, font_size));
		info.setFont(new Font("Serif", Font.PLAIN, font_size));
		output.setEditable(false);
		output.setLineWrap(true);
	    output.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		output.setPreferredSize(new Dimension(300,300));
		input.setLineWrap(true);
	    input.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
	    input.setPreferredSize(new Dimension(300,300));
		lang2.setSelectedIndex(2);
		lang1.setSelectedIndex(1);
		info.setText(String.valueOf(lang1.getSelectedItem()));
		info2.setText(String.valueOf(lang2.getSelectedItem()));
		
		later = new Translater(output.getDocument(),
				Loadlanguage((String)lang1.getSelectedItem()),Loadlanguage((String)lang2.getSelectedItem()));
		input.getDocument().addDocumentListener(later);
		addWindowListener(new WindowAdapter(){
			@Override
            public void windowClosing(WindowEvent e){
                UpdateOrdlister();
            }
        });
		lang1.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String selected = String.valueOf(e.getItem());
				info.setText(selected);
				later.ChangeLanguage(Loadlanguage(selected), Loadlanguage(String.valueOf(lang2.getSelectedItem())));
			}
		});
		lang2.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String selected = String.valueOf(e.getItem());
				info2.setText(selected);
				later.ChangeLanguage(Loadlanguage(String.valueOf(lang1.getSelectedItem())),Loadlanguage(selected));
			}
		});
		
		cont.add(info);
		cont.add(info2);
		cont.add(input);
		cont.add(output);
		cont.add(lang1);
		cont.add(lang2);
		
		layout.putConstraint(SpringLayout.WEST, info,20,SpringLayout.WEST, cont);
		layout.putConstraint(SpringLayout.NORTH, info,10,SpringLayout.NORTH, cont);
		layout.putConstraint(SpringLayout.NORTH, input,10,SpringLayout.SOUTH, info);
		layout.putConstraint(SpringLayout.WEST, input,10,SpringLayout.WEST, cont);
		layout.putConstraint(SpringLayout.EAST, info2,-20,SpringLayout.EAST, cont);
		layout.putConstraint(SpringLayout.NORTH, info2,10,SpringLayout.NORTH, cont);
		layout.putConstraint(SpringLayout.NORTH, output,10,SpringLayout.SOUTH, info);
		layout.putConstraint(SpringLayout.EAST, output,-10,SpringLayout.EAST, cont);
		layout.putConstraint(SpringLayout.WEST, lang1,10,SpringLayout.EAST, info);
		layout.putConstraint(SpringLayout.NORTH, lang1,15,SpringLayout.NORTH, cont);
		layout.putConstraint(SpringLayout.EAST, lang2,-10,SpringLayout.WEST, info2);
		layout.putConstraint(SpringLayout.NORTH, lang2,15,SpringLayout.NORTH, cont);
		
		
		this.pack();
		this.setVisible(true);
	  }
	public void LastOrdlister(){
		File folder = new File("Ordlister");
		File[] Ordlister = folder.listFiles();

		for (File Ordliste : Ordlister) {
		    if (Ordliste.isFile()) {
		        lang1.addItem(stripExtension(Ordliste.getName()));
		        lang2.addItem(stripExtension(Ordliste.getName()));
		    }
		}
	}
	public ArrayList<String> Loadlanguage(String language){
		try{
			File location = new File("Ordlister/" + language + ".xml");
			ArrayList<String> temp = new ArrayList<String>();
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			org.w3c.dom.Document document = documentBuilder.parse(location);
			NodeList lang_ch = ((org.w3c.dom.Document) document).getElementsByTagName("language").item(0).getChildNodes();
			for(int i = 1;i<lang_ch.getLength();i+=2){
				temp.add(lang_ch.item(i).getTextContent());
			}
			NrofWords = temp.size();
			System.out.println("Language " + location.getName() + " loaded");
			return temp;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
	}
	static String stripExtension (String str) {
        if (str == null) return null;
        int pos = str.lastIndexOf(".");
        if (pos == -1) return str;
        return str.substring(0, pos);
    }
	public void UpdateOrdlister(){
		if(later.changed){
		try {	
			File location1 = new File("Ordlister/" + String.valueOf(lang1.getSelectedItem()) + ".xml");
			File location2 = new File("Ordlister/" + String.valueOf(lang1.getSelectedItem()) + ".xml");
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
			org.w3c.dom.Document document1 = documentBuilder.parse(location1);
			org.w3c.dom.Document document2 = documentBuilder.parse(location2);
			
			System.out.println("Modifying " + String.valueOf(lang1.getSelectedItem()) + " & " + 
			String.valueOf(lang2.getSelectedItem()));
			
			for(int i = NrofWords;i<later.lang1.size();i++){
				
				String word = later.lang1.get(i);
				String trans = later.lang2.get(i);
				
				System.out.println(word + " - " + trans + " new word translation");
				((org.w3c.dom.Document) document1).getElementsByTagName("language").item(0).appendChild(
						createNode(document1, word));
				((org.w3c.dom.Document) document2).getElementsByTagName("language").item(0).appendChild(
						createNode(document2, trans));
				System.out.println(word + " added to " + String.valueOf(lang1.getSelectedItem()));
				System.out.println(trans + " added to " + String.valueOf(lang2.getSelectedItem()));
			}
			
			SaveXML(location1,document1);
			SaveXML(location2,document2);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage() + " error");
		}
		}else{ System.out.println("no new words to save");}
	}
	public Node createNode(org.w3c.dom.Document document, String value){
		Node nde = document.createElement("word");
		nde.setTextContent(value);
		return nde;
	}
	private void SaveXML(File location, org.w3c.dom.Document document){
		try{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult streamResult =  new StreamResult(location);
			transformer.transform(source, streamResult);
			System.out.println(location.getName() + " updated");
		}
		catch (TransformerException e) {
		  System.out.println(e.getMessage() + " error");
		}
	}
}
