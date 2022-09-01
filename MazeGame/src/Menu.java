import java.awt.Color;
import enigma.console.TextAttributes;
public class Menu {
	
	public void mainMenu(enigma.console.Console cn) {
		cn.getTextWindow().setCursorPosition(64,3);
		TextAttributes nm_color=new TextAttributes(Color.CYAN, Color.black);
		cn.getTextWindow().output("N U M B E R  M A Z E",nm_color);
		cn.getTextWindow().setCursorPosition(48,5);
		for(int i=0;i<25;i++) {
			cn.getTextWindow().setCursorPosition(48,5+i);
			
				for(int j=0;j<50;j++) {	
					if(i==0 || i==24) {
						cn.getTextWindow().output('#');			
					}
					else {
						if(j==0 || j==49) {
							cn.getTextWindow().output('#');	
						}
						else {
							cn.getTextWindow().output(' ');	
						}
					}
							
				}		
		}
		TextAttributes menu_color=new TextAttributes(Color.BLUE, Color.black);
		TextAttributes easy_color=new TextAttributes(Color.YELLOW, Color.black);
		TextAttributes normal_color=new TextAttributes(Color.GREEN, Color.black);
		TextAttributes hard_color=new TextAttributes(Color.RED, Color.black);
		cn.getTextWindow().setCursorPosition(69,5);
		cn.getTextWindow().output("GAME MENU",menu_color);
		cn.getTextWindow().setCursorPosition(70,11);
		cn.getTextWindow().output("EASY(1)",easy_color);
		cn.getTextWindow().setCursorPosition(69,14);
		cn.getTextWindow().output("NORMAL(2)",normal_color);
		cn.getTextWindow().setCursorPosition(70,17);
		cn.getTextWindow().output("HARD(3)",hard_color);
		
		//cn.getTextWindow().ou
		
		
	}
	public void menuClear(enigma.console.Console cn) {
		for(int i=0;i<30;i++) {
			cn.getTextWindow().setCursorPosition(48,3+i);			
			for(int j=0;j<50;j++) {	
				cn.getTextWindow().output(' ');	
			}		
		}
	}
}