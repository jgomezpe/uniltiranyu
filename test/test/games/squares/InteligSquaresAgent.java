/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.squares;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.squares.Squares;

/**
 *
 * @author Jonatan
 */
public class InteligSquaresAgent implements AgentProgram {
    protected String color;
    public int numTurn;
    
    public static int UP = 0;
    public static int RIGHT = 1;
    public static int DOWN = 2;
    public static int LEFT = 3;
    public static String[] squaresMovements= {"top","right","bottom","left"};
    public int maxDeep;
    public int maxBreadth;
    public int turnStage;
    
    public InteligSquaresAgent( String color ){
    	this.color = color;        
        this.numTurn = 0;
        this.maxDeep = 1;
        this.maxBreadth = 2;
        this.turnStage = 2;
    }

    public InteligSquaresAgent( String color , int maxDeep, int maxBreadth , int turnStage ){
        this.color = color;        
        this.numTurn = 0;
        this.maxDeep = maxDeep;
        this.maxBreadth = maxBreadth;
        this.turnStage = turnStage;
    }
    
    public String randomMove(Percept p) {
		int size = Integer.parseInt((String)p.get(Squares.SIZE));
		int i = 0;
		int j = 0;
		//int gstate = 0;
		Array<String> v = new Array<String>();
		Array<Integer> n = new Array<Integer>();
		 // if (numTurn>size) gstate=0;     
		while(v.size()<=0){
		    i = (int)(size*Math.random());
		    j = (int)(size*Math.random());
			 // System.out.println( "	My coords:"+i+","+j);
		    if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)) {
		  	//System.out.print( "	left:"+p.getAttribute(i+":"+j+":"+Squares.LEFT)+" ;");
		      v.add(Squares.LEFT);
		    	n.add(checkSquare(p,i,j-1));
		    }
		    if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)) {
		    	//System.out.print( "	TOP:"+p.getAttribute(i+":"+j+":"+Squares.TOP)+" ;");
		      v.add(Squares.TOP);
		  	n.add(checkSquare(p,i-1,j));
		    }
		    if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)) {
		      //System.out.print( "	TOP:"+p.getAttribute(i+":"+j+":"+Squares.TOP)+" ;");
		      v.add(Squares.BOTTOM);
		  	n.add(checkSquare(p,i+1,j));
		    }
		  	if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)) {
		      v.add(Squares.RIGHT);
		  	n.add(checkSquare(p,i,j+1));
		    }
		}
		int option = 0;
		String move = v.get(0);
		for (int x=0;
				x<v.size(); x++) {
			if (option < n.get(x)) {
				option = n.get(x);
				move = v.get(x);
			}
		}
		return i+":"+j+":"+move  ;
    }
    
    public int checkSquare(Percept p, int i, int j) {
        int n = 0;
    	if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
          n++;
        if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
          n++;
        if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
          n++;
        if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
          n++;
        return n;
    }
    
    public String expandNode(Percept p) {
    	try {
	    	int maxDeep = this.maxDeep;
	    	int maxBreadth = this.maxBreadth;    
	        int boardSize = Integer.parseInt((String)p.get(Squares.SIZE));
	    	System.out.println("---------------calculating turn action-------------");
	    	simState rootState = createState(p,boardSize);
	    	simState moveState= calcStateMax(rootState, maxDeep,maxBreadth);
	    	System.out.println("	-------------------temp resp: " + moveState.prevMov);
	    	return moveState.prevMov;
	    } catch(Exception error) {
	    	return randomMove(p);
	    }     	
    }
    
    
