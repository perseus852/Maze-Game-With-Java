import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import enigma.console.TextAttributes;
import enigma.console.TextWindowNotAvailableException;

import java.awt.Color;
public class NumberMaze {
	   public static TextMouseListener tmlis; 
	   public static KeyListener klis; 
	   public static TextAttributes att1 = new TextAttributes(Color.BLUE, Color.black);
	   
	   public static int mousepr;          // mouse pressed?
	   public static int mousex, mousey;   // mouse text coords.
	   public static int keypr;   // key pressed?
	   public static int rkey;    // key   (for press/release)   
	   // ----------------------------------------------------
	    public static Stack leftbackpack=new Stack(8);
		public static Stack rightbackpack=new Stack(8);
		public static Stack templeft=new Stack(8);
		public static Stack tempright=new Stack(8);
		public static Queue inputque=new Queue(10000);
	   public NumberMaze(enigma.console.Console cn,int choice) throws Exception {
		
		      tmlis=new TextMouseListener() {
		         public void mouseClicked(TextMouseEvent arg0) {}
		         public void mousePressed(TextMouseEvent arg0) {
		            if(mousepr==0) {
		               mousepr=1;
		               mousex=arg0.getX();
		               mousey=arg0.getY();
		            }
		         }
		         public void mouseReleased(TextMouseEvent arg0) {}
		      };
		      cn.getTextWindow().addTextMouseListener(tmlis);
		    
		      klis=new KeyListener() {
		         public void keyTyped(KeyEvent e) {}
		         public void keyPressed(KeyEvent e) {
		            if(keypr==0) {
		               keypr=1;
		               rkey=e.getKeyCode();
		            }
		         }
		         public void keyReleased(KeyEvent e) {}
		      };
		      cn.getTextWindow().addKeyListener(klis);
		      // ----------------------------------------------------
		      File file= new File("maze.txt");
		      BufferedReader buffer = new BufferedReader(new FileReader(file));
			  String str;
			  String[] strarr=new String[23];
			  int q=0;
	        while((str=buffer.readLine())!=null) { //printing maze
	      	  cn.getTextWindow().output(str+"\n");
	      	  strarr[q]=str;
	      	  q++;
	      	  
	        }
	        buffer.close();
	        int[][]mazemap=new int[23][55];
	        for(int i=0;i<23;i++) {
	        	for(int j=0;j<55;j++) {
	        		if(strarr[i].charAt(j)=='#') { //assigning mazemap array,walls are represented by 2,spaces by 0
	        			mazemap[i][j]=-2;
	        		}
	        		else {
	        			mazemap[i][j]=0;
	        		}
	        	}
	        }
	        int leftbp_x=59;
	        int leftbp_y=14;
	        int rightbp_x=65;
	        int rightbp_y=14;
	        int score=0;
	        Number human_number= new Number(5);
	        int[] human_location=human_number.randomLocation(human_number,mazemap,cn,att1); //placing human number
	        int px=human_location[1],py=human_location[0];
	        mazemap[py][px]=1; //human number is represented by 1
	        int second=0;
	        long s=0;
	        Number[] numbers= new Number[1000];
	        int number_counter=0;
	        boolean bp_flag=false;
	        boolean game_flag=true;
	        boolean one_flag=false;
	        boolean hnm_flag; //human number move
	        long s1=0;
			boolean pf_bp_flag=false;
	        while(game_flag) {
	        	hnm_flag=false;
	        	if(s==0) {
	        		elementsQueue(cn,choice);
	        		for(int i=0;i<25;i++) { //printing the first 25 numbers on the screen
	        			Object objnum=inputque.Dequeue();
						Number numque=new Number((int)objnum);
						numbers[number_counter]=numque;
						int[] computer_location=numque.randomLocation(numque, mazemap, cn, numque.getColor());
						numque.setLocation_x(computer_location[1]); numque.setLocation_y(computer_location[0]);
						number_counter++;
						elementsQueue(cn,choice);
	        		}
	        	}
					
	        	 if(s%50==0) { //printing second
	        		 cn.getTextWindow().setCursorPosition(67,22);	        		 
	        		 cn.getTextWindow().output(Integer.toString((int)second));
	        		 second++;	        		 
	        	 }
	        	 s++;
	        	 boolean inserttime=false;
	        	 if(choice==1) { //setting the inserttime according to the user choice(easy/normal/hard)
	        		 
	        		 if(s%400==0) {
	        			 inserttime=true;
	        		 }
	        	 }
	        	 else if(choice==2) {
	        		 
	        		 if(s%250==0) {
	        			 inserttime=true;
	        		 }
	        	 }
	        	 else {
	        		 
	        		 if(s%150==0) {
	        			 inserttime=true;
	        		 }
	        	 }
				if(inserttime) {//inserting the numbers which is in the input(easy=8second,normal=5second,hard=3second)
					if(s!=0) {
						Object objnum=inputque.Dequeue();
						Number numque=new Number((int)objnum);
						numbers[number_counter]=numque;
						int[] computer_location=numque.randomLocation(numque, mazemap, cn, numque.getColor());
						numque.setLocation_x(computer_location[1]); numque.setLocation_y(computer_location[0]);
						number_counter++;
					}
						
					elementsQueue(cn,choice);
				}
				for(int i=0;i<numbers.length;i++) {//pathfinding of pf numbers printed on the screen only the first time
       			 if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Red")) &&numbers[i].isPf_firsttime()) {
       				 numbers[i].pathFinding(cn,numbers[i],human_number,mazemap);
       				 
       			 }
				}
				if(s%20==0) {//random moving for yellow numbers
					for(int i=0;i<numbers.length;i++) {
						if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Yellow")) ) { 
							numbers[i].randomMoving(cn, numbers[i],mazemap,numbers,leftbackpack,leftbp_y,human_number);
							
						}
					}
				}
				boolean backpack_flag=false;
				if(s%20==0) {//deleting yellow numbers which is captured by the human number
					for(int i=0;i<numbers.length;i++) {
						if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Yellow")) ) { 
							backpack_flag=numbers[i].isBackpack_flag();
							if(backpack_flag==true) {	//adding backpack							
								numbers[i]=null;
								break;
							}
						}
					}					
				}
				if(s%20==0) {
					for(int i=0;i<numbers.length;i++) {//if yellow number capture the human number,game is over
						if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Yellow")) ) { 
							game_flag=numbers[i].isNum_game_flag();
							if(game_flag==false) {								
								break;
							}
						}
					}					
				}
				int counter=0;
				if(s-s1==200 && one_flag==true) {//when the human number return 1,after 4 second it will turn to 2
					one_flag=false;
					human_number.addingHumannum(1);
					cn.getTextWindow().output(px,py,Integer.toString((int) human_number.getNumber()).charAt(0),att1);
					
				}
	        	 if(keypr==1) {    //moving of human number and backpack elements
	 	            if(rkey==KeyEvent.VK_LEFT && px>1 &&mazemap[py][px-1]!=-2 && one_flag==false) { 
	 	            	hnm_flag=true;
	 	            	mazemap[py][px]=0;	 	    
	 	            	cn.getTextWindow().output(px,py,' ');
	 	            	px--; 
	 	            	if(mazemap[py][px]==-1||mazemap[py][px]==-3) {
	 	            		for(int i=0;i<numbers.length;i++) {
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()<=human_number.getNumber()) {
	 	            				if(leftbackpack.isFull()) { //adding left bp
	 	            					leftbackpack.pop();
	 	            					leftbp_y++;
	 	            				}
	 	            				leftbackpack.push(numbers[i].getNumber());
	 	            				counter++;
	 	            				numbers[i]=null; //deleting the number which is captured by the humannumber
	 	            				
	 	            			}
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()>human_number.getNumber()) {
	 	            				game_flag=false; //if other number bigger than the human number,game is over
	 	            			}
	 	            		}
	 	            	}
	 	            	mazemap[py][px]=1;
	 	            	cn.getTextWindow().output(px,py,Integer.toString((int) human_number.getNumber()).charAt(0),att1);
	 	            	
	 	            }
	 	            if(rkey==KeyEvent.VK_RIGHT && px<53 &&mazemap[py][px+1]!=-2 && one_flag==false) { //same operations with vk_left
	 	            	hnm_flag=true;
	 	            	mazemap[py][px]=0;
	 	            	cn.getTextWindow().output(px,py,' ');
	 	            	px++;
	 	            	if(mazemap[py][px]==-1||mazemap[py][px]==-3) {
	 	            		for(int i=0;i<numbers.length;i++) {
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()<=human_number.getNumber()) {
	 	            				if(leftbackpack.isFull()) {
	 	            					leftbackpack.pop();
	 	            					leftbp_y++;
	 	            				}
	 	            				leftbackpack.push(numbers[i].getNumber());
	 	            				counter++;
	 	            				numbers[i]=null;
	 	            				
	 	            			}
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()>human_number.getNumber()) {
	 	            				game_flag=false;
	 	            			}
	 	            		}
	 	            	}
	 	            	mazemap[py][px]=1;
	 	            	cn.getTextWindow().output(px,py,Integer.toString((int) human_number.getNumber()).charAt(0),att1);
	 	            }
	 	            if(rkey==KeyEvent.VK_UP && py>1 &&mazemap[py-1][px]!=-2 && one_flag==false) {
	 	            	hnm_flag=true;
	 	            	mazemap[py][px]=0;
	 	            	cn.getTextWindow().output(px,py,' ');
	 	            	py--;
	 	            	if(mazemap[py][px]==-1||mazemap[py][px]==-3) {
	 	            		for(int i=0;i<numbers.length;i++) {
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()<=human_number.getNumber()) {
	 	            				if(leftbackpack.isFull()) {
	 	            					leftbackpack.pop();
	 	            					leftbp_y++;
	 	            				}
	 	            				leftbackpack.push(numbers[i].getNumber());
	 	            				counter++;
	 	            				numbers[i]=null;
	 	            				
	 	            			}
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()>human_number.getNumber()) {
	 	            				game_flag=false;
	 	            			}
	 	            		}
	 	            	}
	 	            	mazemap[py][px]=1;
	 	            	cn.getTextWindow().output(px,py,Integer.toString((int) human_number.getNumber()).charAt(0),att1);
	 	            }
	 	            if(rkey==KeyEvent.VK_DOWN && py<21 &&mazemap[py+1][px]!=-2 && one_flag==false) {
	 	            	hnm_flag=true;
	 	            	mazemap[py][px]=0;
	 	            	cn.getTextWindow().output(px,py,' ');
	 	            	py++;
	 	            	if(mazemap[py][px]==-1||mazemap[py][px]==-3) {
	 	            		for(int i=0;i<numbers.length;i++) {
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()<=human_number.getNumber()) {
	 	            				if(leftbackpack.isFull()) {
	 	            					leftbackpack.pop();
	 	            					leftbp_y++;
	 	            				}
	 	            				leftbackpack.push(numbers[i].getNumber());
	 	            				counter++;
	 	            				numbers[i]=null;
	 	            				
	 	            			}
	 	            			if(numbers[i]!=null && numbers[i].getLocation_x()==px && numbers[i].getLocation_y()==py && numbers[i].getNumber()>human_number.getNumber()) {
	 	            				game_flag=false;
	 	            			}
	 	            		}
	 	            	}
	 	            	mazemap[py][px]=1;
	 	            	cn.getTextWindow().output(px,py,Integer.toString((int) human_number.getNumber()).charAt(0),att1);
	 	            }    
	 	            if(rkey==KeyEvent.VK_W &&!leftbackpack.isEmpty() &&!rightbackpack.isFull()) {//moving of backpack elements
	 	            	Object obj2=leftbackpack.pop();
	 	            	rightbackpack.push(obj2);
	 	            	leftbp_y++;
	 	            	cn.getTextWindow().output(leftbp_x,leftbp_y,' ');
	 	            	cn.getTextWindow().output(rightbp_x,rightbp_y,Integer.toString((int) obj2).charAt(0));
	 	            	rightbp_y--;
	 	            }
	 	            if(rkey==KeyEvent.VK_Q &&!rightbackpack.isEmpty() &&!leftbackpack.isFull()) {
	 	            	Object obj3=rightbackpack.pop();
	 	            	leftbackpack.push(obj3);
	 	            	rightbp_y++;
	 	            	cn.getTextWindow().output(rightbp_x,rightbp_y,' ');
	 	            	cn.getTextWindow().output(leftbp_x,leftbp_y,Integer.toString((int) obj3).charAt(0));
	 	            	leftbp_y--;
	 	            }
	 	            
	 	           keypr=0; 
	        	 }
	        	 if(backpack_flag==true || counter==1 || bp_flag==true || pf_bp_flag==true) { //when a new number is added to the leftbackpack,leftbackpack is reprinted
	        		 leftbp_y=14;
	        		 for(int i=0;i<8;i++) {
	        			 cn.getTextWindow().output(leftbp_x,leftbp_y,' ');
	        			 leftbp_y--;
	        		 }
	 	            	leftbp_y=14;
	 	            	while(!leftbackpack.isEmpty()) {
		 		        	templeft.push(leftbackpack.pop());
		 		        	
		 		        }
		 	           while(!templeft.isEmpty()) {
		 		        	Object obj=templeft.pop();
		 		        	cn.getTextWindow().output(leftbp_x,leftbp_y,Integer.toString((int) obj).charAt(0));
		 		        	leftbackpack.push(obj);
		 		        	leftbp_y--;
		 		     
		 		        }		 	           
	 	            }
	        	 if(bp_flag==true) {//when a new number is added to the right backpack,right backpack is reprinted
	        		 rightbp_y=14;
	        		 for(int i=0;i<8;i++) {
	        			 cn.getTextWindow().output(rightbp_x,rightbp_y,' ');
	        			 rightbp_y--;
	        		 }
	        		 rightbp_y=14;
	        		 while(!rightbackpack.isEmpty()) {
	        			 tempright.push(rightbackpack.pop());
	        		 }
	        		 while(!tempright.isEmpty()) {
	        			 Object objrbp=tempright.pop();
	        			 cn.getTextWindow().output(rightbp_x,rightbp_y,Integer.toString((int) objrbp).charAt(0));
	        			 rightbackpack.push(objrbp);
	        			 rightbp_y--;
	        		 }
	        	 }
	        		 while(!leftbackpack.isEmpty()) {
	        			 templeft.push(leftbackpack.pop());
	        		 }
	        		 while(!rightbackpack.isEmpty()) {
	        			 tempright.push(rightbackpack.pop());
	        		 }
	        		 rightbp_y=14;
	        		 leftbp_y=14;
	        		 bp_flag=false;
	        		 while(!tempright.isEmpty() && !templeft.isEmpty()) {
	        			 if(tempright.peek()==templeft.peek()) {//controlling same numbers on the same level and score calculating
	        				 int factor;
	        				 if((int)tempright.peek()<4) {
	        					 factor=1;
	        				 }
	        				 else if((int)tempright.peek()<7) {
	        					 factor=5;
	        				 }
	        				 else {
	        					 factor=25;
	        				 }
	        				 score+=(factor*(int)tempright.peek());
    						 tempright.pop();
    						 templeft.pop();
    						 cn.getTextWindow().output(leftbp_x,leftbp_y,' ');
    						 cn.getTextWindow().output(rightbp_x,rightbp_y,' ');
    						 bp_flag=true;
    						 human_number.addingHumannum(1);
    						 if(human_number.getNumber()==1) {//controlling the human number return to 1
    							 one_flag=true;
    							 s1=s;
    						 }
    						 cn.getTextWindow().output(px,py,Integer.toString((int) human_number.getNumber()).charAt(0),att1);
    					 }
	        			 else {
	        				 if(!tempright.isEmpty()) {
	        					 rightbackpack.push(tempright.pop());
	        					 rightbp_y--;
	        				 }
	        				 if(!templeft.isEmpty()) {
	        					 leftbackpack.push(templeft.pop());
	        					 leftbp_y--;
	        				 }
	        				 
	        			 }
	        					 
	        		 }     		 
		        	 while(!templeft.isEmpty()) {
		        		 leftbackpack.push(templeft.pop());
		        		 leftbp_y--;
		        	 }
		        	 while(!tempright.isEmpty()) {
		        		 rightbackpack.push(tempright.pop());
		        		 rightbp_y--;
		        	 }		        	 	        	 
	        	 cn.getTextWindow().setCursorPosition(67,20);	        		 
        		 cn.getTextWindow().output(Integer.toString((int)score));        		 	
	        		 if(hnm_flag) {//when the human number move, all path of pf numbers recalculate, all points are deleted
	        			 for(int ww=0;ww<23;ww++) {
 	        	        	for(int j=0;j<55;j++) {
 	        	        		if(mazemap[ww][j]==0) {
 	        	        			cn.getTextWindow().output(j,ww,' ');
 	        	        		}
 	        	        	}
 	        		     }
	        		 }
	        		 
	        		 
	        		 for(int i=0;i<numbers.length;i++) {
	        			 if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Red"))) {
	        				 if(!hnm_flag) {//if human number doesnt move, the yellow number which is crossed the path is checked
	        					 
	        					 Stack tempstackforx= new Stack(2000);
		        				 Stack tempstackfory= new Stack(2000);
	        					 tempstackforx= numbers[i].getStack_x();
		        				 tempstackfory= numbers[i].getStack_y();
		        				 Stack tempstackforx_d=new Stack(2000);
		        				 Stack tempstackfory_d=new Stack(2000);
		        				 boolean numberonpath=false;
		        				 
		        				 while(tempstackforx.size()>1) {	
		        					 Object ty=tempstackfory.pop();
		        					 Object tx=tempstackforx.pop();
		        					 tempstackfory_d.push(ty); tempstackforx_d.push(tx);
		        					 if(mazemap[(int)ty][(int)tx]!=0 &&mazemap[(int)ty][(int)tx]!=1) {
		        						 numberonpath=true; //checking the number which is on the path
		        					 }
		        				 }
		        				 
		        				 if(tempstackforx.size()==1) {
	        						 tempstackforx_d.push(tempstackforx.pop());
	        						 tempstackfory_d.push(tempstackfory.pop());
	        					 }
		        				 while(!tempstackforx_d.isEmpty()) {
		        					 tempstackforx.push(tempstackforx_d.pop());
		        					 tempstackfory.push(tempstackfory_d.pop());
		        				 }
		        				 
		        				 numbers[i].setStack_x(tempstackforx);
		        				 numbers[i].setStack_y(tempstackfory);
		        				 if(numberonpath) {	
		        					 
		        					 while(!tempstackforx.isEmpty()) {	
		        						 Object ty1=tempstackfory.pop();
		        						 Object tx1=tempstackforx.pop();
		        						 if(mazemap[(int)ty1][(int)tx1]==0) //deleting the path
		        							 cn.getTextWindow().output((int)tx1,(int)ty1,' ');		        						 
			        					 
			        				 }
		        					 numbers[i].pathFinding(cn,numbers[i],human_number,mazemap); //calculating the new path
		        					 
		        				 }
	        				 }
	        				 else {		        					 
	        					 numbers[i].pathFinding(cn,numbers[i],human_number,mazemap);
	        					 
	        				 }
	        				 
	        			 }
	        		 }
	        		 if(game_flag==false) {
	        			 break;
	        		 }
	        		 
        			 if(s%20==0) { //moving of pf numbers
        				 pf_bp_flag=false;
        				 for(int i=0;i<numbers.length;i++) {
                			 if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Red"))) {
                				 numbers[i].getStack_x().push(px); numbers[i].getStack_y().push(py);
                				 if(!numbers[i].getStack_x().isEmpty()) {               					 
                					 
                					 Stack tempSlocx=new Stack(2000);
                    				 Stack tempSlocy=new Stack(2000);
                    				 if(numbers[i].getStack_x().size()>=1) {
                    					 tempSlocx.push(px); 
                    					 tempSlocy.push(py);
                    				 }
                    				 numbers[i].getStack_x().pop(); numbers[i].getStack_y().pop();
                    				 Stack tempSlocx2=new Stack(2000);
                    				 Stack tempSlocy2=new Stack(2000);
                    				                     				 
                    				 while(!numbers[i].getStack_x().isEmpty()) {
                    					 Object tmpobj1=numbers[i].getStack_x().pop();
                    					 Object tmpobj2=numbers[i].getStack_y().pop();
                    					 tempSlocx.push(tmpobj1);
                    					 tempSlocy.push(tmpobj2);
                    					 tempSlocx2.push(tmpobj1);
                    					 tempSlocy2.push(tmpobj2);
                    				 }
                    				 
                    				 
                    				 if(!tempSlocx2.isEmpty()) {
                    					 tempSlocx2.pop();
                        				 tempSlocy2.pop();
                    				 }
                    				 
                    				 while(!tempSlocx2.isEmpty()) {
                    					 Object objtmp1=tempSlocx2.pop();
                    					 Object objtmp2=tempSlocy2.pop();
                    					 if(mazemap[(int)objtmp2][(int)objtmp1]!=-3)
                    						 //cn.getTextWindow().output((int)objtmp1,(int)objtmp2,'.',numbers[i].getColor());
                    					 numbers[i].getStack_x().push(objtmp1);
                    					 numbers[i].getStack_y().push(objtmp2);
                    				 }
                    				 if(!tempSlocx.isEmpty()) {
                    					 int temploc_x=(int)tempSlocx.pop();
                        				 int temploc_y=(int)tempSlocy.pop();
                        				 if(temploc_x==px&&temploc_y==py && (mazemap[(int)numbers[i].getLocation_y()-1][(int)numbers[i].getLocation_x()]==1|| mazemap[(int)numbers[i].getLocation_y()+1][(int)numbers[i].getLocation_x()]==1|| mazemap[(int)numbers[i].getLocation_y()][(int)numbers[i].getLocation_x()-1]==1|| mazemap[(int)numbers[i].getLocation_y()][(int)numbers[i].getLocation_x()+1]==1)) {
                        					 if(numbers[i].getNumber()<=human_number.getNumber()) {
                        						 if(leftbackpack.isFull()) {
             	 	            					leftbackpack.pop();
             	 	            					leftbp_y++;
             	 	            				}
             	 	            				leftbackpack.push(numbers[i].getNumber());
             	 	            				mazemap[numbers[i].getLocation_y()][numbers[i].getLocation_x()]=0;
             	 	            				cn.getTextWindow().output(numbers[i].getLocation_x(),numbers[i].getLocation_y(),' ');
             	 	            				pf_bp_flag=true;
             	 	            				numbers[i]=null;
             	 	            				break;
                        					 }
                        					 else {
                        						 game_flag=false;
                            					 break;
                        					 }
                        					 
                        				 }
                        				 if(mazemap[temploc_y][temploc_x]!=-3 &&numbers[i]!=null) {
                        					 if(temploc_x==px&&temploc_y==py) {
                        						 if(mazemap[(int)numbers[i].getLocation_y()-1][(int)numbers[i].getLocation_x()]==1|| mazemap[(int)numbers[i].getLocation_y()+1][(int)numbers[i].getLocation_x()]==1|| mazemap[(int)numbers[i].getLocation_y()][(int)numbers[i].getLocation_x()-1]==1|| mazemap[(int)numbers[i].getLocation_y()][(int)numbers[i].getLocation_x()+1]==1) {
                        							 cn.getTextWindow().output(numbers[i].getLocation_x(),numbers[i].getLocation_y(),' ');
                                    				 mazemap[numbers[i].getLocation_y()][numbers[i].getLocation_x()]=0;
                                    				 cn.getTextWindow().output(temploc_x,temploc_y,Integer.toString((int) numbers[i].getNumber()).charAt(0),numbers[i].getColor());                                   				 
                                    				 numbers[i].setLocation_xpf(temploc_x);
                                    				 numbers[i].setLocation_ypf(temploc_y);
                                    				 mazemap[numbers[i].getLocation_y()][numbers[i].getLocation_x()]=-3;
                        						 }
                        						 else {
                        							 numbers[i].pathFinding(cn,numbers[i],human_number,mazemap);
                        						 }
                        					 }
                        					 else {
                        						 cn.getTextWindow().output(numbers[i].getLocation_x(),numbers[i].getLocation_y(),' ');
                                				 mazemap[numbers[i].getLocation_y()][numbers[i].getLocation_x()]=0;
                                				 cn.getTextWindow().output(temploc_x,temploc_y,Integer.toString((int) numbers[i].getNumber()).charAt(0),numbers[i].getColor());                               				 
                                				 numbers[i].setLocation_xpf(temploc_x);
                                				 numbers[i].setLocation_ypf(temploc_y);
                                				 mazemap[numbers[i].getLocation_y()][numbers[i].getLocation_x()]=-3;
                        					 }
                        					 
                        				 }
                        				                        				                         				 
                    				 }
                    				 }
                			 }
                		 }
        				 if(!game_flag) {
        					 break;
        				 }

        			 }
        			 for(int i=0;i<numbers.length;i++) {
	        			 if(numbers[i]!=null  && (numbers[i].stringColor(numbers[i]).equalsIgnoreCase("Red")) &&numbers[i].isPf_firsttime()) {
	        				 numbers[i].setPf_firsttime(false);
	        			 }
	        		 }
        			 
        		//System.out.println();
	        	 Thread.sleep(19);	        	 
	        }
	        if(!game_flag) {
	        	cn.getTextWindow().setCursorPosition(40,30);	  
	        	cn.getTextWindow().output("G A M E  O V E R !!!!!!");
	        }
	   }
	   public void elementsQueue(enigma.console.Console cn,int choice) {
		   Random rnd= new Random();
		   int input_x=57;
		   int input_y=2;
		   while(inputque.Size()!=10) {
			   int number=rnd.nextInt(100)+1;
			   if(choice==1) {
				   if(number<80) {
					   int num2=rnd.nextInt(3)+1;
					   inputque.Enqueue(num2);
				   }
				   else if(number>=80 && number<97) {
					   int num2=rnd.nextInt(3)+4; 
					   inputque.Enqueue(num2);
				   }
				   else {
					   int num2=rnd.nextInt(3)+7; 
					   inputque.Enqueue(num2);
				   }
			   }
			   else if(choice==2) {
				   if(number<75) {
					   int num2=rnd.nextInt(3)+1;
					   inputque.Enqueue(num2);
				   }
				   else if(number>=75 && number<95) {
					   int num2=rnd.nextInt(3)+4; 
					   inputque.Enqueue(num2);
				   }
				   else {
					   int num2=rnd.nextInt(3)+7; 
					   inputque.Enqueue(num2);
				   }
			   }
			   else {
				   if(number<50) {
					   int num2=rnd.nextInt(3)+1;
					   inputque.Enqueue(num2);
				   }
				   else if(number>=50 && number<65) {
					   int num2=rnd.nextInt(3)+4; 
					   inputque.Enqueue(num2);
				   }
				   else {
					   int num2=rnd.nextInt(3)+7; 
					   inputque.Enqueue(num2);
				   }
			   }
			   
		   
		   }
		   for(int i=0;i<10;i++) {
			   Object obj3=inputque.Dequeue();
			   cn.getTextWindow().output(input_x,input_y,Integer.toString((int) obj3).charAt(0));
			   inputque.Enqueue(obj3);
			   input_x++;
		   }		   
	   }
	   

}