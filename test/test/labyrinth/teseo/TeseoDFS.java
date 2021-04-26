/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.labyrinth.teseo;

import java.util.Arrays;

import uniltiranyu.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;

/**
 *
 * @author Equipo 6 - cin>>UNnombre;
 */
public class TeseoDFS extends SimpleTeseoAgentProgram {
	
	protected boolean[][] m = new boolean[100][100];
	protected int[][] pr = new int [100][100];
	protected int x = 0;
	protected int y = 0;
	protected int N = 0; //North 0, East 1, South 2, West 3
	
    public TeseoDFS() {
    	for(int i=0; i<100; i++) {
    		for(int j=0; j<100; j++) {
    			pr[i][j] = -1;
    		}
    	}
    	for(int i=0; i<100; i++) {
    		for(int j=0; j<100; j++) {
    			m[i][j] = false;
    		}
    	}
    	m[x][y] = true;
    }
    
    @Override
    public int accion(boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL) {
        if (MT) return -1;
        boolean[] mov = new boolean[4];
        mov[0] = false;
        mov[1] = false;
        mov[2] = false;
        mov[3] = false;
        if(!PI) mov[3] = true;
        if(!PF) mov[0] = true;
        if(!PD) mov[1] = true;
        if(!PA) mov[2] = true;
        System.out.println(N + " " + x + " " + y);
        System.out.println();
        m[x][y] = true;
        int nN= -1;
        int auxN = N;
        if( N == 0 ) {
        	for(int i=0; i<4; i++) {
        		if(!mov[i])continue;
        		int nx, ny;
        		nx = x;
        		ny = y;
        		if(i == 0) {ny--; auxN=0;}
        		if(i == 1) {nx++; auxN=1;}
        		if(i == 2) {ny++; auxN=2;}
        		if(i == 3) {nx--; auxN=3;}
        		if(m[nx][ny]) continue;
        		if(auxN == 0 ) nN = 2;
        		if(auxN == 1 ) nN = 3;
        		if(auxN == 2 ) nN = 0;
        		if(auxN == 3 ) nN = 1;
        		if( pr[nx][ny] == -1 )pr[nx][ny] = nN;
        		x = nx;
        		y = ny;
        		N=auxN;
        		return i;
        	}
        }else if( N == 1 ) {
        	for(int i=0; i<4; i++) {
        		if(!mov[i])continue;
        		int nx, ny;
        		nx = x;
        		ny = y;
        		if(i == 0) {nx++; auxN=1;}
        		if(i == 1) {ny++; auxN=2;}
        		if(i == 2) {nx--; auxN=3;}
        		if(i == 3) {ny--; auxN=0;}
        		if(m[nx][ny]) continue;
        		if(auxN == 0 ) nN = 2;
        		if(auxN == 1 ) nN = 3;
        		if(auxN == 2 ) nN = 0;
        		if(auxN == 3 ) nN = 1;
        		if( pr[nx][ny] == -1 )pr[nx][ny] = nN;
        		x = nx;
        		y = ny;
        		N = auxN;
        		return i;
        	}
        }else if( N == 2 ) {
        	for(int i=0; i<4; i++) {
        		if(!mov[i])continue;
        		int nx, ny;
        		nx = x;
        		ny = y;
        		if(i == 0) {ny++; auxN=2;}
        		if(i == 1) {nx--; auxN=3;}
        		if(i == 2) {ny--; auxN=0;}
        		if(i == 3) {nx++; auxN=1;}
        		if(m[nx][ny]) continue;
        		if(auxN == 0 ) nN = 2;
        		if(auxN == 1 ) nN = 3;
        		if(auxN == 2 ) nN = 0;
        		if(auxN == 3 ) nN = 1;
        		if( pr[nx][ny] == -1 )pr[nx][ny] = nN;
        		x = nx;
        		y = ny;
        		N = auxN;
        		return i;
        	}
        }else {
        	for(int i=0; i<4; i++) {
        		if(!mov[i])continue;
        		int nx, ny;
        		nx = x;
        		ny = y;
        		if(i == 0) {nx--; auxN=3;}
        		if(i == 1) {ny--; auxN=0;}
        		if(i == 2) {nx++; auxN=1;}
        		if(i == 3) {ny++; auxN=2;}
        		if(m[nx][ny]) continue;
        		if(auxN == 0 ) nN = 2;
        		if(auxN == 1 ) nN = 3;
        		if(auxN == 2 ) nN = 0;
        		if(auxN == 3 ) nN = 1;
        		if( pr[nx][ny] == -1 )pr[nx][ny] = nN;
        		x = nx;
        		y = ny;
        		N = auxN;
        		return i;
        	}
        }
        //
        System.out.println("Back here " + pr[x][y]);
        if(pr[x][y] == 0) {
        	y--;
        	if(N==0) {N=pr[x][y+1]; return 0;}
        	if(N==1) {N=pr[x][y+1]; return 3;}
        	if(N==2) {N=pr[x][y+1]; return 2;}
        	if(N==3) {N=pr[x][y+1]; return 1;}
        }else if(pr[x][y] == 1) {
        	x++;
        	if(N==0) {N=pr[x-1][y]; return 1;}
        	if(N==1) {N=pr[x-1][y]; return 0;}
        	if(N==2) {N=pr[x-1][y]; return 3;}
        	if(N==3) {N=pr[x-1][y]; return 2;}
        }else if(pr[x][y] == 2) {
        	y++;
        	if(N==0) {N=pr[x][y-1]; return 2;}
        	if(N==1) {N=pr[x][y-1]; return 1;}
        	if(N==2) {N=pr[x][y-1]; return 0;}
        	if(N==3) {N=pr[x][y-1]; return 3;}
        }else {
        	x--;
        	if(N==0) {N=pr[x+1][y]; return 3;}
        	if(N==1) {N=pr[x+1][y]; return 2;}
        	if(N==2) {N=pr[x+1][y]; return 1;}
        	if(N==3) {N=pr[x+1][y]; return 0;}
        }
        return 0;
    }
    
}
