package test.games.squares;

import speco.array.Array;

//import java.util.ArrayList;
//import java.util.List;

public class simState {
	public Box[][] simBoard; //board state nodes
	public Array<Box> mainBoxes;
	public Array<Integer[]> sitesVisited = new Array<Integer[]>();
		public Array<Integer> stateBacktracking = new Array<Integer>();
	public int score; //simulate state score
	public String prevMov;
	public int numBoxes;
	
    public simState(int size){
        this.simBoard = new Box[size][size];
        this.score = 0;
        this.prevMov = "";
        this.mainBoxes = new Array<Box>();
    	this.numBoxes = 0;
    //    this.sitesVisited = new Array<Integer[]>();
        	//System.out.println("	size : "+this.simBoard.length);
    }
    
    public simState(simState baseState) {
    	this.simBoard = baseState.simBoard;
        this.score = baseState.score;
        this.prevMov = baseState.prevMov;
        this.mainBoxes = baseState.mainBoxes;
        this.numBoxes = baseState.numBoxes;
    //    this.sitesVisited = new Array<Integer[]>();
    }
    
    public simState(simState baseState, String prevMov) {
    	this.simBoard = baseState.simBoard;
        this.score = baseState.score;
        this.prevMov = prevMov;
        this.mainBoxes = baseState.mainBoxes;
    //    this.sitesVisited = new Array<Integer[]>();
    }
    
    public void addBox(int x, int y) { 
    	this.simBoard[x][y]= new Box(x,y); 
    	
    }
    
    public void addBoxOption(int x, int y, int option) { this.simBoard[x][y].addOption(option); }
    
    public void deleteBox(int x, int y){ this.simBoard[x][y].deleteOptions(); };
    
    public Box getBox(int x, int y) { return simBoard[x][y]; }
    
    public void setMainBoxes(Array<Box> mainBoxes) { this.mainBoxes = mainBoxes; }   
    
    public Array<Box> getMainBoxes() { return this.mainBoxes; }
    
    public void addSiteVisited(int x, int y) { 
    	Integer[] site = {(Integer) x,(Integer) y};
    	this.sitesVisited.add(site); 
    }
    
    public Boolean isVisited(int x, int y) {
    	Integer[] site = {(Integer) x,(Integer) y};
    	return sitesVisited.contains(site);
    }
    
    public void setScore(int score) { 
    	System.out.println("prevScore : "+this.score+" newScore : "+score);
    	this.score = score;
    }
    
    public void setPrevMov(String mov) { this.prevMov = mov; }
    
		    public void setBacktracking(Array<Integer> backtrack) {
		    	this.stateBacktracking = backtrack;
		    }
		    
	public boolean isInMainBoxes(int x, int y) {
		boolean check = false;
		for(Box box : this.mainBoxes) {
			//System.out.println("checking x:"+x+" y:"+y+" in 0:"+box.coordinates[0]+" 1:"+box.coordinates[1]);
			if ( (box.coordinates[0]==x) && (box.coordinates[1]==y) ) {
				check = true;
				break;
			}
		}
		return check;
	}
	
	public void printBoard() {
		System.out.print("lenght"+simBoard.length);
		for (int i=0;i<simBoard.length;i++) {
    		for(int j=0;j<simBoard.length;j++) {
    			if (simBoard[i][j] != null) {
    				Box tempBox = simBoard[i][j]; 
    				System.out.print("- "+tempBox.coordinates[0]+","+tempBox.coordinates[1]+":"+tempBox.getConnections().size());
    			}else {
    				System.out.print("- null ");
    			}
    		}
			System.out.println(" ");
		}
		System.out.println(" ");
	}
	
	public Box getFirstBox() {
		Box tempBox = new Box(-1,-1);
		for (int i=0;i<simBoard.length;i++) {
    		for(int j=0;j<simBoard.length;j++) {
    			if (simBoard[i][j] != null) {
    				tempBox = simBoard[i][j]; 
    				if ( tempBox.getConnections().size() > 0 ) {
    					System.out.print("getting- "+tempBox.coordinates[0]+","+tempBox.coordinates[1]+":"+tempBox.getConnections().size());
    					break;
    				}
    			}
    		}
		}
		return tempBox;
	}
}