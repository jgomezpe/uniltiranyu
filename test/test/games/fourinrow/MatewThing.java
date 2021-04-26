package test.games.fourinrow;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.HashMap;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

import java.util.Arrays;

public class MatewThing implements AgentProgram {
    public static int[] ordenColumna;
    HashMap<BitSet[], Integer> transpositionTable = new HashMap<>(400);
    String color;
    Position posicion;
    Boolean firsTime=true;
    int deep = 8;

    MatewThing(String color) {
        this.color = color;
        //System.out.println("si xd");
    }

    private void updateBoard(Percept p) {

        for(int i= 0;i<posicion.WIDTH;i++) {
            if (posicion.techo[i]< posicion.HEIGHT) {
                if (!p.get(posicion.HEIGHT-1-posicion.techo[i]+":"+i).equals(FourInRow.SPACE)) {
                    posicion.noVacio[i].set(posicion.techo[i]);
                    posicion.techo[i]++;
                }
            }
        }
    }

    int solve(Position P) {
        int min = -(P.WIDTH * P.HEIGHT - P.nbMoves()) / 2;
        int max = (P.WIDTH * P.HEIGHT + 1 - P.nbMoves()) / 2;

        if (P.canWinNext()) {

            return max;
        }
        while (min < max) {
            int med = min + (max - min) / 2;
            if (med <= 0 && min / 2 < med) {
                med = min / 2;
            } else if (med >= 0 && max / 2 > med) {
                med = max / 2;
            }

            int r = negamax(P, med, med + 1, deep);
            if (r <= med) {
                max = r;
            } else {
                min = r;
            }
        }
        return min;
    }

    int puntajes(Position p) {

        int[] puntajes = new int[p.WIDTH];
        Arrays.fill(puntajes, Integer.MIN_VALUE);
        for (int i = 0; i < puntajes.length; i++) {

            if (p.canPlay(ordenColumna[i])) {
                
                if (p.isWinningMove(ordenColumna[i])){
                    puntajes[ordenColumna[i]] = (p.WIDTH*p.HEIGHT + 1 - p.nbMoves()) / 2;
                } else {

                    p.play(ordenColumna[i]);
                    puntajes[ordenColumna[i]] = -solve(p);
                    p.dPlay(ordenColumna[i]);

                }
                //System.out.println(ordenColumna[i] +":" + puntajes[ordenColumna[i]]);
            }
        }
        int max=Integer.MIN_VALUE;
        int pos=-1;
        //System.out.println("Asdasdads");
        
        for( int i =0;i<puntajes.length;i++){
            if( puntajes[ordenColumna[i]]>max){
                pos=ordenColumna[i];
                max=puntajes[ordenColumna[i]];
            }
        }
        //transpositionTable.clear();
        posicion.jugador[pos].set(posicion.techo[pos]);
        posicion.noVacio[pos].set(posicion.techo[pos]);
        posicion.techo[pos]++;
        return pos;
    }

    int negamax(Position P, int alfa, int beta, int profundidad) {
        if (P.nbMoves() == P.HEIGHT * P.WIDTH || profundidad == 0) {// aca poner el límite de altura de busqueda
            return 0;
        }

        ArrayDeque<Integer> movPos = P.movimientoForzado();// lista de movimientos que puede hacer u obligado a hacer
        if (movPos.size() == 0) {// perdio :'v
            return -(P.WIDTH * P.HEIGHT - P.nbMoves()) / 2;
        }
        int min = -(P.WIDTH * P.HEIGHT - 2 - P.nbMoves()) / 2;
        if (alfa < min) {
            alfa = min;
            if (alfa >= beta)
                return alfa;
        }
        int max = (P.WIDTH * P.HEIGHT - 1 - P.nbMoves()) / 2;
        if (transpositionTable.containsKey(P.key())) {
            max = transpositionTable.get(P.key()) + P.MIN_SCORE - 1;
        }

        if (beta > max) {
            beta = max;
            if (alfa >= beta) {
                return beta;
            }
        }

        for (int columna = 0; columna < P.WIDTH; columna++) {

            if (P.canPlay(ordenColumna[columna]) && movPos.contains(ordenColumna[columna])) {
                // Position P2 = new Position(P);// modificar para que funcione mejor en java

                P.play(ordenColumna[columna]);
                int score = -negamax(P, -beta, -alfa, profundidad-1);
                P.dPlay(ordenColumna[columna]);
                if (score >= beta) {
                    return score;
                }
                if (score > alfa) {
                    alfa = score;
                }
            }
        }

        transpositionTable.put(P.key(), alfa - P.MIN_SCORE + 1);
        return alfa;
    }

    @Override
    public Action compute(Percept p) {

       if(firsTime){
           int m = Integer.parseInt((String) p.get(FourInRow.SIZE));
           this.firsTime=false;
           this.posicion = new Position(m);
           ordenColumna = new int[m];
           for (int i = 0; i < m; i++) {
               ordenColumna[i] = m / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
           }
       }
        if (p.get(FourInRow.TURN).equals(color)) {
            updateBoard(p);
            int columna = this.puntajes(posicion);
            return new Action((int)(posicion.WIDTH-posicion.techo[columna]) + ":" + columna + ":" + color);

        }

        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {

    }
}
