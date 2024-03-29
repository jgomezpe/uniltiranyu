/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.squares;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Jonatan
 */
public class Board {
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 4;
    public static final int BOTTOM = 8;
    public static final int WHITE = 16;
    public static final int BLACK = 32;
    
    public int[][] values;
    
    protected static int[][] init( int n, int m ){        
        int[][] values = new int[n][m];        
        for( int i=0; i<n; i++ ){
            values[i][0] = LEFT;
            values[i][m-1] = RIGHT;
        }
        for( int i=0; i<m; i++ ){
            values[0][i] |= TOP;
            values[n-1][i] |= BOTTOM;
        }
        return values;
    }
    
    public Board( int n ){
        this(n, n);
    }
    
    public Board( int n, int m ){
        values = init(n, m);
    }
       
    public boolean invalid( int i, int j, int val ){
      return i<0 || values.length<=i || j<0 || values[0].length <= j ||
             val<=0 || val>BOTTOM || (values[i][j] & val) == val;
    }
    
    protected int lines(int i, int j){
      int c=(values[i][j] & LEFT)==LEFT?1:0;
      c+=(values[i][j] & TOP)==TOP?1:0;
      c+=(values[i][j] & RIGHT)==RIGHT?1:0;
      c+=(values[i][j] & BOTTOM)==BOTTOM?1:0;
      return c;
    }
    
    protected boolean closable(int i, int j){
        return lines(i,j)==3;
    }
    
    protected boolean closed(int i, int j){
        return lines(i,j)==4;
    }
    
    protected void check(int color, int i, int j ){
    	if( closed(i,j) ){
    		if((values[i][j] & (BLACK+WHITE))==0) values[i][j] |= color;
    	}else{ 
	      if(closable(i,j)){
	        if( (values[i][j] & LEFT)==0 ){
	            values[i][j] |= LEFT;
	            values[i][j-1] |= RIGHT;
	            values[i][j] |= color;
	            check(color, i,j-1);
	        }
	
	        if( (values[i][j] & TOP)==0 ){
	            values[i][j] |= TOP;
	            values[i-1][j] |= BOTTOM;
	            values[i][j] |= color;
	            check(color, i-1,j);
	        }
	        
	        if( (values[i][j] & RIGHT)==0 ){
	            values[i][j] |= RIGHT;
	            values[i][j+1] |= LEFT;
	            values[i][j] |= color;
	            check(color, i,j+1);
	        }
	
	        if( (values[i][j] & BOTTOM)==0 ){
	            values[i][j] |= BOTTOM;
	            values[i+1][j] |= TOP;
	            values[i][j] |= color;
	            check(color, i+1,j);
	        }        
	      }
    	}  
    }
    
    public boolean play( boolean white, int i, int j, int val ){
        if( invalid(i,j,val) ){ return false; }
        int color = white?BLACK:WHITE;
        values[i][j] |= val; 
        switch(val){
            case LEFT:
              values[i][j-1] |= RIGHT;  
              check(color,i,j-1);
            break;    
            case TOP:
              values[i-1][j] |= BOTTOM;  
              check(color,i-1,j);
            break;    
            case RIGHT:
              values[i][j+1] |= LEFT;  
              check(color,i,j+1);
            break;    
            case BOTTOM:
              values[i+1][j] |= TOP;  
              check(color,i+1,j);
            break;    
        }
        check(color, i, j );
        return true;        
    }
        
    public String toString(){
      StringBuilder sb = new StringBuilder();
      for( int j=0; j<values[0].length; j++ ){
        sb.append(' ');
        if( (values[0][j] & TOP)==TOP ) sb.append('_'); else sb.append(' ');
      }
      sb.append('\n');
      for( int i=0; i<values.length; i++ ){
          for( int j=0; j<values[i].length; j++ ){
              if( (values[i][j] & LEFT)==LEFT ) sb.append('|'); else sb.append(' ');
              if( closed(i,j) ) sb.append(((values[i][j]&BLACK)==BLACK)?'b':'w');
              else if( (values[i][j] & BOTTOM)==BOTTOM ) sb.append('_'); else sb.append(' ');
          }
          if( (values[i][values[i].length-1] & RIGHT)==RIGHT ) sb.append('|'); else sb.append(' ');
          sb.append('\n');
      }
      return sb.toString();
    }
    
