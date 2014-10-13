
public class Oblig5 {
	static Game game = new Game();
	static Character bg = new Character();
	static Character hero = new Character();
	static Character girl = new Character();
	
	public static void main(String[] args){
		bg.setImage("http://www.itsartmag.com/features/itsart/wp-content/uploads/2013/06/destiny-26.jpg");
		bg.resize(1.7);
		game.addCharacter(bg, "background");
		
		
		hero.setImage("http://static.cargurus.com/images/site/2010/10/28/20/59/2011_volvo_s40-pic-4980867574327064935.png");
		hero.x = 0;
		hero.y = 650;
		hero.resize(0.7);
		game.addCharacter(hero, "hero");	
		
		girl.setImage("http://mortengoodwin.net/mario/princess.jpg");
		girl.x = 1500;
		girl.y = 350;
		girl.resize(0.1);
		game.addCharacter(girl, "princess");
				
	}
}
