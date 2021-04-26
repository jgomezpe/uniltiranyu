package test.labyrinth.teseo;
import uniltiranyu.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;;
/**
 * @author Grupo 7
 */
public class TeseoTest extends SimpleTeseoAgentProgram {
	protected int[][][] m = new int[100][100][5];
	protected int r;
	protected int c;	
	protected int o;
	protected int count;
	
	protected static int in = 0;	// has been in the box	m[][][0]	true: count		false: 0
	protected static int I = 1;		// wall on the left		m[][][1]	true: 1			false: 0
	protected static int F = 2;		// wall in front		m[][][2]	true: 1			false: 0
	protected static int D = 3;		// wall on the right	m[][][3]	true: 1			false: 0
	protected static int A = 4;		// wall bellow			m[][][4]	true: 1			false: 0
		
    public TeseoTest() {
    	r = 50; c = 50; o = 0; count = 1;
    	m[r][c][in] = count;
    }
    
    // Are there roads that I have not traveled?
    public boolean roadNotTraveled() { 	
    	boolean result = true;    	
    	// Is there a wall or has it been there
    	boolean left = ((m[r][c-1][in] != 0) || (m[r][c][I] > 0));
    	boolean up = ((m[r+1][c][in] != 0) || (m[r][c][F] > 0 ));
    	boolean right = ((m[r][c+1][in] != 0) || (m[r][c][D] > 0));
    	boolean down = ((m[r-1][c][in] != 0) || (m[r][c][A] > 0));
    	
    	if(left && up && right && down)
    		result = false;   	    	
    	return result;
    }
    
    public void markWalls(boolean PI, boolean PF, boolean PD, boolean PA){
    	if(PI) {
    		m[r][c][I] = 1;
    		m[r][c-1][D] = 1;
    	}
    	if(PF) {
    		m[r][c][F] = 1;
    		m[r+1][c][A] = 1;
    	}    	
    	if(PD) {
    		m[r][c][D] = 1;
    		m[r][c+1][I] = 1;
    	}    	
    	if(PA) {
    		m[r][c][A] = 1;
    		m[r-1][c][F] = 1;
    	}   	
    }
            
