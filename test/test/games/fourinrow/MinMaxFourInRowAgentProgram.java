/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.fourinrow;

import java.util.*;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

public class MinMaxFourInRowAgentProgram implements AgentProgram {
    protected String color;
    private int depth;
    private boolean firstTurn=true;

    public MinMaxFourInRowAgentProgram(String color) {
        this.color = color;
        this.depth = 10;
    }

    @Override
    public Action compute(Percept p) {
        
        if (p.get(FourInRow.TURN).equals(color)) { // en este if el agente comprueba si es su turno
            int n = Integer.parseInt((String) p.get(FourInRow.SIZE)); // se obtiene el tamano del tablero
           
            int firstTurnX = n-1; // optimizacion primera jugada
            int firstTurnY = (int) (Math.floor(n / 2));

            int[][] board = new int[n][n]; //se crea un tablero representativo
            System.out.println(color); 
            for (int j = 0; j < n; j++) { //for para recorrer el tablero de izq a derecha
                for (int i = n - 1; i >= 0; i--) { // for para recorrer el tablero de abajo hacia arriba
                    Object temp = p.get(i + ":" + j); // se llena el tablero representativo con los datos del juego
                    if (temp.equals("white")) {  //las movidas del jugador blanco se representan con un 1
                        board[i][j] = 1;
                        System.out.print(1 + "\t");
                    } else if (temp.equals("black")) {// las movidas del jugador negro se representan con -1
                        board[i][j] = -1;
                        System.out.print(-1 + "\t");
                    } else { //si esta vacio se representa con un 0
                        i = n;
                        j++;
                        System.out.println(0);
                        if (j == n) {
                            break;
                        }
                    }
                }
            }

            int acc = 0;
            for (int k = 0; k < n; k++) {//detecta el numero de fichas
                if (board[n - 1][k] != 0) {
                    acc++;
                }
            }
            if (acc <= 1) {
                this.depth = 10 + ((10 - n) / 2); //profundidad iterativa
            }

            if (Minimax.dimensions != board.length) { // verifica si cambio el tamano del tablero
                Minimax.dimensions = board.length;
                Minimax.transposition = new HashMap<>();  
            }
            Minimax.State s = new Minimax.State(new int[] { n, n }, color.equals("white") ? 1 : -1, board, 0,
                    this.depth);//pasar los datos al minimax, creando un nuevo estado 
            Minimax.State decision = Minimax.minimaxDecision(s);
            this.depth++;

            if (firstTurn && p.get(FourInRow.TURN).equals("white")) {//optimizacion del primer turno para ahorrar tiempo
                firstTurn=false;
                return new Action(firstTurnX + ":" + firstTurnY + ":" + color);
            } else {
                return new Action(decision.move[0] + ":" + decision.move[1] + ":" + color);
            }
        }

        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }

}

class Minimax {

    static int dimensions = 4;
    static Map<Integer, tableNode> transposition = new HashMap<>();
    static int MAX = 1000;
    static int MIN = -1000;

    static private class tableNode {
        protected double score;  //puntaje 
        protected int depth; //profundidad del nodo en el arbol
        protected int type; // 0 is exact, 1 is upper bound and -1 is lower bound
        protected int color;

        public tableNode(double score, int depth, int type, int color) {
            this.score = score;
            this.depth = depth;
            this.type = type;
            this.color = color;
        }
    }

    private Minimax() {
    }

    public static State minimaxDecision(State state) {
        return state.getActions().stream().max(Comparator.comparing(Minimax::minValue)).get();  //retorna una coleccion de acciones de un estado
    }// y retorna el maximo elemento o la mejor jugada teniendo en cuenta el minimo o la mejor jugada que puede realizar el enemigo

    private static double maxValue(State state, int alpha, int beta) {
        int best = MIN;

        if (transposition.containsKey(Arrays.deepHashCode(state.board) + state.color)) { // si contiene un tablero en el mismo estado y en el mismo turno
            if (transposition.get(Arrays.deepHashCode(state.board) + state.color).depth > state.stone) { //
                tableNode t = transposition.get(Arrays.deepHashCode(state.board) + state.color); //usa la jugada que se habia realizado en un caso anterior
                double score = t.score /* (state.color*t.color) */;
                if (t.type == 0) {
                    return score;
                } else if (t.type /* (state.color*t.color) */ < 0 && score >= beta) {
                    return score;
                } else if (t.type /* (state.color*t.color) */ > 0 && score <= alpha) {
                    return score;
                } else if (beta <= alpha) {
                    return score;
                }
            }
        }
        if (state.isTerminal()) { // si es un estado terminal se agrega a la tabla de transposicion 
            double score = state.getUtility();
            if (score <= alpha) {
                transposition.put(Arrays.deepHashCode(state.board) + state.color,
                        new tableNode(score, state.stone, 1, state.color));
            } else if (score >= beta) {
                transposition.put(Arrays.deepHashCode(state.board) + state.color,
                        new tableNode(score, state.stone, -1, state.color));
            } else {
                transposition.put(Arrays.deepHashCode(state.board) + state.color,
                        new tableNode(score, state.stone, 0, state.color));
            }
            return score;
        }

        for (State action : state.getActions()) { //recorre la lista de posibles acciones y retorna la mejor jugada segun el maxValue
            double val = minValue(action, alpha, beta);
            best = (int) Math.max(best, val); //teoria de alpha y beta
            alpha = Math.max(alpha, best);

            if (beta <= alpha)
                break;
        }
        return best;
    }

    private static double minValue(State state) { //retorna el valor minimo de un estado
        return minValue(state, MIN, MAX);
    }

