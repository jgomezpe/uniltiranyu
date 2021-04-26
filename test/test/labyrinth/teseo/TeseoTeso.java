package test.labyrinth.teseo;
import java.util.*;


public class TeseoTeso extends SimpleTeseoAgentProgram4{
	
	protected HashMap<String, Boolean> mapa;
	protected Stack<Integer> recorrido;
	protected int x=50;//posición
	protected int y=50;
	protected int o = 0;//orientacion
	
    public TeseoTeso() {
    	mapa = new HashMap<String,Boolean>();
    	recorrido = new Stack<Integer>();
    	mapa.put(x+" "+y, true);
    }
    
    protected void actualizarCoordenada(int a) {
    	switch( o ) {
        case 0:
        	switch(a){
        		case 0: y++; break;
        		case 1: x++; o=1; break;
        		case 2: y--; o=2; break;
        		case 3: x--; o=3; break;
        		case 4: y--; break;
        		case 5: y--; o=3; break;
        		case 6: y--; o=2; break;
        		case 7: y--; o=1; break;
        	}
        break;		
        case 1:
        	switch(a){
        		case 0: x++; break;
        		case 1: y--; o=2; break;
        		case 2: x--; o=3; break;
        		case 3: y++; o=0; break;
        		case 4: x--; break;
        		case 5: x--; o=0; break;
        		case 6: x--; o=3; break;
        		case 7: x--; o=2; break;
        	}
        break;		
        case 2:
        	switch(a){
        		case 0: y--; break;
        		case 1: x--; o=3; break;
        		case 2: y++; o=0; break;
        		case 3: x++; o=1; break;
        		case 4: y++; break;
        		case 5: y++; o=1; break;
        		case 6: y++; o=0; break;
        		case 7: y++; o=3; break;
        	}
        break;		
        case 3:
        	switch(a){
        		case 0: x--; break;
        		case 1: y++; o=0; break;
        		case 2: x++; o=1; break;
        		case 3: y--; o=2; break;
        		case 4: x++; break;
        		case 5: x++; o=2; break;
        		case 6: x++; o=1; break;
        		case 7: x++; o=0; break;
        	}
        break;		
        }
    	
    }
    
    protected boolean haPasado(int a) {
    	boolean paso = false;
    	switch( o ) {
	        case 0:
	        	switch(a){
	        		case 0: paso = mapa.containsKey(x+" "+(y+1)); break;
	        		case 1: paso = mapa.containsKey((x+1)+" "+y); break;
	        		case 2: paso = mapa.containsKey(x+" "+(y-1)); break;
	        		default: paso = mapa.containsKey((x-1)+" "+y); break;
	        	}
	        break;		
	        case 1:
	        	switch(a){
		        	case 0: paso = mapa.containsKey((x+1)+" "+y); break;
	        		case 1: paso = mapa.containsKey(x+" "+(y-1)); break;
	        		case 2: paso = mapa.containsKey((x-1)+" "+y); break;
	        		default: paso = mapa.containsKey(x+" "+(y+1)); break;
	        	}
	        break;		
	        case 2:
	        	switch(a){
		        	case 0: paso = mapa.containsKey(x+" "+(y-1)); break;
	        		case 1: paso = mapa.containsKey((x-1)+" "+y); break;
	        		case 2: paso = mapa.containsKey(x+" "+(y+1)); break;
	        		default: paso = mapa.containsKey((x+1)+" "+y); break; 
	        	}
	        break;	
	        case 3:
	        	switch(a){
		        	case 0: paso = mapa.containsKey((x-1)+" "+y); break;
	        		case 1: paso = mapa.containsKey(x+" "+(y+1)); break;
	        		case 2: paso = mapa.containsKey((x+1)+" "+y); break;
	        		default: paso = mapa.containsKey(x+" "+(y-1)); break; 
	        	}
	        break;		
        }
    	return paso;
    }
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
    	int a;
    	boolean devolvio = false;
        if (MT) return -1;
        if (!PI && !haPasado(3)) a = 3;
        else if (!PF && !haPasado(0)) a = 0;
        else if (!PD && !haPasado(1)) a = 1;
        else if (!haPasado(2)) a = 2;
        else {
        	a = recorrido.pop();
        	devolvio = true;
        }
        
        // Computing r, c, o
        actualizarCoordenada(a);
        if(!devolvio) recorrido.add(4+a);
        mapa.put(x+" "+y, true);
        return a;
    }
}
