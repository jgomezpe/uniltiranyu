/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nsgl.agents.examples.labyrinth.teseo.simple;

/**
 *
 * @author Jonatan
 */
public class TeseoConMatrix extends SimpleTeseoAgentProgram {

	protected boolean[][] m = new boolean[100][100];
	protected int r=50;
	protected int c=50;
	protected int o = 0;
	
    public TeseoConMatrix() {
    	m[r][c] = true;
    }
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
    	int a;
        if (MT) return -1;
        if (!PI) a = 3;
        else if (!PF) a = 0;
        else if (!PD) a = 1;
        else a = 2;
        // Computing r, c, o
        switch( o ) {
        case 0:
        	switch(a){
        		case 0: r--; o=0; break;
        		case 1: c++; o=1; break;
        		case 2: r++; o=2; break;
        		default: c--; o=3; 
        	}
        break;		
        case 1:
        	switch(a){
        		case 0: c++; o=1; break;
        		case 1: r++; o=2; break;
        		case 2: c--; o=3; break;
        		default: r--; o=0; 
        	}
        break;		
        case 2:
        	switch(a){
        		case 0: r--; break;
        		case 1: c++; o=1; break;
        		case 2: r++; o=2; break;
        		default: c--; o=3; 
        	}
        break;		
        case 3:
        	switch(a){
        		case 0: r--; break;
        		case 1: c++; o=1; break;
        		case 2: r++; o=2; break;
        		default: c--; o=3; 
        	}
        break;		
        }
        m[r][c] = true;
        return a;
    }
    
}