    private static double minValue(State state, int alpha, int beta) { 
        int best = MAX;

        if (transposition.containsKey(Arrays.deepHashCode(state.board) + state.color)// si encuentra en la tabla el mismo tablero y el mismo turno
                && transposition.get(Arrays.deepHashCode(state.board) + state.color).depth > state.stone) { // hace la misma decision que tomo antes
            tableNode t = transposition.get(Arrays.deepHashCode(state.board) + state.color);
            double score = t.score /* (state.color*t.color) */;
            if (t.type == 0) {
                return score;
            } else if (t.type /* (state.color*t.color) */ < 0 && score >= beta) {
                return score;
            } else if (t.type /* (state.color*t.color) */ > 0 && score <= alpha) {
                return score;
            } else if (beta <= alpha) {
                return score;
            }
        }
        if (state.isTerminal()) { // agrega el estado a la tabla de transposicion
            double score = state.getUtility();
            if (score <= alpha) {
                transposition.put(Arrays.deepHashCode(state.board) + state.color,
                        new tableNode(score, state.stone, 1, state.color));
            } else if (score >= beta) {
                transposition.put(Arrays.deepHashCode(state.board) + state.color,
                        new tableNode(score, state.stone, -1, state.color));
            } else {
                transposition.put(Arrays.deepHashCode(state.board) + state.color,
                        new tableNode(score, state.stone, 0, state.color));
            }
            return score;
        }

        for (State action : state.getActions()) { //retorna la mejor jugada segun el minValue
            double val = maxValue(action, alpha, beta);
            best = (int) Math.min(best, val);
            beta = Math.min(beta, best);

            if (beta <= alpha)
                break;
        }
        return best;
    }

    public static class State { //clase representativa de estado

        int w;
        int[] move;
        int[][] board;
        int color;
        int stone;//nivel de profundidad del arbol
        int depth;

        public State(int[] move, int color, int[][] board, int stone, int depth) {
            this.move = move;
            this.board = board;
            this.color = color;
            this.w = this.checkWin();
            this.stone = stone;
            this.depth = depth;
        }

        Collection<State> getActions() { //
            List<State> actions = new LinkedList<>();
            boolean term = false; //si es estado final
            int n = this.board.length; //tamano del tablero
            for (int j = 0; j < n; j++) {//recorre de izq a derecha el tablero
                for (int i = n - 1; i >= 0; i--) {// recorre de abajo a arriba el tablero
                    if (this.board[i][j] == 0) { // revisa si esta vacia la casilla en esa parte del tablero
                        int[][] temp = new int[this.board.length][]; //copia del tablero 
                        for (int k = 0; k < this.board.length; k++)// copia fila por fila del tablero anterior
                            temp[k] = this.board[k].clone();
                        if (this.stone % 2 == 1) { // determina el turno y crea el movimiento
                            temp[i][j] = this.color;
                        } else { 
                            temp[i][j] = -this.color;
                        }
                        State temp2 = new State(new int[] { i, j }, this.color, temp, this.stone + 1, this.depth);
                        if (temp2.isTerminal()) { //creando nodos hijos
                            term = true; //verifica si es terminal
                        }
                        if (this.stone < this.depth) {
                            actions.add(temp2);
                        }
                        i = n;
                        j++;
                        if (j == n) {
                            break;
                        }
                    }
                }
            }
            if (term) {
                actions.removeIf(action -> !action.isTerminal()); //si es terminal remueve los movimientos no terminales
            }
            return actions;
        }

        public int checkWin() {//revisa si el juego ya ha acabado
            int HEIGHT = this.board.length;
            int WIDTH = this.board[0].length;
            int EMPTY_SLOT = 0;
            for (int r = 0; r < HEIGHT; r++) { // iterate rows, bottom to top
                for (int c = 0; c < WIDTH; c++) { // iterate columns, left to right
                    int player = this.board[r][c];
                    if (player == EMPTY_SLOT)
                        continue; // don't check empty slots

                    if (c + 3 < WIDTH && player == this.board[r][c + 1] && player == this.board[r][c + 2]
                            && player == this.board[r][c + 3])// revisa si ya gano en una fila hacia arriba, derecha, diagonal iz y diagonal derecha
                        return player;
                    if (r + 3 < HEIGHT) {
                        if (player == this.board[r + 1][c] && player == this.board[r + 2][c]
                                && player == this.board[r + 3][c])
                            return player;
                        if (c + 3 < WIDTH && player == this.board[r + 1][c + 1] && // look up & right
                                player == this.board[r + 2][c + 2] && player == this.board[r + 3][c + 3])
                            return player;
                        if (c - 3 >= 0 && player == this.board[r + 1][c - 1] && // look up & left
                                player == this.board[r + 2][c - 2] && player == this.board[r + 3][c - 3])
                            return player;
                    }
                }
            }
            return EMPTY_SLOT;
        }

        public boolean full() {// mirar si el tablero esta lleno
            boolean flag = true;
            for (int i = 0; i < this.board.length && flag; i++) {
                for (int j = 0; j < this.board[0].length && flag; j++) {
                    flag &= this.board[i][j] != 0;
                }
            }
            return flag;
        }

        boolean isTerminal() { //si alguno gano o el tablero esta lleno
            return this.w != 0 || full();
        }

        double getUtility() { //evalua los pasos al nodo terminal
            int n = (this.board.length * this.board.length);
            if (this.w > 0) {
                if (this.color == 1) {
                    return n - this.stone;
                    // return n;
                } else {
                    return -(n - this.stone);
                    // return -n;
                }
            } else {
                if (this.w < 0) {
                    if (this.color == -1) {
                        return n - this.stone;
                        // return n;
                    } else {
                        return -(n - this.stone);
                        // return -n;
                    }
                } else {
                    return 0;
                }
            }
        }
    }
}