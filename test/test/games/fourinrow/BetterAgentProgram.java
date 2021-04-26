package test.games.fourinrow;

import java.util.Random;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

public class BetterAgentProgram implements AgentProgram
{
	
    Random r;
	private String color;
	private FourInRowGame myGame;
	boolean iAmWhite;
    public BetterAgentProgram( String color ){
        this.color = color;
        this.iAmWhite = IAmWhite(color);
        this.myGame = null; 
        r=new Random();
    }
        
    public boolean IAmWhite(String color) { // Es blanco o negro
    	boolean iAmWhite = true;
    	if (color == "black") {
    		iAmWhite = true;
    	}else if (color == "white") {
    		iAmWhite = false;
    	}
    	return iAmWhite;
    }
 
    
    @Override
    public Action compute(Percept p) {
	if(this.myGame == null ) {
	    int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
	    this.myGame = new FourInRowGame(n, n);
	}
    	int move = 0;
        int iCanWin = canWin(iAmWhite);
        int theyCanWin = canWin(!iAmWhite);
    	long time = (long)(300);
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(FourInRow.TURN).equals(color) ){
        	int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
        	int i = (int)(1);
            int j = (int)(4);
            boolean flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
            
            while( !flag ){
                
	            if(iCanWin >= 0) {//Si puede ganar mueve en la posici�n en la que puede ganar
	                move=iCanWin;
	                i = move;
	                
	                flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
	                return new Action( i+":"+j+":"+color );
	            } else if(theyCanWin >= 0) { // si el oponente puede ganar lo bloquea para que no gane
	                move = theyCanWin;
	                j = move;
	                flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
	                return new Action( i+":"+j+":"+color );
	                
	            } else { // movimiento random
	            	flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
	                return randomMove(p);
	            }
	            
            }
        }
        return new Action(FourInRow.PASS);
    }
    
    
    public Action randomMove(Percept p) { //Movimiento random igual que el Dummy
    	long time = (long)(500*Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(FourInRow.TURN).equals(color) ){
        	
        	int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
            int i = (int)(n*Math.random());
            int j = (int)(n*Math.random());
            boolean flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
            while( !flag ){
                i = (int)(n*Math.random());
                j = (int)(n*Math.random());
                flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
            }
            return new Action( i+":"+j+":"+color );
        }
        return new Action(FourInRow.PASS);
    }
    
    public void moveOnColumn(int i) { // Moverse a la columna i 
    	boolean iAmWhite = IAmWhite(color);
    	FourInRowSlot topEmptySlot = getTopEmptySlot(myGame.getColumn(i));
        if(!(topEmptySlot==null)) {
            if(iAmWhite) {
                topEmptySlot.addRed();
            } else {
                topEmptySlot.addYellow();
            }
        }
    }
    
    public FourInRowSlot getTopEmptySlot(FourInRowColumn column) { //Obtener una casilla vacia
        int topEmptySlot=-1;
        for(int i = 0; i<column.getRowCount(); i++) {
            if(!column.getSlot(i).getIsFilled()) {
                topEmptySlot=i;
            }
        }
        if(topEmptySlot<0) {
            return null;
        } else {
            return column.getSlot(topEmptySlot);
        }
    }
    
    public int canWin(boolean red) {// Check si puede ganar 
        for(int i = 0; i < myGame.getColumnCount(); i++) { //check columna i
            int tei = getTopEmptyIndex(myGame.getColumn(i));
            if(tei > -1) { //tiene un slot vacio
                if(tei < myGame.getRowCount() - 3) { // Si hay 3 fichas debajo de esta casilla
                    if(myGame.getColumn(i).getSlot(tei+1).getIsRed()==red && myGame.getColumn(i).getSlot(tei+2).getIsRed()==red && myGame.getColumn(i).getSlot(tei+3).getIsRed()==red) { // if a column win is available here
                        return i;
                    }
                }
                if(i < myGame.getColumnCount() - 3) { //si hay 3 fichas al lado derecho de esta casilla
                    if(checkIfEqual(red, myGame.getColumn(i+1).getSlot(tei), myGame.getColumn(i+2).getSlot(tei), myGame.getColumn(i+3).getSlot(tei))) {
                        return i;
                    }
                }
                if(i < myGame.getColumnCount() - 2 && i > 0) { //si hay dos fichas a la derecha y una a la izquierda  
                    if(checkIfEqual(red, myGame.getColumn(i-1).getSlot(tei), myGame.getColumn(i+1).getSlot(tei), myGame.getColumn(i+2).getSlot(tei))) {
                        return i;
                    }
                }
                if(i < myGame.getColumnCount() - 1 && i > 1) { // si hay una ficha a la izquierda y dos a la derecha de la casilla
                    if(checkIfEqual(red, myGame.getColumn(i-1).getSlot(tei), myGame.getColumn(i+1).getSlot(tei), myGame.getColumn(i-2).getSlot(tei))) {
                        return i;
                    }
                }
                if(i > 2) { // si hay 3 fichas a la izquierda
                    if(checkIfEqual(red, myGame.getColumn(i-1).getSlot(tei), myGame.getColumn(i-3).getSlot(tei), myGame.getColumn(i-2).getSlot(tei))) {
                        return i;
                    }
                }
                if(i < myGame.getColumnCount() - 3 && tei < myGame.getRowCount() - 3) { //si hay tres fichas hacia abajo a la derecha
                    if(checkIfEqual(red, myGame.getColumn(i+1).getSlot(tei+1), myGame.getColumn(i+3).getSlot(tei+3), myGame.getColumn(i+2).getSlot(tei+2))) {
                        return i;
                    }
                }
                if(i < myGame.getColumnCount() && i > 2 && tei < myGame.getRowCount() && tei > 2) { // si hay tres fichas hacia diagonal a la derecha
                    if(checkIfEqual(red, myGame.getColumn(i-1).getSlot(tei-1), myGame.getColumn(i-2).getSlot(tei-2), myGame.getColumn(i-3).getSlot(tei-3))) {
                        return i;
                    }
                }
                if(i > 2 && i < myGame.getColumnCount() && tei < myGame.getRowCount() - 3 && tei >= 0) { // si hay tres fichas hacia abajo a la izquierda
                    if(checkIfEqual(red, myGame.getColumn(i-1).getSlot(tei+1), myGame.getColumn(i-2).getSlot(tei+2), myGame.getColumn(i-3).getSlot(tei+3))) {
                        return i;
                    }
                }
                if(i >= 0 && i < myGame.getColumnCount() - 3 && tei < myGame.getRowCount() && tei > 2) { //si hay tres fichas diagonal a la izquierda
                    if(checkIfEqual(red, myGame.getColumn(i+3).getSlot(tei-3), myGame.getColumn(i+2).getSlot(tei-2), myGame.getColumn(i+1).getSlot(tei-1))) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    public boolean checkIfEqual(boolean isRed, FourInRowSlot slot1, FourInRowSlot slot2, FourInRowSlot slot3) { // Verifica si tres fichas son del mismo color
        if(slot1.getIsFilled() && slot2.getIsFilled() && slot3.getIsFilled()) {
            if(slot1.getIsRed()==isRed && slot2.getIsRed()==isRed && slot3.getIsRed()==isRed) {
                return true;
            }
        }
        return false;
    }
    public int getTopEmptyIndex(FourInRowColumn column) { // ir a una casilla vacia
        int topEmptySlot=-1;
        for(int i = 0; i<column.getRowCount(); i++) {
            if(!column.getSlot(i).getIsFilled()) {
                topEmptySlot=i;
            }
        }
        return topEmptySlot;
    }
    
   

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}