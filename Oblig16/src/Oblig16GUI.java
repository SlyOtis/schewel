import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.*;
public class Oblig16GUI extends JFrame{
	private static int width = 500;
	private static int height = 250;
	private JTextField input_data;
	private JTextField output_data;
	private Språk input_curr;
	private Språk output_curr;
	public Oblig16GUI() {
		super("Oblig 16 - Currency exchange");
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((dim.width/2)-(width/2), (dim.height/2)-(height/2), width, height);
		JPanel holder = new JPanel(new GridLayout(0,2));
		JPanel from = new JPanel();
		JButton btn = new JButton();
		btn.setAction(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				konverter();
				
			}
		});
		
		from.setLayout(new BoxLayout(from, BoxLayout.Y_AXIS));
		from.setMaximumSize(new Dimension(width/2,height));
		input_curr = new Språk(0);
		input_data = new JTextField();
		input_data.setPreferredSize(new Dimension(width/2,40));
		btn.setText("Konverter valutta");
		btn.setPreferredSize(new Dimension(width/2, 40));
		btn.setMinimumSize(new Dimension(width/2, 40));
		from.add(new JLabel("Bestem valutta å konvertere fra"));
		from.add(Box.createRigidArea(new Dimension(0, 5)));
		from.add(input_curr);
		from.add(Box.createRigidArea(new Dimension(0, 5)));
		from.add(new JLabel("Ønsket beløp å konvertere:"));
		from.add(Box.createRigidArea(new Dimension(0, 5)));
		from.add(input_data);
		
		JPanel to = new JPanel();
		to.setLayout(new BoxLayout(to, BoxLayout.Y_AXIS));
		output_curr = new Språk(1);
		output_data = new JTextField();
		output_data.setEditable(false);
		output_data.setPreferredSize(new Dimension(width/2,40));
		to.add(new JLabel("Bestem valutta å konvertere til"));
		to.add(Box.createRigidArea(new Dimension(0, 5)));
		to.add(output_curr);
		to.add(Box.createRigidArea(new Dimension(0, 5)));
		to.add(new JLabel("Konvertert beløp:"));
		to.add(Box.createRigidArea(new Dimension(0, 5)));
		to.add(output_data);		
		
		holder.add(from);
		holder.add(to);
		this.add(holder, BorderLayout.CENTER);
		this.add(btn, BorderLayout.PAGE_END);
		this.pack();
	}
	private double konverterFraEuro(double beløp){
		switch (output_curr.getSelectedIndex()){
		case 0:
			return (beløp * 1.14);
		case 1:
			return (beløp * 136.55);
		case 2:
			return (beløp * 8.78);
		case 3:
			return (beløp * 1);
		}
		return beløp;
	}
	private double konverterTilEuro(double beløp){
		switch (input_curr.getSelectedIndex()){
		case 0:
			return (beløp / 1.14);
		case 1:
			return (beløp / 136.55);
		case 2:
			return (beløp / 8.78);
		case 3:
			return (beløp / 1);
		}
		return beløp;
	}
	private void konverter(){
		try{
			double beløp = Double.parseDouble(input_data.getText());
			output_data.setText(String.valueOf(konverterFraEuro(konverterTilEuro(beløp))));
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Må være et tall");
			input_data.setText("");
		}
	}
	class Språk extends JComboBox<String>{
		Språk(int standard){
			DefaultComboBoxModel<String> currency = 
					new DefaultComboBoxModel<String>(new String[]{"Dollar", "Yen", "Kroner", "Euro"});
			this.setModel((ComboBoxModel<String>) currency);
			this.setSelectedIndex(standard);
		}
	}
}
