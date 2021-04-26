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
public class DeepBlue implements AgentProgram {
    protected String color;
    public DeepBlue( String color ){
        this.color = color;
    }

    @Override
    public Action compute(Percept p) {
        long time = (long)(200 * Math.random());
        try{
            Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(Squares.TURN).equals(color) ){
            int size = Integer.parseInt((String)p.get(Squares.SIZE));
            Array<String> todasLasPosibilidades = new Array<String>();//Se crea vector que guarda todas las posibles paredes
            todasLasPosibilidades.add(Squares.RIGHT);
            todasLasPosibilidades.add(Squares.LEFT);
            todasLasPosibilidades.add(Squares.TOP);
            todasLasPosibilidades.add(Squares.BOTTOM);
            System.out.println("Voy yo, color: "+color);
            int contadorDeJuego = 0; //Cuenta la cantidad de veces que se ha buscado un cuadro con mas de 2 paredes dosponibles
            int i = 0;
            int j = 0;
            int paredes = 0; //Cuenta las paredes disponibles de un cuadro (i,j)
            Array<String> v = new Array<String>();
            Array<String> mejor = new Array<String>();
            while(v.size()==0){
                i = (int)(size*Math.random());
                j = (int)(size*Math.random());
                if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE)){
                    mejor.add(Squares.LEFT);
                    paredes += 1; } //Se cuentan cuantas paredes disponibles hay en este cuadro coordenadas (i,j)
                if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE)){
                    mejor.add(Squares.TOP);
                    paredes += 1;}
                if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE)){
                    mejor.add(Squares.BOTTOM);
                    paredes += 1;}
                if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE)){
                    mejor.add(Squares.RIGHT);
                    paredes += 1;}

                if(paredes>2)
                    v = mejor;//Se envian los lados disponibles ya que no compromete el cierre en el proximo turno
                else if(contadorDeJuego>(size*size*2) && paredes>0) { //Si no se encuentra despues del doble de casillas, se juega cualquier casilla
                    v = todasLasPosibilidades;
                    contadorDeJuego = 0;
                }
                paredes = 0;
                contadorDeJuego += 1;
                //fin del while
            }
            try{
                String move = v.get((int)(Math.random()*v.size()));
                return new Action( i+":"+j+":"+move);
            }catch(Exception e){}
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }

}