    public int getWay() {
    	int a = -1;
    	if(roadNotTraveled()) {
    		count ++;
    		switch( o ) {
            case 0:		// head up	
        		if((m[r][c-1][in] == 0) && (m[r][c][I] == 0)) {
        			m[r][c-1][in] = count;
        			a = 3;
        		}else if((m[r+1][c][in] == 0) && (m[r][c][F] == 0)) {
        			m[r+1][c][in] = count;
        			a = 0;		
        		}else if((m[r][c+1][in] == 0) && (m[r][c][D] == 0)) {
        			m[r][c+1][in] = count;
        			a = 1;
        		}else if((m[r-1][c][in] == 0) && (m[r][c][A] == 0)){
        			m[r-1][c][in] = count;
        			a = 2;
        		}
            break;	
            case 1:		// head right
        		if((m[r+1][c][in] == 0) && (m[r][c][F] == 0)) {
        			m[r+1][c][in] = count;
        			a = 3;
        		}else if((m[r][c+1][in] == 0) && (m[r][c][D] == 0)) {
        			m[r][c+1][in] = count;
        			a = 0;
        		}else if((m[r-1][c][in] == 0) && (m[r][c][A] == 0)) {
        			m[r-1][c][in] = count;
        			a = 1;
        		}else if((m[r][c-1][in] == 0) && (m[r][c][I] == 0)){
        			m[r][c-1][in] = count;
        			a = 2;
        		}
            break;		
            case 2:		// head down
            	if((m[r][c+1][in] == 0) && (m[r][c][D] == 0)) {
            		m[r][c+1][in] = count;
        			a = 3;
        		}else if((m[r-1][c][in] == 0) && (m[r][c][A] == 0)) {
        			m[r-1][c][in] = count;
        			a = 0;
        		}else if((m[r][c-1][in] == 0) && (m[r][c][I] == 0)) {
        			m[r][c-1][in] = count;
        			a = 1;
        		}else if((m[r+1][c][in] == 0) && (m[r][c][F] == 0)){
        			m[r+1][c][in] = count;
        			a = 2;
        		}
            break;		
            case 3:		// head left
            	if((m[r-1][c][in] == 0) && (m[r][c][A] == 0)) {
        			m[r-1][c][in] = count;
        			a = 3;
        		}else if((m[r][c-1][in] == 0) && (m[r][c][I] == 0)) {
        			m[r][c-1][in] = count;
        			a = 0;
        		}else if((m[r+1][c][in] == 0) && (m[r][c][F] == 0)) {
        			m[r+1][c][in] = count;
        			a = 1;
        		}else if((m[r][c+1][in] == 0) && (m[r][c][D] == 0)) {
        			m[r][c+1][in] = count;
        			a = 2;
        		}
            break;
            }
    	}else {
    		switch( o ) {
            case 0:		// head up            	
            	if((m[r][c][I] == 0) && (m[r][c-1][in] == (count - 1)))
        			a = 3;
        		else if((m[r][c][F] == 0) && (m[r+1][c][in] == (count - 1)))
        			a = 0;		
        		else if((m[r][c][D] == 0) && (m[r][c+1][in] == (count - 1)))
        			a = 1;
        		else if((m[r][c][A] == 0) && (m[r-1][c][in] == (count - 1)))
        			a = 2;
            break;	
            case 1:		// head right
        		if((m[r][c][F] == 0) && (m[r+1][c][in] == (count - 1)))
        			a = 3;
        		else if((m[r][c][D] == 0) && (m[r][c+1][in] == (count - 1)))
        			a = 0;
        		else if((m[r][c][A] == 0) && (m[r-1][c][in] == (count - 1)))
        			a = 1;
        		else if((m[r][c][I] == 0) && (m[r][c-1][in] == (count - 1)))
        			a = 2;
            break;		
            case 2:		// head down
            	if((m[r][c][D] == 0) && (m[r][c+1][in] == (count - 1)))
        			a = 3;
        		else if((m[r][c][A] == 0) && (m[r-1][c][in] == (count - 1)))
        			a = 0;
        		else if((m[r][c][I] == 0) && (m[r][c-1][in] == (count - 1)))
        			a = 1;
        		else if((m[r][c][F] == 0) && (m[r+1][c][in] == (count - 1)))
        			a = 2;
            break;		
            case 3:		// head left
            	if((m[r][c][A] == 0) && (m[r-1][c][in] == (count - 1)))
        			a = 3;
        		else if((m[r][c][I] == 0) && (m[r][c-1][in] == (count - 1)))
        			a = 0;
        		else if((m[r][c][F] == 0) && (m[r+1][c][in] == (count - 1)))
        			a = 1;
        		else if((m[r][c][D] == 0) && (m[r][c+1][in] == (count - 1)))
        			a = 2;
            break;		
            }
    		count--;
    	}
    	return a;
    }
    
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {	
    	if (MT || ((r == 50) && (c == 50) && (!roadNotTraveled())))
    		return -1;
    	
    	int a = -1;
    	
    	switch(o) {
    	case 0:		// head up
    		markWalls(PI, PF, PD, PA);
    		break;
    	case 1:		// head right
    		markWalls(PA, PI, PF, PD);
    		break;
    	case 2:		// head down
    		markWalls(PD, PA, PI, PF);
    		break;
    	case 3:		// head left
    		markWalls(PF, PD, PA, PI);
    		break;
    	}
    	a = getWay();
    	
    	// a: means the turn at that time
    	// o: indicates the agent orientation for the next iteration
        switch( o ) {
        case 0:		// head up
        	switch(a){
        		case 0: r++; o=0; break;
        		case 1: c++; o=1; break;
        		case 2: r--; o=2; break;
        		case 3: c--; o=3; break;
        	}
        break;		
        case 1:		// head right
        	switch(a){
        		case 0: c++; o=1; break;
        		case 1: r--; o=2; break;
        		case 2: c--; o=3; break;
        		case 3: r++; o=0; break;
        	}
        break;		
        case 2:		// head down
        	switch(a){
        		case 0: r--; o=2;break;
        		case 1: c--; o=3; break;
        		case 2: r++; o=0; break;
        		case 3: c++; o=1; break; 
        	}
        break;		
        case 3:		// head left
        	switch(a){
        		case 0: c--; o=3;break;
        		case 1: r++; o=0; break;
        		case 2: c++; o=1; break;
        		case 3: r--; o=2; break;
        	}
        break;		
        }
        return a;
    }
}
