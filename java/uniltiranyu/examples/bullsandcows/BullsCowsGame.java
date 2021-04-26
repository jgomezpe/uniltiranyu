/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.bullsandcows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hazarda.Hazarda;
import uniltiranyu.Action;
import uniltiranyu.Percept;

/**
 *
 * @author Jonatan
 */
public class BullsCowsGame {
	public static int read( String text ){	
		try{
			System.out.println(text);
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			return Integer.parseInt(s);
		}catch(IOException e){ e.printStackTrace(); }
		return 0;
	}
    
	public static int[] readNumber( String text ){	
		try{
			System.out.println(text);
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String s = bufferRead.readLine();
			int[] opt = new int[s.length()];
			for( int i=0; i<opt.length; i++ ) opt[i] = s.charAt(i) - '0';
			return opt;
		}catch(Exception e){ e.printStackTrace(); }
		return null;
	}
	
	public static int[] usuario(int l) {
		int[] opt = new int[l];
		for( int i=0; i<opt.length; i++ ) {
			opt[i] = read("Digit?");
		}
		return opt;
	}

	public static void main( String[] args ){
		int digits = read("Digits?"); // 10 digitos 0..9 
		int positions = read("Positions?"); // 4 posiciones
		NumberIndex ni = new NumberIndex(digits, positions);
		SimpleBCPlayer destructor = new SimpleBCPlayer(ni);
		//UNEquipoPlayer player = new UNEquipoPlayer(ni);
		int s = ni.size();
		
		int c_number = Hazarda.uniform(s);
		int[] opt = ni.getOption(c_number);
		boolean exit = false;
		int b=-1, c=-1; 
		Percept p = new Percept();
		do{
			int[] u_opt = usuario(positions);
			System.out.println("Your robot will try..");
			for( int x : u_opt ) System.out.print(x);
			System.out.println();
			int[] u_bc = NumberIndex.compare(opt, u_opt);
			System.out.println("Bulls (Fijas):"+u_bc[0]+ "Cows (Picas):"+u_bc[1]);
			if(u_bc[0]==positions){
				exit = true;
				System.out.println("Congratulations. You are almost as intelligent as me");
			}
			
			p.set("B",  b);
			p.set("C",  c);
			
			Action m_opt = destructor.compute(p);
			System.out.println("Destructor will try:"+m_opt.getCode());
			
			exit = m_opt.getCode().contains("er");
			if( !exit ) {
				b = read("Bulls (Fijas)?");
				c = read("Cows (Picas)?");
			}
		}while(!exit);
	}
}