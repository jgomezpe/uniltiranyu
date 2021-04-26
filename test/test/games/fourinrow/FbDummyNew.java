package test.games.fourinrow;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

/**
 *
 * @author FBI
 */
public class FbDummyNew implements AgentProgram {
    protected String color;
    public FbDummyNew(String color ){
        this.color = color;        
    }
    
    @Override
    public Action compute(Percept p) {        
        long time = (long)(100); //* Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}


        if( p.get(FourInRow.TURN).equals(color) ){
        	//boolean flag = false;
        	int i = 0;
        	int j = 0;
            int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
            //nuestro tablero actual de turnos
            int [][] values = new int[n][n];
            int [] vectoFlags = new int[n];
            for (int z = 0; z < n; z++) {
                vectoFlags[z]  = -1;
            }
            // se llena el tablero de turnos
            for (int arrayi = 0; arrayi < n; arrayi++) {
                for (int arrayj = 0; arrayj < n; arrayj++) {
                    if(p.get((arrayi)+":"+arrayj).equals((String)FourInRow.BLACK)){
                        values[arrayi][arrayj]=-1;
                    } else if(p.get((arrayi)+":"+arrayj).equals(oponentColor(color,p))){
                        values[arrayi][arrayj]=1;
                    }
                    else{
                        values[arrayi][arrayj]=0;
                    }
                }
            }

            //se verifica si se pierde o se gana con un movimiento simulado en nuestro tablero de turnos
            for (int k = 0; k < n; k++) {
                for (int l = 0; l < n; l++) {
                    if((k==n-1) || !p.get((k+1)+":"+l).equals((String)FourInRow.SPACE)){
                        vectoFlags[k]=l;
                        //verificamos si se gana con cierto turno en futuro
                        if(fbiFourConnected( 1, values, n,k,l)){
                            return new Action( k+":"+l+":"+color );
                        }else {
                            // se verifica si se pierde con cierto turno en futuro
                            for (int rk = 0; rk < n; rk++) {
                                for (int rl = 0; rl < n; rl++) {
                                    if((rk==n-1) || !p.get((rk+1)+":"+rl).equals((String)FourInRow.SPACE)){
                                        if(!fbiFourConnected( 1, fbiFlagBoard(1,values,k,l), n,rk,rl)){
                                            //
                                            k = (int)(n*Math.random());
                                            /*while(vectoFlags[k] == -1){
                                                k = (int)(n*Math.random());
                                            }*/
                                            l = (int)(n*Math.random());
                                            return new Action( k+":"+l+":"+color );
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


            /*for (int pintari = 0; pintari < n; pintari++) {
                for (int pintarj = 0; pintarj < n; pintarj++) {
                    System.out.print(values[pintari][pintarj] + " ");
                }
                System.out.println("\n");
            }*/

        	/*do {
            //i = (int)(n*Math.random());
            //j = (int)(n*Math.random());
            flag = (i==n-1) || !p.getAttribute((i+1)+":"+j).equals((String)FourInRow.SPACE);
        	}
            while( !flag );
            //System.out.println(i);
            //System.out.println(j);
            //System.out.println(oponentColor(color,p));
            //System.out.println(color+" : "+isWinningMove(i,j,color,p, n));*/
            

            //return new Action( i+":"+j+":"+color );
        }
        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }
    //gets opponent color
    String oponentColor (String color, Percept p) {
    	String rivalColor ="";
    	int i = 7;
    	for(int j = 0; j < 8; j++) {
    		if(!p.get((i)+":"+j).equals(color) && !p.get((i)+":"+j).equals((String)FourInRow.SPACE)) {
    			rivalColor = (String) p.get((i)+":"+j);
    			break;
    		}
    	}
    	
    	return rivalColor;
    }
    //check if row, col is a winning move
    boolean isWinningMove(int row, int col, String color, Percept p, int n){
      // check for vertical alignments
    	if(row <(n-3)) {
    		if( p.get((row+1)+":"+(col)).equals(color) 
    			&& p.get((row+2)+":"+(col)).equals(color) 
    			&& p.get((row+3)+":"+(col)).equals(color))
    			return true;
    	}
    	
    	int row2 = (n-1)-row;
      for(int dy = -1; dy <=1; dy++) {    // Iterate on horizontal (dy = 0) or two diagonal directions (dy = -1 or dy = 1).
        int nb = 0;                       // counter of the number of stones of current player surrounding the played stone in tested direction.
        for(int dx = -1; dx <=1; dx += 2) {     	// count continuous stones of current player on the left, then right of the played column.
        	int x = col+dx, y = row2+dx*dy;
        	while(x >= 0 && x < (n) && y >= 0 && y < (n) && p.get(((n-1)-y)+":"+x).equals(color)) {
        		x += dx;
                y += dx*dy;
                nb++;
        	}
          }
        if(nb >= 2) return true; // there is an aligment if at least 3 other stones of the current user 
                                 // are surronding the played stone in the tested direction.
      }
      return false;
    }

    /*boolean isFull(int row, int col, String color, Percept p, int n){
        for (int actualrow = 0; actualrow < n; actualrow++) {
            if((p.getAttribute((0)+":"+(col)).equals(color) || !p.getAttribute((0)+":"+(col)).equals(color))
                    ||(p.getAttribute((1)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
                    ||(p.getAttribute((2)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
                    ||(p.getAttribute((3)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
                    ||(p.getAttribute((4)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
                    ||(p.getAttribute((5)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
                    ||(p.getAttribute((6)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
                    ||(p.getAttribute((7)+":"+(col)).equals(color) || (oponentColor(color,p)==color))
            ){
                return true;
            }
        }
        return false;
    }*/

    public boolean fbiFourConnected(int player, int[][] tablero, int n, int xrow, int ycol){

        tablero[xrow][ycol] = player;

        // verificar horizontal
        for (int j = 0; j<n-3 ; j++ ){
            for (int i = 0; i<n; i++){
                if (tablero[i][j] == player && tablero[i][j+1] == player && tablero[i][j+2] == player && tablero[i][j+3] == player){
                    return true;
                }
            }
        }

        // verificar vertical
        for (int i = 0; i<n-3 ; i++ ){
            for (int j = 0; j<n; j++){
                if (tablero[i][j] == player && tablero[i+1][j] == player && tablero[i+2][j] == player && tablero[i+3][j] == player){
                    return true;
                }
            }
        }

        // diagonal hacia arriba
        for (int i=3; i<n; i++){
            for (int j=0; j<n-3; j++){
                if (tablero[i][j] == player && tablero[i-1][j+1] == player && tablero[i-2][j+2] == player && tablero[i-3][j+3] == player)
                    return true;
            }
        }

        // diagonal hacia abajo
        for (int i=3; i<n; i++){
            for (int j=3; j<n; j++){
                if (tablero[i][j] == player && tablero[i-1][j-1] == player && tablero[i-2][j-2] == player && tablero[i-3][j-3] == player)
                    return true;
            }
        }

        return false;
    }

    int [][]fbiFlagBoard(int player, int[][] tablero,int xrow, int ycol){
        tablero[xrow][ycol] = player;
        return  tablero;
    }
}