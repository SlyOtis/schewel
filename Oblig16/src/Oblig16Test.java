import javax.swing.JOptionPane;
import org.junit.Test;
public class Oblig16Test {

	private int from_curr = 2;
	private int to_curr = 1;
	private double beløp;
	private int testbeløp = 100;
	
	@Test
	public void konverterFraEuro(){
		switch (from_curr){
		case 0:
			beløp = (testbeløp * 1.14);
		case 1:
			beløp = (testbeløp * 136.55);
		case 2:
			beløp = (testbeløp * 8.78);
		case 3:
			beløp = (testbeløp * 1);
		}
	}
	@Test
	public void konverterTilEuro(){
		switch (to_curr){
		case 0:
			beløp = (testbeløp / 1.14);
		case 1:
			beløp = (testbeløp / 136.55);
		case 2:
			beløp = (testbeløp / 8.78);
		case 3:
			beløp = (testbeløp / 1);
		}
	}
	@Test
	public void konverter(){
		try{
			konverterTilEuro();
			konverterFraEuro();
			System.out.println("Konvertert valutta: " + beløp);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Må være et tall");
		}
	}
}
