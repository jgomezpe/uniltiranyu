
package test.games.fourinrow;

import java.util.Arrays;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.games.fourinrow.FourInRow;

import static java.lang.System.exit;

/**
 *
 * @author Jonatan
 */
public class DummyAgentProgram implements AgentProgram {
    protected String color;
    protected int[][] board;
    protected int[] checker;
    Boolean virgin = true;
    int RedWins = 1000000;
    int width;
    int height;
    int g_maxDepth = 9;
    class aux {
        public Integer move, score;

        public aux() {
            move = 0;
            score = 0;
        }
    }
    public DummyAgentProgram(String color) {

        this.color = color;

    }

    private void updateBoard(Percept p){

        for(int i = 0;i<width;i++ ){

            if(checker[i]>=0) {
                if (!p.get(checker[i] + ":" + i).equals(FourInRow.SPACE)) {

                    board[checker[i]][i] = -1;
                    checker[i]--;

                }
            }
        }
        System.out.println(color+" checker :"+ Arrays.toString(checker));
    }

    void abNegamax(int color, int depth, int[][] board, aux cosas, int a, int b) {



        int bestScore = a;
        int bestMove = -1;

        for (int column = 0; column < width; column++) {
            if (board[0][column] != 0) continue;
            int rowFilled = dropDisk(board, column, color);
            if (rowFilled == -1)
                continue;

            int s = ScoreBoard(board);

            if (color == 1 && s == RedWins) {
                bestMove = column;
                bestScore = s;
                board[rowFilled][column] = 0;
                break;
            }
            aux cosasSub = new aux();
            if (depth > 1) {
                abNegamax(color == 1 ? -1 : 1, depth - 1, board, cosasSub, -b, -bestScore);
            } else {
                cosasSub.move = -1;
                cosasSub.score = s;
            }

            board[rowFilled][column] = 0;


            if (cosasSub.score > bestScore) {
                bestScore = cosasSub.score;

                if (bestScore >= b) {
                    break; // pruning
                }
                // write best move on top of tree
                if (depth == g_maxDepth) {
                    bestMove = column;
                }
            }
        }

        cosas.move = bestMove;

        cosas.score = bestScore * -1;


    }


    int ScoreBoard(int[][] board) {
        int counters[] ={0,0,0,0,0,0,0,0,0,0,0};

        int[][] scores = board;

        // Horizontal spans
        for (int y = 0; y < height; y++) {
            int score = scores[y][0] + scores[y][1] + scores[y][2];
            for (int x = 3; x < width; x++) {
                assert (inside(y, x));
                score += scores[y][x];
                counters[score + 4]++;
                assert (inside(y, x - 3));
                score -= scores[y][x - 3];
            }
        }
        // Vertical spans
        for (int x = 0; x < width; x++) {
            int score = scores[0][x] + scores[1][x] + scores[2][x];
            for (int y = 3; y < height; y++) {
                assert (inside(y, x));
                score += scores[y][x];
                counters[score + 4]++;
                assert (inside(y - 3, x));
                score -= scores[y - 3][x];
            }
        }
        // Down-right (and up-left) diagonals
        for (int y = 0; y < height - 3; y++) {
            for (int x = 0; x < width - 3; x++) {
                int score = 0;
                for (int idx = 0; idx < 4; idx++) {
                    int yy = y + idx;
                    int xx = x + idx;
                    assert (inside(yy, xx));
                    score += scores[yy][xx];
                }
                counters[score + 4]++;
            }
        }
        // up-right (and down-left) diagonals
        for (int y = 3; y < height; y++) {
            for (int x = 0; x < width - 3; x++) {
                int score = 0;
                for (int idx = 0; idx < 4; idx++) {
                    int yy = y - idx;
                    int xx = x + idx;
                    assert (inside(yy, xx));
                    score += scores[yy][xx];
                }
                counters[score + 4]++;
            }
        }
        if (counters[0] != 0)
            return -RedWins;
        else if (counters[8] != 0)
            return RedWins;
        else // heuristic function
            return
                    counters[5] + 2 * counters[6] + 5 * counters[7] -
                            counters[3] - 2 * counters[2] - 5 * counters[1];
    }

    int nextMove(int[][] board) {
        int scoreOrig = ScoreBoard(board);
        if (scoreOrig == RedWins) {  System.out.println(  "Computer wins!\nGAME OVER\n"); return -1; }
        else if (scoreOrig == -RedWins) {   System.out.println(  "You win!!!!!\n"); return -1; }
            aux cosas = new aux();
            abNegamax(1, g_maxDepth, board, cosas, -RedWins * 10, RedWins * 10);
            if (cosas.move != -1) {
                System.out.println("Computer "+color + "chose: " + cosas.move);
                dropDisk(board,cosas.move,1);
                checker[cosas.move]--;
                for(int u=0;u<width;u++) {
                    System.out.println(Arrays.toString(board[u]));
                }
                return cosas.move;

            } else {
                System.out.println("No move possible");
                exit(-1);
                return -1;
            }

    }
    Boolean inside(int y, int x) {
        return y >= 0 && y < height && x >= 0 && x < width;
    }
    int dropDisk(int[][] board, int column, int color) {
        for (int y = height - 1; y >= 0; y--)
            if (board[y][column] == 0) {
                board[y][column] = color;
                return y;
            }
        return -1;
    }

    void printBoard(int[][] board)
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int y = 0; y < height; y++)
        {
            System.out.print(  "[");

            for (int x = 0; x < width; x++)
            {
                if (board[y][x] == 1)
                    System.out.print('O');
                    //System.out.println(  "X";
                else if (board[y][x] == -1)
                    System.out.print('X');
                    //System.out.println(  "O";
                else
                    System.out.print(  " ");
                if (x != width - 1)
                    System.out.print(  " ");
            }
            System.out.println(  "]");
        }
        System.out.println(  " 0 1 2 3 4 5 6 7 8");
    }



    @Override
    public Action compute(Percept p) {
        if (virgin) {
            int m = Integer.parseInt((String) p.get(FourInRow.SIZE));
            board = new int[m][m];
            checker = new int[m];
            width = m;
            height = m;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    board[i][j] = 0;
                }
                checker[i] = m - 1;
            }
          virgin=false;
        }

/*
        long time = (long) (200 * Math.random());

        try {
            Thread.sleep(time);
        } catch (Exception e) {
        }
*/
        if (p.get(FourInRow.TURN).equals(color)) {
            updateBoard(p);
            int check= nextMove(board);
            if(check == -1){
                return new Action((int)(-1) + ":" + -1 + ":" + color);
            }
            return new Action((int)(checker[check]+1) + ":" + check + ":" + color);
        }
        return new Action((int)(-1) + ":" + -1 + ":" + color);
    }

    @Override
    public void init() {
    }



}