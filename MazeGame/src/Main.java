import enigma.core.Enigma;
import java.util.Scanner;
public class Main {
	public static enigma.console.Console cn = Enigma.getConsole("NumberMaze",150,39,12,0);
	public static void main(String[] args) throws Exception {
		Menu menu =new Menu();
		menu.mainMenu(cn);
		cn.getTextWindow().setCursorPosition(62,21);
		cn.getTextWindow().output("Please enter your choice: ");
        //NumberMaze numbermaze = new NumberMaze(cn);
        Scanner scn = new Scanner(System.in);
        int choice=0;
        boolean choice_flag=true;
        while(choice_flag) {
        	choice=scn.nextInt();
        	if(choice==1 || choice==2 || choice==3) {
        		choice_flag=false;
        	}
        	else {
        		cn.getTextWindow().setCursorPosition(88,21);
        		cn.getTextWindow().output("        ");
        		cn.getTextWindow().setCursorPosition(88,21);
        	}
        }
        
        menu.menuClear(cn);
        cn.getTextWindow().setCursorPosition(0,0);
        NumberMaze numbermaze = new NumberMaze(cn,choice);
        
        
        
		

	}
	

}