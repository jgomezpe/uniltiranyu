package test.labyrinth.teseo;
import java.util.ArrayList;
import java.util.Random;

import uniltiranyu.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

public class SmartSimple extends SimpleTeseoAgentProgram {
	
	boolean [][] map;// map
	int x,y; // coordinates x y
	int orientation;//N -> 0,E ->1, S -> 2, W -> 3
	public SmartSimple() {
		map = new boolean [100][100];
		orientation = 0;
		x=50;
		y=50;
	}

	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
		// TODO Auto-generated method stub
		
		if(MT) {
			x=50;
			y=50;
			cleanMap();
			return -1;
		}else {
			System.out.print("X: "+(x-50)+" Y: "+(y-50)+" --> ");
			mark(x,y);
			return betterOption(PF, PD, PA, PI);
		}
	}

	
	public void mark(int x, int y) {
		map[x][y]=true;
	}
	
	
	public int betterOption(boolean PF, boolean PD, boolean PA, boolean PI) {
		//move to response
		
		int move =0;
		Random rand = new Random();
		
		//positions to check if already visit
		boolean F=false;
		boolean D=false;
		boolean I=false;
		boolean A=false;
		
		//list of candidates to move
		ArrayList<Integer> candidates = new ArrayList<Integer>();
		ArrayList<Integer> candidatesSimple = new ArrayList<Integer>();
		
		//look if already visit around positions
		switch (orientation) {
			case 0:
				F=map[x-1][y];
				D=map[x][y+1];
				I=map[x][y-1];
				A=map[x+1][y];
				break;
			case 1:
				F=map[x][y+1];
				D=map[x+1][y];
				I=map[x-1][y];
				A=map[x][y-1];
				break;
			case 2:
				F=map[x+1][y];
				D=map[x][y-1];
				I=map[x][y+1];
				A=map[x-1][y];
				break;
			case 3:
				F=map[x][y-1];
				D=map[x-1][y];
				I=map[x+1][y];
				A=map[x][y+1];
				break;
		}
		
		
		//add move candidates to the list
		if(!PF && !F) {
			candidates.add(0);
		}else if(!PD && !D) {
			candidates.add(1);
		}else if(!PI && !I) {
			candidates.add(3);
		}else if(!PA && !A) {
			candidates.add(2);
		}
		
		if(candidates.size()==0) {
			//if all candidates was visited
			System.out.print("all was visited \n");
			if(!PF) {
				candidatesSimple.add(0);
			}else if(!PD) {
				candidatesSimple.add(1);
			}else if(!PI) {
				candidatesSimple.add(3);
			}else if(!PA) {
				candidatesSimple.add(2);
			}
			
			move = candidatesSimple.get(rand.nextInt(candidatesSimple.size()));
			move(move);
			orientation=(orientation+move)%4;

			
			
		}else {
			//if there are candidates without visiting
			System.out.print("some ones are not visited yet \n");
			move = candidates.get(rand.nextInt(candidates.size()));
			move(move);
			orientation=(orientation+move)%4;
		}
		return move;
	}
	
	public void move(int move) {
		switch (orientation) {
			case 0:
				switch (move) {
					case 0:
						x--;
						break;
					case 1:
						y++;
						break;
					case 2:
						x++;
						break;
					case 3:
						y--;
						break;
				}
				break;
			case 1:
				switch (move) {
					case 0:
						y++;
						break;
					case 1:
						x++;
						break;
					case 2:
						y--;
						break;
					case 3:
						x--;
						break;
				}
				
				break;
			case 2:
				switch (move) {
					case 0:
						x++;
						break;
					case 1:
						y--;
						break;
					case 2:
						x--;
						break;
					case 3:
						y++;
						break;
				}
				
				break;
			case 3:
					switch (move) {
					case 0:
						y--;
						break;
					case 1:
						x--;
						break;
					case 2:
						y++;
						break;
					case 3:
						x++;
						break;
				}
				
				break;
		}
	}
	
	public void cleanMap() {
		map= new boolean[100][100];
	}

}
