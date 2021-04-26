package test.labyrinth.teseo;


//import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import uniltiranyu.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

public class InteligLabyrinth extends SimpleTeseoAgentProgram {
	/*
	 labyrinthSquares: Grafo de las casillas conocidas por el agente.
     currentX, currentY: Posicion del laberinto donde se encuentra el agente.
     direction: Direcci�n con la que el agente entra a la casilla actual.
    */
    public Hashtable<Integer, Hashtable> labyrinthSquares;
    public int currentX;
    public int currentY;
	public int direction;
    
	public InteligLabyrinth() {
		this.labyrinthSquares = new Hashtable<Integer, Hashtable>();
		this.currentX = 0;
		this.currentY = 0;
		this.direction = 0;
		System.out.println("createAgent");	
	}
	
	public int checkVisited(int X, int Y) {
		if ( this.labyrinthSquares.containsKey(X) ) {
			Hashtable<Integer, Node> axisCheck = this.labyrinthSquares.get(X);
			if (axisCheck.containsKey(Y) ){
				return axisCheck.get(Y).getTimesVisited();
			}else {return 0;}
		}else {return 0;}
	}
	
	public int checkWalls(int X, int Y) {
		if ( this.labyrinthSquares.containsKey(X) ) {
			Hashtable<Integer, Node> axisCheck = this.labyrinthSquares.get(X);
			if (axisCheck.containsKey(Y) ){
				return axisCheck.get(Y).getNumWalls();
			}else {return 0;}
		}else {return 0;}
	}
	
	//Retorna si la casilla ha sido visitada dada la direccion observada.
	public int checkNeighbor(int direction) {		
		switch (direction) {
			case 3: 
				return checkVisited(currentX-1, currentY);
			case 0:
				return checkVisited(currentX, currentY-1);
			case 1: 
				return checkVisited(currentX+1, currentY);
			case 2:
				return checkVisited(currentX, currentY+1);
			default:
				return -1;
		}
	}
	
	//Retorna el numero de paredes de la casilla dada la direccion observada.
	public int checkWalls(int direction) {		
		switch (direction) {
			case 3: 
				return checkWalls(currentX-1, currentY);
			case 0:
				return checkWalls(currentX, currentY-1);
			case 1: 
				return checkWalls(currentX+1, currentY);
			case 2:
				return checkWalls(currentX, currentY+1);
			default:
				return -1;
		}
	}
	
	//Convierte la direcci�n relativa del agente a la direcci�n del tablero
	public int resolveDirection(int realDirection, int newDirection) {
		return (realDirection+newDirection)%4;
	}
	
	public int boolToInt(boolean bool) {
		return bool ? 1:0;
	}
	
	@Override
	public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
		/*
		 * newdirection: Direcci�n donde se mover� el agente
		 */
		int newdirection = -1;
		int numVisits = -1;
		
		//Numero de paredes de la casilla actual
		int numWalls = boolToInt(PF)+boolToInt(PD)+boolToInt(PA)+boolToInt(PI);
		
		//Visita la casilla (la agrega al grafo en caso que no haya sido visitada)
		if ( this.labyrinthSquares.containsKey(this.currentX) ) {
			Hashtable<Integer, Node> axisCheck = this.labyrinthSquares.get(this.currentX);
			if (axisCheck.containsKey(this.currentY) ){
				axisCheck.get(this.currentY).visitNode();
				//axisCheck.get(this.currentY).printTimesVisited();
			}else {
				Node square = new Node(this.currentX,this.currentY);
				square.setNumWalls(numWalls);
				square.visitNode();
				axisCheck.put(this.currentY,square);
			}
		}else {
			Hashtable<Integer, Node> axis= new Hashtable<Integer, Node>(); 
			Node square = new Node(this.currentX,this.currentY);
			axis.put(currentY,square);
			square.setNumWalls(numWalls);
			square.visitNode();
			this.labyrinthSquares.put(currentX,axis);
		};
		/*
		 * options<direcci�n,visitas>: Guarda las direcciones posibles a donde moverse, 
		 * 			junto con el numero de veces que se ha vistado cada una.
		 */
	    Hashtable<Integer, Integer> options = new Hashtable<Integer, Integer>();
		//META
		if (MT) return -1;
		//Revisa si es posible moverse a la casillla obervada, y las veces que se ha visitado
		if (!PI) { 
        	numVisits = checkNeighbor(resolveDirection(direction,3));
			numWalls = checkWalls(resolveDirection(direction,3));
			if (numWalls<3) options.put(3,numVisits);
		}
        if (!PF) {
        	numVisits = checkNeighbor(resolveDirection(direction,0));
        	numWalls = checkWalls(resolveDirection(direction,0));
        	if (numWalls<3) options.put(0,numVisits);
		}
        if (!PD) {
        	numVisits = checkNeighbor(resolveDirection(direction,1));
        	numWalls = checkWalls(resolveDirection(direction,1));
        	if (numWalls<3) options.put(1,numVisits);
        }
        if (!PA) {;
        	numVisits = checkNeighbor(resolveDirection(direction,2));
        	numWalls = checkWalls(resolveDirection(direction,2));
        	if (numWalls<3) options.put(2,numVisits);
        }	 
        
        
        /*
         * DECIDE a donde moverse. Elige la direcci�n de la casilla que se ha visitado menos veces
         * (Atr�s como �ltima opci�n}
         */
        Set<Integer> setOfOptions = options.keySet();
        int minVisits = 9999;
        for(Integer direction : setOfOptions) {
        	//System.out.println("eval dir "+direction+" times "+options.get(direction));
        	if (minVisits > options.get(direction)) {
        		minVisits = options.get(direction);
        		newdirection = direction;
        	}
        /*	if (minVisits == options.get(direction) && direction !=2) {
            	minVisits = options.get(direction);
        		newdirection = direction;
        	}
        */
        }
        
        /*
         * Actualiza la posici�n de la casilla a donde se mover�
         */
	  	//System.out.println("	 realdir-> " +Integer.toString(direction) +" -- ndir-> "+Integer.toString(newdirection) );
 		switch (resolveDirection(newdirection,direction)) {
			case 3: 
				this.currentX--;
				break;
			case 0:
				this.currentY--;
				break;
			case 1: 
				this.currentX++;
				break;
			case 2:
				this.currentY++;
				break;
		};
        
		/*
		 * Retorna la direcci�n a donde se mover� 
		 */
        direction = resolveDirection(newdirection,direction);
    	return newdirection;
	}
}
