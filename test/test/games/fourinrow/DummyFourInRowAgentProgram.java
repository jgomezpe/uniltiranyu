    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.fourinrow;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.Board;
import uniltiranyu.examples.games.fourinrow.FourInRow;


public class DummyFourInRowAgentProgram implements AgentProgram {
    protected String color;
    public DummyFourInRowAgentProgram( String color ){
        this.color = color;        
    }
    static Map<Integer,int[]> transTable = new HashMap<>();//Transposition table
    
    private int[][] generateBoardMatrix(int n, Percept p){
       
        int[][] boardMatrix = new int[n][n];
        for (int i = 0; i < n; i++) { //this equals to the row in our matrix.
            for (int j = 0; j < n; j++) { //this equals to the column in each row.
                Object temp = p.get(i+":"+j);
                int k = 0;
                int m = 0;
                if (temp.equals("white")){k=1;m=1;}else if (temp.equals("black")){k=-1;m=1;}
                boardMatrix[i][j] = k;
            }
        };
        
        return boardMatrix;
    }

    
    private int countMoves(int[][] values){
        int k = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if(values[i][j] != 0) k++;
            }
        }
        return k;
    }

    private boolean isFull(int[][] values){
        boolean flag = true;
        for( int i=0; i<values.length&&flag; i++){
            for( int j=0; j<values[0].length&&flag; j++){
                flag &= values[i][j] != 0;
            }
        }
        return flag;
    }

    private int nextRow(int[][] board, int column){
        for (int i = 0; i < board.length; i++) {
                if(board[i][column] != 0){
                    return i - 1;
                }
        }
        return board.length - 1;
    }

    public int check_ld( int i, int j, int[][] values){
        int n = values.length;
        if( i<=n-Board.LINE && j>=Board.LINE-1 && values[i][j]!=0){
            int c = values[i][j];
            int k = 0;
            while( k<Board.LINE && c==values[i][j] ){
                i++;
                j--;
                k++;
            }
            if( k==Board.LINE ) return c;
        }
        return 0;
    }

    private int check_rd( int i, int j, int[][] values){
        int n = values.length;
        if( i<=n-Board.LINE && j<=n-Board.LINE && values[i][j]!=0){
            int c = values[i][j];
            int k = 0;
            while( k<Board.LINE && c==values[i][j] ){
                i++;
                j++;
                k++;
            }
            if( k==Board.LINE ) return c;
        }
        return 0;
    }

    private int check_d( int i, int j, int[][] values){
        int n = values.length;
        if( i<=n-Board.LINE && values[i][j]!=0){
            int c = values[i][j];
            int k = 0;
            while( k<Board.LINE && c==values[i][j] ){
                i++;
                k++;
            }
            if( k==Board.LINE ) return c;
        }
        return 0;
    }

    private int check_r( int i, int j, int[][] values){
        int n = values.length;
        if( j<=n-Board.LINE && values[i][j]!=0){
            int c = values[i][j];
            int k = 0;
            while( k<Board.LINE && c==values[i][j] ){
                j++;
                k++;
            }
            if( k==Board.LINE ) return c;
        }
        return 0;
    }

    private int check(int[][] values){
        for(int i=0; i<values.length; i++){
            for(int j=0; j<values[0].length; j++){
                int x = check_ld(i, j, values);
                if( x != 0 ) return x;
                x = check_rd(i, j, values);
                if( x != 0 ) return x;
                x = check_d(i, j, values);
                if( x != 0 ) return x;
                x = check_r(i, j, values);
                if( x != 0 ) return x;
            }
        }
        return 0;
    }

    private int[][] cloneArray(int[][] src) {
        int length = src.length;
        int[][] target = new int[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    private void play(String color, int column, int[][] board){
        int token = 0;
        if(color.equals(FourInRow.WHITE)){
            token = 1;
        }else if(color.equals(FourInRow.BLACK)){
            token = -1;
        }
        int row = nextRow(board, column);
        if(row < 0) return;
        board[row][column] = token;
    }

    private boolean isWinningMove(String color, int column, int[][] board){
        int token = 0;
        if(color.equals(FourInRow.WHITE)){
            token = 1;
        }else if(color.equals(FourInRow.BLACK)){
            token = -1;
        }
        int[][] tempBoard = cloneArray(board);
        int row = nextRow(tempBoard, column);
        if(row < 0) return false;
        tempBoard[row][column] = token;
        return check(tempBoard) == token;
    }

    int[] negamax(int size, int[][] oldBoard, String curColor, int alpha, int beta) {
        int[][] board = cloneArray(oldBoard);
        String nextColor = curColor;
        int[] results = {0, 0};
        if(isFull(board)) // check for draw game
            return results;

        for(int x = 0; x < size; x++) // check if current player can win next move
            if(color.equals(nextColor) && isWinningMove(nextColor, x, board)){
                results[0] = x;
                results[1] = (size*size - countMoves(board))/2;
                return results;
            }
            
        int max = (size * size - 1 - countMoves(board))/2;	// upper bound of our score as we cannot win immediately
        if(transTable.containsKey(Arrays.deepHashCode(oldBoard))){
        	results = transTable.get(Arrays.deepHashCode(oldBoard));
        	max = results[1] + (-(size * size) / 2)+ 2; //(-(size * size) / 2)+ 2 ->minimo posible
        }
      	if(beta > max) {
        	beta = max;                     // there is no need to keep beta above our max possible score.
        	if(alpha >= beta){
        		results[1] = beta;
        		return results;
        	}
      	}
        
        results[1] = -size*size;
        if(nextColor.equals(FourInRow.WHITE)) nextColor = FourInRow.BLACK;
        else if(nextColor.equals(FourInRow.BLACK)) nextColor = FourInRow.WHITE;
        
        for(int x = 0; x < size; x++) {
            play(nextColor, x, board);// It's opponent turn in P2 position after current player plays x column.
            int[] score = negamax(size, board, nextColor, -beta, -alpha);
            score[1] = -score[1];// If current player plays col x, his score will be the opposite of opponent's score after playing col x
            if(score[1] >= beta) return score;
            if(score[1] > alpha){
                alpha = score[1];
                results[0] = x;
                results[1] = score[1]; // keep track of best possible score so far.
            }
        }
        transTable.put(Arrays.deepHashCode(oldBoard), results);
        return results;
    }
    
    @Override
    public Action compute(Percept p) {        
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(FourInRow.TURN).equals(color) ){
            int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
            int[][] matrix = generateBoardMatrix(n, p);
            int[] movement = negamax(n, matrix, this.color, -(n * n) / 2, (n * n) / 2);
            int i = nextRow(matrix, movement[0]);
            int j = movement[0];
            /*boolean flag = (i==n-1) || !p.getAttribute((i+1)+":"+j).equals((String)FourInRow.SPACE);
            while( !flag ){
                i = (int)(n*Math.random());
                j = (int)(n*Math.random());
                flag = (i==n-1) || !p.getAttribute((i+1)+":"+j).equals((String)FourInRow.SPACE);
            }*/
            return new Action( i+":"+j+":"+color );
        }
        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }
    
}