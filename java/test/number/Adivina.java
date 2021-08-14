package test.number;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;

public class Adivina implements AgentProgram{
    protected int superior;
    protected int inferior;
    protected int numero;
    
    @Override
    public Action compute(Percept p) {
	String c = (String)p.get("codigo");
	if(c.equals("iniciar")) {
	    superior = 101;
	    inferior = -1;
	    numero = (inferior + superior)/2;
	    return new Action(""+numero);
	}else if(c.equals("menor")) {
	    superior = numero;
	    numero = (inferior + superior)/2;
	    return new Action(""+numero);
	}else if(c.equals("mayor")) {
	    inferior = numero;
	    numero = (inferior + superior)/2;
	    return new Action(""+numero);
	}else return new Action("terminar");
    }

    @Override
    public void init() {
	    superior = 101;
	    inferior = -1;
	    numero = inferior + (int)((superior-inferior)*Math.random());
    }
    
    public static void main( String[] args ) {
	Adivina smith = new Adivina();
	int a = 49;
	System.out.println("Número que trata de adivinar smith:" + a );
	Percept percepcion = new Percept();
	percepcion.set("codigo", "iniciar");
	boolean bandera = true;
	do {
	    Action accion = smith.compute(percepcion);
	    try{
		int k = Integer.parseInt(accion.getCode());
		System.out.println("Número que pregunta smith:" + k );
		if( a<k ) {
			percepcion.set("codigo", "menor");
		}else {
		    if( a>k ) {
			percepcion.set("codigo", "mayor");
		    }else {
			percepcion.set("codigo", "igual");			
		    } 
		}
	    }catch( NumberFormatException e ) {
		bandera = false;
	    }
	}while(bandera);
    }
}