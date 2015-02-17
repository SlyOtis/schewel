import javax.swing.JOptionPane;
import org.junit.Test;
public class Oblig16Test {

	private int from_curr = 2;
	private int to_curr = 1;
	private double bel�p;
	private int testbel�p = 100;
	
	@Test
	public void konverterFraEuro(){
		switch (from_curr){
		case 0:
			bel�p = (testbel�p * 1.14);
		case 1:
			bel�p = (testbel�p * 136.55);
		case 2:
			bel�p = (testbel�p * 8.78);
		case 3:
			bel�p = (testbel�p * 1);
		}
	}
	@Test
	public void konverterTilEuro(){
		switch (to_curr){
		case 0:
			bel�p = (testbel�p / 1.14);
		case 1:
			bel�p = (testbel�p / 136.55);
		case 2:
			bel�p = (testbel�p / 8.78);
		case 3:
			bel�p = (testbel�p / 1);
		}
	}
	@Test
	public void konverter(){
		try{
			konverterTilEuro();
			konverterFraEuro();
			System.out.println("Konvertert valutta: " + bel�p);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"M� v�re et tall");
		}
	}
}
