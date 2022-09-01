import java.awt.Color;
import java.util.Random;

import enigma.console.TextAttributes;
public class Number {
	private TextAttributes color;
	private int number;
	private int location_x;
	private int location_y;
	private boolean random_flag;
	private int direction;
	private int len1;
	private int counter;
	private int[]next_coors;
	private boolean backpack_flag;
	private boolean num_game_flag;
	private Stack stack_x;
	private Stack stack_y;
	private boolean pf_firsttime;
	public Number (int number) throws Exception {
		this.number=number;
		if(number<4) {
			color=new TextAttributes(Color.GREEN, Color.black);
			
		}
		else if(number>3 && number<7) {
			color=new TextAttributes(Color.YELLOW, Color.black);
		}
		else {
			color=new TextAttributes(Color.RED, Color.black);
		}
		random_flag=true;
		direction=0;
		len1=0;
		counter=0;
		
	}
	
	
	public static int[] randomLocation(Number num,int[][] arr,enigma.console.Console cn,TextAttributes att) throws Exception {
		int[] locationarr=new int[2]; //random placing of input numbers and assigning mazemap
		Random rnd= new Random();
		boolean flag=true;
		
		while(flag==true) {
			int x= rnd.nextInt(23);
			int y= rnd.nextInt(55);
			locationarr[0]=x;
			locationarr[1]=y;
			if(arr[x][y]==0) {
				cn.getTextWindow().output(y,x,Integer.toString(num.getNumber()).charAt(0),att);
				if(num.getNumber()>0 && num.getNumber()<7)
					arr[x][y]=-1;
				else if(num.getNumber()>6 && num.getNumber()<10) {
					arr[x][y]=-3;
					num.setPf_firsttime(true);
				}
					
				flag=false;
			}
			
		}
		return locationarr;
		
		
	}
	public static void randomMoving(enigma.console.Console cn,Number num1,int[][] arr,Number[] numbers,Stack leftbackpack,int leftbp_y,Number human_number) {
		//random moving of yellow numbers
		
		Random rnd= new Random();
		num1.setBackpack_flag(false);
		num1.setNum_game_flag(true);
		if(num1.isFlag()==true) { //1 north 2 south 3 west 4 east
			num1.setDirection(rnd.nextInt(4)+1);
			num1.setLen1(rnd.nextInt(8)+1);		
		}
		
		if(num1.getDirection()==1   ) {
			if(num1.getLocation_y()>1) {
				if(arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1 &&  arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3) {
					num1.deletingMazemap(cn,arr,num1); 
					num1.setLocation_y(-1);
					num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
					num1.addingCounter();
					num1.setFlag(false);
				}
				else { 
					num1.setFlag(true);
					while(true) {
						int rnd2=(rnd.nextInt(3)+1); //1-south 2west 3east
						if(rnd2==1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3 ) {
							num1.deletingMazemap(cn,arr,num1); 
							num1.setLocation_y(1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==2 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3 ) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
								arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
							break;
						}												
					}
				}
			}
		
			else {
				
					num1.setFlag(true);
					while(true) {
						int rnd2=(rnd.nextInt(3)+1); //1-south 2west 3east
						if(rnd2==1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2 &&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3 ) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_y(1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==2 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==3 && arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
								arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
							break;
						}
					}
			}
		}
		else if(num1.getDirection()==2) {
			if(num1.getLocation_y()<21) {
				if(arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3) {
					num1.deletingMazemap(cn,arr,num1);
					num1.setLocation_y(1);
					num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
					num1.addingCounter();
					num1.setFlag(false);
				}
				else {
					
					num1.setFlag(true);
					while(true) {
						int rnd2=(rnd.nextInt(3)+1); //1-south 2west 3east
						if(rnd2==1 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1  &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_y(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==2 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
								arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
							break;
						}												
					}					
				}
			}
			else {				
				num1.setFlag(true);
				while(true) {
					int rnd2=(rnd.nextInt(3)+1); //1-north 2west 3east
					if(rnd2==1 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3 ) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_y(-1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					else if(rnd2==2 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2&& arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_x(-1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_x(1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
							arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
						break;
					}
				}
			}
			
			
		}
		else if(num1.getDirection()==3 ) {
			if(num1.getLocation_x()>1) {
				if(arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
					num1.deletingMazemap(cn,arr,num1);
					num1.setLocation_x(-1);
					num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
					num1.addingCounter();
					num1.setFlag(false);
				}
				
				else {
					
					num1.setFlag(true);
					while(true) {
						int rnd2=(rnd.nextInt(3)+1); //1-kuzey 2batý 3doðu
						if(rnd2==1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3 ) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_y(+1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==2 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_y(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
								arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
							break;
						}
					}
					
				}
			}
			else {
				num1.setFlag(true);
				while(true) {
					int rnd2=(rnd.nextInt(3)+1); //1-north 2west 3east
					if(rnd2==1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1  &&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_y(+1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					else if(rnd2==2 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_y(-1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3 ) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_x(1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
							arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
						break;
					}
			}
			}
			
			
		}
		else if(num1.getDirection()==4 ) {
			if(num1.getLocation_x()<53) {
				if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-2&&arr[num1.getLocation_y()][num1.getLocation_x()+1]!=-3) {
					num1.deletingMazemap(cn,arr,num1);
					num1.setLocation_x(+1);
					num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
					num1.addingCounter();
					num1.setFlag(false);
				}
				
				else {
					num1.setFlag(true);
					while(true) {
						
						int rnd2=(rnd.nextInt(3)+1); //1-north 2south 3east
						if(rnd2==1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1&&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2 &&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_y(+1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==2 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_y(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}
						else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
							num1.deletingMazemap(cn,arr,num1);
							num1.setLocation_x(-1);
							num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
							break;
						}	
						if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
								arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
							break;
						}
						
					}
				}
			}
			else {
				num1.setFlag(true);
				while(true) {
					int rnd2=(rnd.nextInt(3)+1); //1-north 2south 3east
					if(rnd2==1 && arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-1&&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-2&&arr[num1.getLocation_y()+1][num1.getLocation_x()]!=-3 ) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_y(+1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					else if(rnd2==2 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-1 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-2 &&arr[num1.getLocation_y()-1][num1.getLocation_x()]!=-3) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_y(-1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					else if(rnd2==3 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-1 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-2 &&arr[num1.getLocation_y()][num1.getLocation_x()-1]!=-3) {
						num1.deletingMazemap(cn,arr,num1);
						num1.setLocation_x(-1);
						num1.meetingHumannumber(cn,numbers,num1,arr,leftbackpack,leftbp_y,human_number);
						break;
					}
					if(arr[num1.getLocation_y()][num1.getLocation_x()+1]!=0 && arr[num1.getLocation_y()][num1.getLocation_x()-1]!=0 && 
							arr[num1.getLocation_y()+1][num1.getLocation_x()]!=0 && arr[num1.getLocation_y()-1][num1.getLocation_x()]!=0) {
						break;
					}
					
					
				}
			}
			
			
		}
		if(num1.isFlag()==false) {
			if(num1.getCounter()==num1.getLen1()) {
				num1.setCounter(0);
				num1.setFlag(true);
			}
			else {
				num1.setFlag(false);
			}
		}
	}
	
	public void pathFinding(enigma.console.Console cn,Number pfnum,Number human_number,int[][]mazemap) {//pathfinding
		int temp_x=pfnum.getLocation_x();
		int temp_y=pfnum.getLocation_y();
		Stack pathStack_x=new Stack(2000);
		Stack pathStack_y=new Stack(2000);
		Stack temppathStack_x=new Stack(2000);
		Stack temppathStack_y=new Stack(2000);
		pathStack_x.push(pfnum.getLocation_x());
		pathStack_y.push(pfnum.getLocation_y());
		int temp_hx=0;
		int temp_hy=0;
		
		boolean bf_flag2=false;
		while((int)pathStack_x.peek()!=human_number.getLocation_x() && (int)pathStack_y.peek()!=human_number.getLocation_y()) {
			//1-north 2-east 3-south 4-west direction order
			boolean pf_flag=true;
			if(pfnum.getLocation_y()>=1) {
				while(true) {
					if((pfnum.getLocation_y()>=1)&&(mazemap[pfnum.getLocation_y()-1][pfnum.getLocation_x()]!=-2 &&  
						mazemap[pfnum.getLocation_y()-1][pfnum.getLocation_x()]!=-1 &&
						mazemap[pfnum.getLocation_y()-1][pfnum.getLocation_x()]!=-5 &&mazemap[pfnum.getLocation_y()-1][pfnum.getLocation_x()]!=-6&&mazemap[pfnum.getLocation_y()-1][pfnum.getLocation_x()]!=-3)) {
						if(mazemap[pfnum.getLocation_y()-1][pfnum.getLocation_x()]==1) {
							bf_flag2=true;
							temp_hx=pfnum.getLocation_x();
							temp_hy=pfnum.getLocation_y()-1;
							break;
						}
						else {
							mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()]=-5;
							
							pfnum.setLocation_y(-1);
							pathStack_y.push(pfnum.getLocation_y());
							pathStack_x.push(pfnum.getLocation_x());
							
							pf_flag=false;
						}
					}
					else {
						break;
					}
					
				}
			}
			if(bf_flag2==true) {
				break;
			}
			if(pfnum.getLocation_x()<=53) {
				while(true) {
					if(pfnum.getLocation_x()<=53&& (mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()+1]!=-2 && 
						mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()+1]!=-1 &&
						mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()+1]!=-5 &&mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()+1]!=-6&&mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()+1]!=-3)) {
						if(mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()+1]==1) {
							bf_flag2=true;
							temp_hx=pfnum.getLocation_x()+1;
							temp_hy=pfnum.getLocation_y();
							break;
						}else {
							mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()]=-5;
							
							pfnum.setLocation_x(1);
							pathStack_y.push(pfnum.getLocation_y());
							pathStack_x.push(pfnum.getLocation_x());
							pf_flag=false;
						}
					}else {
						break;
					}
					
					
				}
			}
			if(bf_flag2==true) {
				break;
			}
			if(pfnum.getLocation_y()<=21) {
				while(true) {
					if(pfnum.getLocation_y()<=21 &&(mazemap[pfnum.getLocation_y()+1][pfnum.getLocation_x()]!=-2 &&
						mazemap[pfnum.getLocation_y()+1][pfnum.getLocation_x()]!=-1 &&
						mazemap[pfnum.getLocation_y()+1][pfnum.getLocation_x()]!=-5 &&mazemap[pfnum.getLocation_y()+1][pfnum.getLocation_x()]!=-6&&mazemap[pfnum.getLocation_y()+1][pfnum.getLocation_x()]!=-3)) {
						if(mazemap[pfnum.getLocation_y()+1][pfnum.getLocation_x()]==1) {
							bf_flag2=true;
							temp_hx=pfnum.getLocation_x();
							temp_hy=pfnum.getLocation_y()+1;
							break;
						}
						else {
							mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()]=-5;
							
							pfnum.setLocation_y(1);
							pathStack_y.push(pfnum.getLocation_y());
							pathStack_x.push(pfnum.getLocation_x());
							pf_flag=false;
						}
					}else {
						break;
					}
					
				}
			}
			if(bf_flag2==true) {
				break;
			}
			if(pfnum.getLocation_x()>=1) {
				while(true) {
					if(pfnum.getLocation_x()>=1&&(mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()-1]!=-2 && 
						mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()-1]!=-1 &&
						mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()-1]!=-5 &&mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()-1]!=-6&&mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()-1]!=-3)) {
						if(mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()-1]==1) {
							bf_flag2=true;
							temp_hx=pfnum.getLocation_x()-1;
							temp_hy=pfnum.getLocation_y();
							break;
						}else {
							mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()]=-5;
							
							pfnum.setLocation_x(-1);
							pathStack_y.push(pfnum.getLocation_y());
							pathStack_x.push(pfnum.getLocation_x());
							pf_flag=false;
						}
					}else {
						break;
					}
					
					
				}
			}
			if(bf_flag2==true) {
				break;
			}
			if(pf_flag==true &&pathStack_y.size()>1) {				
				mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()]=-6;
				pathStack_y.pop();
				pathStack_x.pop();
				pfnum.setLocation_xpf((int)pathStack_x.peek());
				pfnum.setLocation_ypf((int)pathStack_y.peek());								
			}
			else if(pf_flag==true &&pathStack_y.size()==1) {
				mazemap[pfnum.getLocation_y()][pfnum.getLocation_x()]=-6;
				pfnum.setLocation_xpf((int)pathStack_x.pop());
				pfnum.setLocation_ypf((int)pathStack_y.pop());
			}
			if(pathStack_y.isEmpty()&&pathStack_x.isEmpty())
				break;
		}
		
		if(!pathStack_y.isEmpty()) {
			pathStack_y.push(temp_hy);
			pathStack_x.push(temp_hx);
		}
		while(!pathStack_y.isEmpty()) {
			temppathStack_y.push(pathStack_y.pop());
		}
		while(!pathStack_x.isEmpty()) {
			temppathStack_x.push(pathStack_x.pop());
		}		
		if(!temppathStack_x.isEmpty()) {
			temppathStack_x.pop();
			temppathStack_y.pop();
		}
		while(temppathStack_y.size()>1) { 
					
					if(!temppathStack_y.isEmpty()) {
						Object tempobjx=temppathStack_x.pop();
						Object tempobjy=temppathStack_y.pop();
						pathStack_x.push(tempobjx); pathStack_y.push(tempobjy);
						//cn.getTextWindow().output((int)tempobjx,(int)tempobjy,'.',pfnum.getColor());
					}
					
				}
		setStack_x(pathStack_x); setStack_y(pathStack_y);
		for(int i=0;i<23;i++) {
        	for(int j=0;j<55;j++) {
        		if(mazemap[i][j]==-6 || mazemap[i][j]==-5) {
        			mazemap[i][j]=0;
        		}
        	}
        }
		
		pfnum.setLocation_xpf(temp_x);
		pfnum.setLocation_ypf(temp_y);
		mazemap[temp_y][temp_x]=-3;		
	}
	private void deletingMazemap(enigma.console.Console cn,int[][] arr,Number num1) {
		cn.getTextWindow().output(num1.getLocation_x(),num1.getLocation_y(),' ',num1.getColor());
		arr[num1.getLocation_y()][num1.getLocation_x()]=0;
	}

	public TextAttributes getColor() {
		return color;
	}


	public void setColor(TextAttributes color) {
		this.color = color;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}
	public void addingHumannum(int number) {
		if(this.number==9) {
			this.number=1;
		}
		else {
			this.number+=1;
		}
	}
	public void nextCoor(int[] arr){
		this.next_coors=arr;
	}
	public int[] getNextcoor() {
		return next_coors;
	}
	public int getLocation_x() {
		return location_x;
	}


	public void setLocation_x(int location_x) {
		this.location_x += location_x;
	}
	public void setLocation_xpf(int location_x) {
		this.location_x = location_x;
	}


	public int getLocation_y() {
		return location_y;
	}

	public void setLocation_ypf(int location_y) {
		this.location_y = location_y;
	}
	public void setLocation_y(int location_y) {
		this.location_y += location_y;
	}


	public boolean isFlag() {
		return random_flag;
	}


	public void setFlag(boolean random_flag) {
		this.random_flag = random_flag;
	}


	public int getDirection() {
		return direction;
	}


	public void setDirection(int direction) {
		this.direction = direction;
	}


	public int getLen1() {
		return len1;
	}


	public void setLen1(int len1) {
		this.len1 = len1;
	}


	public int getCounter() {
		return counter;
	}


	public void setCounter(int counter) {
		this.counter = counter;
	}
	public void addingCounter() {
		this.counter+=1;
	}
	public String stringColor(Number num1) {//determining the number's color
		String color1;
		if(num1.getNumber()<4) {
			color1="Green";
			
		}
		else if(num1.getNumber()>3 && num1.getNumber()<7) {
			color1="Yellow";
		}
		else {
			color1="Red";
		}
		return color1;
	}

	public void setStack_x(Stack stack_x ) {
		this.stack_x=stack_x;
	}
	public void setStack_y(Stack stack_y) {
		this.stack_y=stack_y;
	}
	public Stack getStack_x() {
		return stack_x;
	}
	public Stack getStack_y() {
		return stack_y;
	}
	public boolean isBackpack_flag() {
		return backpack_flag;
	}


	public void setBackpack_flag(boolean backpack_flag) {
		this.backpack_flag = backpack_flag;
	}


	public boolean isNum_game_flag() {
		return num_game_flag;
	}


	public void setNum_game_flag(boolean num_game_flag) {
		this.num_game_flag = num_game_flag;
	}
	
	private void meetingHumannumber(enigma.console.Console cn,Number[] numbers,Number num1,int[][]arr,Stack leftbackpack,int leftbp_y,Number human_number) {
		if(arr[num1.getLocation_y()][num1.getLocation_x()]==1) {//when the number meets human number
     		for(int i=0;i<numbers.length;i++) {
     			if(numbers[i]!=null && numbers[i].getLocation_x()==num1.getLocation_x() && numbers[i].getLocation_y()==num1.getLocation_y() && numbers[i].getNumber()<=human_number.getNumber()) {
   
     				if(leftbackpack.isFull()) {
     					leftbackpack.pop();
     					leftbp_y++;
     				}
     				leftbackpack.push(numbers[i].getNumber());
     				numbers[i].setBackpack_flag(true);
     				
     				
     			}
     			if(numbers[i]!=null && numbers[i].getLocation_x()==num1.getLocation_x() && numbers[i].getLocation_y()==num1.getLocation_y() && numbers[i].getNumber()>human_number.getNumber()) {
     				numbers[i].setNum_game_flag(false);
     			}
     		}
     	}
		else {
			arr[num1.getLocation_y()][num1.getLocation_x()]=-1;
			cn.getTextWindow().output(num1.getLocation_x(),num1.getLocation_y(),Integer.toString((int) num1.getNumber()).charAt(0),num1.getColor());
		}
	}


	public boolean isPf_firsttime() {
		return pf_firsttime;
	}


	public void setPf_firsttime(boolean pf_firsttime) {
		this.pf_firsttime = pf_firsttime;
	}
	
}