//
    public simState createState(Percept p,int boardSize){
    	simState state = new simState(boardSize);
    	Array<Box> mainBoxes = new Array<Box>();
    	for (int i=0;i<boardSize;i++) {
    		for(int j=0;j<boardSize;j++) {
    			state.addBox(i,j);
    	    	if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
    	    		state.addBoxOption(i, j, LEFT);
    	        if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
    	    		state.addBoxOption(i, j, UP);
    	        if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
    	    		state.addBoxOption(i, j, DOWN);
    	        if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
    	    		state.addBoxOption(i, j, RIGHT);
    	        Box cbox = state.getBox(i,j);
        		if (cbox.connections.size()>2)      
        			mainBoxes.add(cbox);
    		}
    	}
    //print ???���
    	state.setMainBoxes(mainBoxes);
		for(Box box : mainBoxes) {
			box.printBox();
    		System.out.print(" | "); 
		}
    	System.out.println(" ");
    	return state;
    }

	public simState calcStateMax( simState prevState, int deep, int breadth) {
    	System.out.println("	-------calculating MAX----------");
		int currentMax = -999;
		String moveMax = "";
		Array<Box> mainBoxes = prevState.getMainBoxes();
		Array<simState> stateOptions = new Array<simState>();
		int nBoxesChecked =0;
		for(Box box : mainBoxes){
			if (nBoxesChecked>=breadth) {
				break;
			}
			for(Integer mov : box.connections) {
				int[] coordinates = box.coordinates;
				simState newState = new simState(prevState); //, prevMove);
				if (prevState.prevMov.equals("")) {
					String prevMove = coordinates[0]+":"+coordinates[1]+":"+squaresMovements[mov];
					newState.setPrevMov(prevMove); 
				}
				int score = simulateScore(coordinates,mov, newState);
				newState.setScore(prevState.score - score); //
				stateOptions.add(newState);
				nBoxesChecked++;
			}
			//nBoxesChecked++;
		}
		simState newState = prevState;
		System.out.println("  --initMin--numopts:"+stateOptions.size());
		for(simState optState : stateOptions) {
	    	simState tempState = calcStateMin(optState,deep,breadth);			
	    	System.out.println("  --scorecalculateMin:"+tempState.score+" prevMove:"+optState.prevMov+"--scorecalculateMax:"+optState.score);	
	    	if (tempState.score > currentMax) {
				currentMax = tempState.score;
				newState = tempState;
				moveMax = optState.prevMov;
			}
		}
		System.out.println(" ------------------------------- final max move" + moveMax +"------------------------------------------");
		if(stateOptions.size()>0) {
			return newState;
		}else {
			Box tempBox = newState.getFirstBox();
			newState.printBoard();
			tempBox.printBox();
			String prevMove = tempBox.coordinates[0]+":"+tempBox.coordinates[1]+":"+squaresMovements[tempBox.connections.get(0)];			
			newState.setPrevMov(prevMove);
			return newState;
		}
	}
	
	public simState calcStateMin(simState prevState, int deep, int breadth) {
		System.out.println("	-------calculating MIN----------");
		simState minState = new simState(prevState);
		Array<Box> mainBoxes = prevState.getMainBoxes();
		//int minMove = -1;
		int minScore = 999;
		int nBoxesChecked =0;
		for(Box box : mainBoxes){
			if (nBoxesChecked>=breadth) {
				break;
			}
			for(Integer mov : box.connections) {
				int[] coordinates = box.coordinates;
				simState newState = new simState(prevState);
				//String prevMove = coordinates[0]+":"+coordinates[1]+":"+squaresMovements[mov];
				//newState.setPrevMov(prevMove); 
				int score = simulateScore(box.coordinates,mov, minState);
				
				if ( score <= minScore ) {
					minScore = score;
					minState = newState;
				}
				nBoxesChecked++;
			}
			//nBoxesChecked++;
		}
		if ( mainBoxes.size()<=0 || deep<=1 ) {
			int newScore = prevState.score + minScore;
			minState.setScore(newScore);			
			return minState; 
		} else {
			return calcStateMax(minState, deep - 1, 1);
		}
	}
	
	public int simulateScore(int[] coordinates,int mov, simState curState) {
		int xCoor = coordinates[0];
		int yCoor = coordinates[1];
		System.out.println(" simulating box x:"+xCoor+" y:"+yCoor+" move:"+mov);
		if(mov==UP) 
			xCoor--;
		if(mov==RIGHT) 
			yCoor++;
		if(mov==DOWN) 
			xCoor++;
		if(mov==LEFT) 
			yCoor--;
		return moveBox( xCoor, yCoor, mov, curState, 0);	
	}
	
	public int moveBox(int x, int y, int prevDirection,simState curState, int counter) {
		Box box = curState.getBox(x,y);
		boolean check = (box.getConnections().size() >= 3);
			//System.out.println(" check"+check);
		if (check) {
			return counter;
		}else {			
			int newMove = resolveMove(box.getCoordinates(), box.getConnections() , prevDirection);
			int xCoor = box.getCoordinates()[0];
			int yCoor = box.getCoordinates()[1];
			System.out.println("	resolving Move | move " + newMove + " | coor "+ xCoor+","+yCoor+" | prevMov :"+prevDirection);
			if(newMove==UP) 
				xCoor--;
			if(newMove==RIGHT) 
				yCoor++;
			if(newMove==DOWN) 
				xCoor++;
			if(newMove==LEFT) 
				yCoor--;
			//curState.deleteBox(box.getCoordinates()[0],box.getCoordinates()[1]);			
			return moveBox( xCoor, yCoor, newMove,curState, counter+1);
		}
		//System.out.println("		new Move : "+resolveMove(box.getCoordinates(), prevDirection) );
		//return counter;
	}
	
	public int resolveMove(int[] coor, Array<Integer> connections ,int prevDirection) {
		int newMove = -1;
		for(Integer opt : connections) {
			//System.out.print("		resolving Move | moveop " + opt + " | coor "+ coor[0]+","+coor[1]+" | prevMov :"+prevDirection+" | incomeMov :"+((prevDirection+2)%4));
			if( ((prevDirection+2)%4) != opt ) {
				newMove=opt;
			}
		}
		//System.out.println("");
		return newMove;
	}
	
    @Override
    public Action compute(Percept p) {
        long time = (long)(200 * Math.random());
        numTurn++;
        try{
          //Thread.sleep(time);
        }catch(Exception e){System.out.println(e);}
        if( p.get(Squares.TURN).equals(color) ){
        /** Intelig **/
            String action = "";
            if (numTurn <= this.turnStage) {
            	/*stage 1*/
            	action = randomMove(p);
            } else {
            	/*stage 2*/
            	action = expandNode(p);
            }
            try{
            	System.out.println( "My Move: "+action+" || num turn "+numTurn);
                return new Action(action);
            }catch(Exception e){System.out.println(e);}
        /****/
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }
    
}

