/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.fourinrow;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

/**
 *
 * @author Miguel Alejandro Peña, Juan Pablo Girón
 */
public class MinimaxFourInRowAgentProgram implements AgentProgram {
    public class Pair{
        private int col;
        private int score;
        
        public Pair(int c, int s){
            this.col = c;
            this.score = s;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
    
    protected String p_color;
    protected int n;
    protected String[][] board;
    public MinimaxFourInRowAgentProgram( String color ){
        this.p_color = color;        
    }
    
    public int get_next_open_row(String[][] brd,int col){
        for(int row = n-1;row >=0;row--){
            //System.out.println("ROW: "+row+", COL: "+col);
            //System.out.println("board row: "+ brd[row][col]);
            if(brd[row][col].equals((String)FourInRow.SPACE)){
                //System.out.println("row: "+row);
                return row;
            }
        }
    return 0;
    }
    
    public boolean is_valid_location(String[][] brd, int col){
        return brd[0][col].equals((String)FourInRow.SPACE);
    }
    
    public Array<Integer> get_valid_locations(String[][] brd){
        Array<Integer> valid_locations = new Array<>();
        for(int col = 0;col < n;col++){
            if(is_valid_location(brd, col))
                valid_locations.add(col);
        }
        return valid_locations;
    }
    
    public void drop_piece(String[][] boards, int row, int col, String color){
        boards[row][col] = color;
    }
    
    public int count_ocurrences(String[] arr,String value){
        int count = 0;
            for(int i=0;i < arr.length;i++)
                if(value.equals(arr[i]))
                    count++;
        return count;
    }
    
    public String opponent_color(String color){
        String opp_color = FourInRow.BLACK;
        if(color.equals(opp_color))
            opp_color = FourInRow.WHITE;
        return opp_color;
    }
    
    public int evaluate_window(String[] window, String color){
        int score = 0;
        String opp_color = opponent_color(color);

      
        if((count_ocurrences(window,color) == 3) && count_ocurrences(window,FourInRow.SPACE) == 1)
            score += 5;
        if((count_ocurrences(window,color) == 2) && count_ocurrences(window,FourInRow.SPACE) == 2)
            score += 2;
        
        if((count_ocurrences(window,opp_color) == 3) && count_ocurrences(window,FourInRow.SPACE) == 1)
            score -= 20;
        
        return score;
    }
    
    public int score_position(String[][] board, String color){
        int score = 0;
        
        // Puntaje para la columna central
        for(int i=0;i < board[0].length;i++){
            if(board[i][Math.floorDiv(board[1].length,2)].equals(color)){
                score += 3;
            }
        }
        
        //Puntaje horizontal
        for(int i=0; i < board[0].length;i++){
            String[] window = new String[4];
            for(int j=0;j < board[1].length - 3;j++){
                for(int w=0;w < 4;w++){
                    window[w] = board[i][j+w];
                }
                score += evaluate_window(window,color);
            }   
        }
        
        //Puntaje vertical
        for(int j=0; j < board[1].length;j++){
            String[] window = new String[4];
            for(int i=0;i < board[0].length - 3;i++){
                for(int w=0;w < 4;w++){
                    window[w] = board[i+w][j];
                }
                score += evaluate_window(window,color);
            }   
        }
        
        //Puntaje para las diagonales
        for(int i=0; i < board[0].length - 3;i++){
            String[] window = new String[4];
            for(int j=0;j < board[1].length - 3;j++){
                for(int w=0;w < 4;w++){
                    window[w] = board[i+w][j+w];
                }
                score += evaluate_window(window,color);
            }   
        }
        
        for(int i=3; i < board[0].length;i++){
            String[] window = new String[4];
            for(int j=3;j < board[1].length;j++){
                for(int w=0;w < 4;w++){
                    window[w] = board[i-w][j-w];
                }
                score += evaluate_window(window,color);
            }   
        }
        
        return score;
    }
    
    public String[][] copy_board(String[][] original){
        String[][] copy = new String[original.length][original[1].length];
        for(int i=0; i<original.length; i++){
            for(int j=0; j<original[i].length; j++){
                copy[i][j]=original[i][j];
                //System.out.print("element["+i+"]["+j+"]: "+original[i][j]);
            }
            //System.out.println("\n");
        }   
        return copy;
    }
    
     public boolean winning_move(String[][] brd, String color){
        
        //Verificar si gana horizontalmente
        for(int i=0; i < brd[0].length;i++){
            String[] window = new String[4];
            for(int j=0;j < brd[1].length - 3;j++){
                if(brd[i][j].equals(color) && brd[i][j+1].equals(color) &&
                   brd[i][j+2].equals(color) && brd[i][j+3].equals(color)){
                    return true;
                }
            }
        }
        
        //Verificar si gana verticalmente
        for(int i=0; i < brd[0].length - 3;i++){
            String[] window = new String[4];
            for(int j=0;j < brd[1].length;j++){
                if(brd[i][j].equals(color) && brd[i+1][j].equals(color) &&
                   brd[i+2][j].equals(color) && brd[i+3][j].equals(color)){
                    return true;
                }
            }
        }
        
        //Verificar si gana en las diagonales abajo/derecha - arriba/izquierda
        for(int i=0; i < brd[0].length - 3;i++){
            String[] window = new String[4];
            for(int j=0;j < brd[1].length - 3;j++){
                if(brd[i][j].equals(color) && brd[i+1][j+1].equals(color) &&
                   brd[i+2][j+2].equals(color) && brd[i+3][j+3].equals(color)){
                    return true;
                }
            }
        }
        
        //Verificar si gana en las diagonales abajo/izquierda - arriba/derecha
        for(int i=0; i < brd[0].length - 3;i++){
            String[] window = new String[4];
            for(int j=3;j < brd[1].length;j++){
                if(brd[i][j].equals(color) && brd[i+1][j-1].equals(color) &&
                   brd[i+2][j-2].equals(color) && brd[i+3][j-3].equals(color)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean is_terminal_node(String[][] brd){
        return winning_move(brd,(String)FourInRow.BLACK) || 
               winning_move(brd,(String)FourInRow.WHITE) ||
               get_valid_locations(brd).size() == 0 ;
    }
    
    public int[] minimax(String[][] brd, int depth,int alpha, int beta, boolean maximizingPlayer){
        Array<Integer> valid_locations = get_valid_locations(brd);
        boolean is_terminal = is_terminal_node(brd);
        if(depth == 0 || is_terminal){
            if(is_terminal){
                if(winning_move(brd,p_color)){
                    return new int[] {0,10000000};
                } else if (winning_move(brd,opponent_color(p_color))){
                    return new int[] {0,-10000000};
                } else { //El juego no tiene más movimientos válidos
                    return new int[] {0,0};
                }
            } else {  //La profundidad es 0
                return new int[] {0,score_position(brd,p_color)};
            }
        }
        if(maximizingPlayer){
            int value = -2_000_000_000; //Cercano a -infinito
            int column = valid_locations.get(0);
            for(int col : valid_locations){
                int row = get_next_open_row(brd,col);
                String[][] board_copy = copy_board(brd);
                drop_piece(board_copy,row,col,p_color);
                int new_score = minimax(board_copy,depth -1,alpha, beta, false)[1];
                if(new_score > value){
                    value = new_score;
                    column = col;  
                }
                alpha = Math.max(alpha, value);
                if(alpha >= beta){
                    break;
                }
            }
        return new int[] {column, value};  
        
        } else{  //Minimizando el player
            int value = 2_000_000_000; //Cercano a infinito
            int column = valid_locations.get(0);
            for(int col : valid_locations){
                int row = get_next_open_row(brd,col);
                String[][] board_copy = copy_board(brd);
                drop_piece(board_copy,row,col,opponent_color(p_color));
                int new_score = minimax(board_copy,depth -1,alpha, beta, true)[1];
                if(new_score < value){
                    value = new_score;
                    column = col;  
                }
                beta = Math.min(beta, value);
                if(alpha >= beta){
                    break;
                }
            }
        return new int[] {column, value};  
        }
    }
    
    @Override
    public Action compute(Percept p) {        
        long time = 1;//(long)(200 * Math.random());
       try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(FourInRow.TURN).equals(p_color) ){
            n = Integer.parseInt((String)p.get(FourInRow.SIZE));
            board = new String[n][n];
            for (int x=0;x < n;x++){
                for(int y=0;y < n;y++){
                    //System.out.print("p: "+p.getAttribute((x)+":"+(y))+" ");
                    board[x][y] = ""+p.get(x+":"+y);
                }
                //System.out.println("");
            }

            int j = minimax(board ,4 , -2_000_000_000, 2_000_000_000,true)[0];
            int i = get_next_open_row(board,j);
            System.out.println("Minimax v0.1.0 "+p_color+" return: i: "+i+",j: "+j);
            return new Action( i+":"+j+":"+p_color );
        }
        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }
    
}