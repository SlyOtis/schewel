import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Oblig10Brus {
	public double poengsum;
	public String brusmerke;
	public double[] stemmer;
	public int ID;
	public ImageIcon brus_bilde;
	public String URL;
	
	public Oblig10Brus(int ny_id, String ny_brusmerke, String ny_url, double[] ny_stemmer){
		System.out.println("Ny brus: " + ny_id + ", " + ny_brusmerke + ", " + ny_url);
		stemmer = ny_stemmer;
		brusmerke = ny_brusmerke;
		URL = ny_url;
		ID = ny_id;
		poengsum = regnPoengsum();
	}
	public Oblig10Brus(int ny_id,String ny_brusmerke, String ny_url){
		System.out.println("Ny brus: " + ny_id + ", " + ny_brusmerke + ", " + ny_url);
		poengsum = 0;
		brusmerke = ny_brusmerke;
		URL = ny_url;
		ID = ny_id;
		stemmer = new double[0];
	}
	public ImageIcon getImage(int bilde_bredde, int bilde_høyde){
		try {
            URL url = new URL(URL);
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(bilde_bredde, bilde_høyde,  java.awt.Image.SCALE_SMOOTH));
            return icon;
         } catch (Exception e) {
        	 System.out.println("Ingen internet tilgang, eller feil på bilde URLen");
        	 return null;
         } 
	}
	private double regnPoengsum(){
		double sum = 0;
		for(int i = 0;i<stemmer.length;i++){
			sum += stemmer[i];
		}
		return (sum/(stemmer.length));
	}
	public void addStemme(double ny_stemme){
		if(stemmer.length <= 0){
			System.out.println("no votes so far");
			stemmer = new double[1];
			stemmer[0] = ny_stemme;
			poengsum = regnPoengsum();
		}else{
			double[] old_stemmer = stemmer;
			stemmer = new double[stemmer.length + 1];
			for(int i = 0;i<stemmer.length;i++){
				stemmer[i] = ny_stemme;
			}
			poengsum = regnPoengsum();
		}
	}
}