    public void draw( Graphics g, int DRAW_AREA_SIZE, int MARGIN ){
          g.setColor(Color.lightGray);
          Font font = g.getFont();
          g.setFont( new Font(font.getName(), font.getStyle(), 20 ));
          int n = values.length;
          int m = values[0].length;
          int CELL_SIZE = (DRAW_AREA_SIZE - 2*MARGIN)/Math.max(n, m);
          int fm = m * CELL_SIZE + MARGIN;
          int fn = n * CELL_SIZE + MARGIN;
          int ci = MARGIN;
          for (int i = 0; i<=n; i++) {
              g.drawLine(fm, ci, MARGIN, ci);
              ci += CELL_SIZE;
          }
          

          int cj = MARGIN;
          for (int j = 0; j<=m; j++) {
              g.drawLine(cj, fn, cj, MARGIN);
              cj += CELL_SIZE;
          }
          
          ci = MARGIN;
          for (int i = 0; i<n; i++) {
              cj=MARGIN;
              for (int j = 0; j<m; j++) {
                  int value = values[i][j];
                  g.setColor(Color.black);
                  if( (value & LEFT)==LEFT ) g.drawLine(cj, ci, cj, ci+CELL_SIZE);
                  if( (value & TOP)==TOP ) g.drawLine(cj, ci, cj+CELL_SIZE, ci);
                  if( (value & RIGHT)==RIGHT ) g.drawLine( cj+CELL_SIZE, ci, cj+CELL_SIZE, ci+CELL_SIZE);
                  if( (value & BOTTOM)==BOTTOM ) g.drawLine( cj, ci+CELL_SIZE, cj+CELL_SIZE, ci+CELL_SIZE);
                  if(closed(i, j)){
                    if( (value&WHITE)==WHITE ){
                      g.setColor(Color.blue);
                      g.fillOval(cj+1, ci+1, CELL_SIZE-2, CELL_SIZE-2);
                    }else{
                      g.setColor(Color.red);
                      g.fillOval(cj+1, ci+1, CELL_SIZE-2, CELL_SIZE-2);
                    }
                  }  
                  cj += CELL_SIZE;
              }
              ci += CELL_SIZE;
          }
    }
    
    public int white_count(){
       int c=0;
       for(int i=0; i<values.length; i++){
          for(int j=0; j<values[0].length; j++){
              if(lines(i,j) == 4 && (values[i][j]&WHITE)==WHITE ){
                  c++;
              }
          }
       }
       return c;
    }

    public int black_count(){
       int c=0;
       for(int i=0; i<values.length; i++){
          for(int j=0; j<values[0].length; j++){
              if(lines(i,j) == 4 && (values[i][j]&WHITE)!=WHITE ){
                  c++;
              }
          }
       }
       return c;
    }
    
    public boolean full(){
        boolean flag = true;
        for( int i=0; i<values.length&&flag; i++){
            for( int j=0; j<values[0].length&&flag; j++){
                flag &= (lines(i,j)==4);
            }
        }
        return flag;
    }
    
    public static void main( String[] args ){
    	int N = 10;
        Board b = new Board(N);
        System.out.println(b);
        System.out.println("************************************");
        for(int i=1; i<N-1; i++ ){
        	b.play((i%2==1),i, 1, LEFT);
        	System.out.println(b);
        	System.out.println("************************************");
        }
        for(int i=0; i<N; i++ ){
        	b.play((i%2==0),i, 2, LEFT);
        	System.out.println(b);
        	System.out.println("************************************");
        }
        for(int i=1; i<N-1; i++ ){
        	b.play((i%2==1),i, 3, LEFT);
        	System.out.println(b);
        	System.out.println("************************************");
        }
        for(int i=0; i<N; i++ ){
        	b.play((i%2==0),i, 4, LEFT);
        	System.out.println(b);
        	System.out.println("************************************");
        }
        
        b.play(true, 3, 0, TOP);
        System.out.println(b);
        System.out.println("************************************");
        
        b.play(false, 5, 2, BOTTOM);
        System.out.println(b);
        System.out.println("************************************");        
    }        
}

