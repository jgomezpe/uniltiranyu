package test.games.squares;

import java.util.*;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.squares.Squares;

public class CuadritoAgentProgram implements AgentProgram{
	protected String color;
	public CuadritoAgentProgram(String color) {
		// TODO Auto-generated constructor stub
		this.color = color;
	}

	@Override
	public Action compute(Percept p) {
		// TODO Auto-generated method stub
		long time = (long)(200*Math.random());
		try {
			Thread.sleep(time);
		}catch(Exception e){}
		//logic goes here =D
		
		int sz = Integer.parseInt((String)p.get(Squares.SIZE));
		if(p.get(Squares.TURN).equals(color)) {
			//System.out.println(sz);
			//store all values (valid positions)
			int all[][] = new int[sz + 2][sz + 2];
			for(int i = 0; i < sz + 2; ++i) {
				for(int j = 0; j < sz + 2; ++j) {
					all[i][j] = 0;
					
				}
				System.out.println();
			}
			
			
			for(int i = 0; i < sz; ++i) {
				for(int j  = 0; j < sz; ++j) {
					int sidecnt = 0;
					if(((String)p.get(i + ":" + j + ":" + Squares.BOTTOM)).equals(Squares.TRUE)) ++sidecnt;
					if(((String)p.get(i + ":" + j + ":" + Squares.LEFT)).equals(Squares.TRUE)) ++sidecnt;
					if(((String)p.get(i + ":" + j + ":" + Squares.TOP)).equals(Squares.TRUE)) ++sidecnt;
					if(((String)p.get(i + ":" + j + ":" + Squares.RIGHT)).equals(Squares.TRUE)) ++sidecnt;
					all[i + 1][j + 1] = 4 - sidecnt;
				}
			}
			
			for(int i = 0; i < sz + 2; ++i) {
				for(int j = 0; j < sz + 2; ++j) {
					System.out.print(all[i][j] + "  ");
				}
				System.out.println();
			}
			
			//generate random position & check if it's possible to add a line around
			int sqr = sz * sz;
			int arraux [] = new int[sqr];
			arraux[0] = (int)(Math.random()*sqr);
			for(int i = 1; i < sqr; ++i) {
				arraux[i] = (int)(Math.random()*sqr);
				for(int j = 0; j < i; ++j) {
					if(arraux[i] == arraux[j])
						--i;							
				}
			}
			
			boolean ok = true;
			for(int i = 0; i < sz; ++i) {
				for(int j = 0; j < sz; ++j) {
										
					boolean done = false;
					
					//check surrounding squares
					int x = arraux[(i * sz) + j]/sz; ++x;
					int y = arraux[(i * sz) + j]%sz; ++y;
					String move = "";
					System.out.println("x: " + x + " y: " + y + " = " + all[x][y] + "\n");
					
					boolean bottom = ((String)p.get((x - 1) + ":" + (y - 1) + ":" + Squares.BOTTOM)).equals(Squares.FALSE);
					boolean left = ((String)p.get((x - 1) + ":" + (y - 1) + ":" + Squares.LEFT)).equals(Squares.FALSE);
					boolean top = ((String)p.get((x - 1) + ":" + (y - 1) + ":" + Squares.TOP)).equals(Squares.FALSE);
					boolean right = ((String)p.get((x - 1) + ":" + (y - 1) + ":" + Squares.RIGHT)).equals(Squares.FALSE);
					
					if(all[x][y] >= 3) {
						int randaux = (int)(Math.random() * 4);
						for(int k = 0; k < 4; ++k) {
							++randaux;
							randaux %= 4;
							System.out.println("rand: " + randaux);
							if(randaux == 0 && all[x + 1][y] >= 3 && bottom) {	
								//add line at the bottom
								--all[x][y];
								--all[x + 1][y];
								try {
									move = "bottom";
									done = true;
									return new Action((x - 1) + ":" + (y - 1) + ":" + move);
								}catch(Exception e) {}
								break;
							}else if(randaux == 1 && all[x][y - 1] >= 3 && left) {
								//add line at the left
								--all[x][y];
								--all[x][y - 1];
								try {
									move = "left";
									done = true;
									return new Action((x - 1) + ":" + (y - 1) + ":" + move);
								}catch(Exception e) {}
								break;
							}else if(randaux == 2 && all[x - 1][y] >= 3 && top) {	
								//add line at the top
								--all[x][y];
								--all[x - 1][y];
								try {
									move = "top";
									done = true;
									return new Action((x - 1) + ":" + (y - 1) + ":" + move);
								}catch(Exception e) {}
								break;
							}else if(randaux == 3 && all[x][y + 1] >= 3 && right){	
								//add line at the right
								--all[x][y];
								--all[x][y + 1];
								try {
									move = "right";
									done = true;
									return new Action((x - 1) + ":" + (y - 1) + ":" + move);
								}catch(Exception e) {}
								break;
							}
						}
					}else {
						continue;
					}
					if(done ) break;
				}
				System.out.println();
			}
			
			//draw a line if its possible and calculate points
			while(ok) {
				int tot = 0;
				for(int i = 0; i < sz; ++i) {
					for(int j = 0; j < sz; ++j) {
						int x = arraux[(i * sz) + j]/sz; ++x;
						int y = arraux[(i * sz) + j]%sz; ++y;
						tot += all[x][y];
						String move = "";
						if(all[x][y] == 2 || all[x][y] == 3) {
							if(all[x + 1][y] == 2 || all[x + 1][y] == 3) {
								//add line at the bottom
								--all[x][y];
								--all[x + 1][y];
								try {
									move = "bottom";
									return new Action((x - 1) + ":" + (y - 1) + ":" + move);
								}catch(Exception e) {}
								//ok = false;
								break;
							}else {
								if(all[x][y - 1] == 2 || all[x][y - 1] == 3) {
									//add line at the left
									--all[x][y];
									--all[x][y - 1];
									try {
										move = "left";
										return new Action((x - 1) + ":" + (y - 1) + ":" + move);
									}catch(Exception e) {}
									//ok = false;
									break;
								}else {
									if(all[x - 1][y] == 2 || all[x - 1][y] == 3) {
										//add line at the top
										--all[x][y];
										--all[x - 1][y];
										try {
											move = "top";
											return new Action((x - 1) + ":" + (y - 1) + ":" + move);
										}catch(Exception e) {}
										//ok = false;
										break;
									}else {
										if(all[x][y + 1] == 2 || all[x][y + 1] == 3) {
											//add line at the right
											--all[x][y];
											--all[x][y + 1];
											try {
												move = "right";
												return new Action((x - 1) + ":" + (y - 1) + ":" + move);
											}catch(Exception e) {}
											//ok = false;
											break;
										}else {
											ok = false;
											break;
										}
									}
								}
							}
						}
					}
				}
				if(tot == 0) {
					//ok = false;
					break;
				}
			}

		}
		return new Action(Squares.PASS);
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
	}
}
