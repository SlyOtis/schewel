import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import java.lang.Math;

public class Oblig7 extends JFrame {
	
	ArrayList<Punkt> punkter = new ArrayList<Punkt>();
	int[] pol_x;
	int[] pol_y;
	
	Date date= new Date();
	Random rg = new Random();
    int type = -1;
	
	public Oblig7() throws InterruptedException{
        this.setBackground(Color.WHITE);
		this.setTitle("Oblig7");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				punkter.add(new Punkt(new Point(e.getX(),e.getY())));
            	repaint(e.getX(),e.getY(),10,10);	
			}
        });
		
        this.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
            	punkter.add(new Punkt(new Point(e.getX(),e.getY())));
            	repaint(e.getX(),e.getY(),10,10);	
            }
        });
        
        for(int i = 0;i<=3;i++){
			punkter.add(new Punkt(rndPoint(0,this.getWidth(),0,this.getHeight())));
		}
        
		while(true){	
			
			Thread.sleep(10000);
			type+=1;
			
			switch(type){
				case 0: {
					JOptionPane.showMessageDialog(null, "Finner nermeste punkt og \n trekker linje mellom dem");
					break;
				}
				case 1:{
					JOptionPane.showMessageDialog(null, "Trekker polygon linje mellom alle punktene");
					break;
				}
				case 2:{
					JOptionPane.showMessageDialog(null, "Finner nermeste punkt og reiser\n dit, så videre til neste nermeste, TPS?");
					break;
				}
			}
			
			int min = 10000;
			int index = -1;
			
			if(type!=2){
				
				pol_x = new int[punkter.size()+1];
				pol_y = new int[punkter.size()+1];
				
				for(int i=0;i<punkter.size();i++){
					
					Punkt pnt = punkter.get(i);
					pol_y[i] = punkter.get(i).point.y;
					pol_x[i] = punkter.get(i).point.x;
						for(int s=0;s<punkter.size();s++){
			        		if(s!=i){
			        			int dis = disPoint(pnt.point,punkter.get(s).point);
				        		if(min>dis){
				        			min = dis;
					        		index = s;
				        		}
			        		}
			        	}
		        	pnt.index = index;
		        	min = 1000000;
		        	index = -1;
		        	repaint();
				}	
			}
			else{
				
				Boolean TPS = true;
				Punkt start = punkter.get(random(punkter.size()-2,0));
				Punkt pnt = start;
				start.zero = true;
				
				while(TPS){
					if(closest(pnt)!=null){
						Punkt next_pnt = closest(pnt);
						pnt.next_point = next_pnt.point;
						pnt.line = true;
						pnt = next_pnt;	
					}
					else{
						pnt.next_point = start.point;
						pnt.line = true;
						TPS = false;
					}
					min = 1000000;
					index = -1;
				}
				repaint();
			}
		}
	}
	public void paint(Graphics g){
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(random(7,3)));
	    
		for(int i=0;i<=punkter.size()-1;i++){
			Point pkt = punkter.get(i).point;
			g2.fillOval(pkt.x, pkt.y, 10,10);
			g2.setColor(new Color(random(255,0), random(255,0), random(255,0)));
		}
		
		switch(type){
			case 0:{
				for(int i=0;i<punkter.size();i++){
					Punkt pnt = punkter.get(i);
					g2.drawLine(pnt.point.x, pnt.point.y, punkter.get(pnt.index).point.x, punkter.get(pnt.index).point.y);
	        	}
				break;
			}
			case 1:{
				g2.drawPolygon(pol_x, pol_y, pol_y.length);
				break;
			}
			case 2:{
				for(int i=0;i<punkter.size();i++){
					Punkt pnt = punkter.get(i);
					if(pnt.zero)g2.drawString("Salesman start",pnt.point.x , pnt.point.y);
					g2.drawLine(pnt.point.x, pnt.point.y, pnt.next_point.x, pnt.next_point.y);
	        	}
				break;
			}
		}

	}
	
	public int random(int upper, int lower){
		return rg.nextInt(upper) + lower;
	}
	
	public Point rndPoint(int start_x,int end_x,int start_y,int end_y){
		int x = random(end_x,start_x);
		int y = random(end_y,start_y);
		return new Point(x, y);
	}
	
	public int disPoint(Point a, Point b){
		
		double x = Math.max(a.x, b.x)-Math.min(a.x, b.x);
		double y = Math.max(a.y, b.y)-Math.min(a.y, b.y);
		
		x = x*x;
		y = y*y;
		
		return (int) Math.sqrt(x+y);
	}
	public Punkt closest(Punkt a){
		int min = 100000;
		int index = -1;
		
		for(int s=0;s<punkter.size();s++){
    		if(punkter.get(s)!=a){
    			if(!punkter.get(s).line){
	    			int dis = disPoint(a.point,punkter.get(s).point);
	        		if(min>dis){
	        			min = dis;
	        			index = s;
	        		}
    			}
    		}
    	}
		if(index!=-1)return punkter.get(index);
		else return null;	
	}
}
class Punkt{
	public Point point;
	public Point next_point;
	public int index = -1;
	public Boolean line = false;
	public Boolean zero = false;
	public Punkt(Point pnt){
		point = pnt;
	}
}
