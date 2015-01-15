import java.awt.CardLayout;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
public class Translater implements DocumentListener {
	
	int maximum_words = 50;
	int maximum_languages = 4;
	
	//ArrayList Languages = new ArrayList();
	public ArrayList<String> lang1, lang2;
	Document output;
	org.w3c.dom.Document document;
	File xml;
	public Boolean changed = false;
	
	int NrofWords = 0;
	Translater(Document output_document, ArrayList<String> from_language, ArrayList<String> to_language){
		output = output_document;
		lang1 = from_language;
		lang2 = to_language;
	}
	public void ChangeLanguage(ArrayList<String> from_language, ArrayList<String> to_language){
		lang1 = from_language;
		lang2 = to_language;
	}
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		JOptionPane.showMessageDialog(null, arg0);	
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		try {
			Document doc = arg0.getDocument();
			String text = doc.getText(0, doc.getLength());
			
			if(text.endsWith(" ")){
				String[] ch = text.split(" ");
				String word = ch[ch.length -1];
				if(word.contains("\n")){
					ch = word.split("\n");
					word = ch[ch.length - 1];
				}
				if(word.length()>0){
					word = translate(word);
					if(output.getLength() != 0){
						output.insertString(output.getLength(), " " + word, null);
					}else{
						output.insertString(0, word, null);
					}
				}
			}else{
				if(text.endsWith("\n")){
					String[] ch = text.split("\n");
					String word = ch[ch.length -1];
					if(word.contains(" ")){
						ch = word.split(" ");
						word = ch[ch.length - 1].trim();
					}
					if(word.length()>0){
						word = translate(word);
						if(output.getLength() != 0){
							output.insertString(output.getLength(), " " + word, null);
						}else{
							output.insertString(0, word, null);
						}
					}
				}
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage() + " error");
			e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		
	}
	public String translate(String word){
		System.out.println("Translating " + word);
		try{
			String last = word;
			if(lang1.contains(word)){
				for(int i = 0;i<lang1.size();i++){
					if(lang1.get(i).contains(word)){
						System.out.println(word + " match found");
						last = lang2.get(i);
						break;
					}
				}
			}
			else{
				try{
					String result = JOptionPane.showInputDialog("Add a translation for " + word);
					lang1.add(word);
					lang2.add(result);
					changed = true;
					last = result;
					System.out.println(word + " - " + result + " translation added");
				}catch(Exception e){
					System.out.println("translation stoped");
				}
			}
			return last;
		}catch(Exception e){
			System.out.println(e.getMessage() + " error");
			return word;
		}
	}
}
