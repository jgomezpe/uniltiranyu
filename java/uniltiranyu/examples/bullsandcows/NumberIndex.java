/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.bullsandcows;

import speco.array.Array;
import speco.array.ArrayMultiSet;

/**
 *
 * @author Jonatan
 */
public class NumberIndex {
	protected int[] options;
	protected int digits;
	protected int positions;
	public NumberIndex( int _digits, int _positions ){
		//Here we assume the number of positions is less or equal than the number of digits
		digits = _digits;
		positions = _positions;
		options = new int[positions+1];
		compute(digits, positions, options);
	}
    
	// 10 digitos y 4 posiciones
	// para la posicion 1 se pueden poner 10 digitos (quito un digito el que pongo y quito una posicion) 
	// compute(10, 4) = 10 * compute( 9, 3 )
	// compute(9, 3) = 9 * compute( 8, 2 )
	// compute(8, 2) = 8 * compute( 7, 1 )
	// compute(7, 1) = 7
	public static int compute(int digits, int positions, int[] options ){        
		if( options[positions] == 0 ){
			if( positions == 1 ) options[positions] = digits;
			else options[positions] = digits * compute(digits-1, positions-1, options);
		}    
		return options[positions];
	}

	public int getIndex( int[] option, int n, Array<Integer> check ){
		if( n==option.length ) return 0;
		ArrayMultiSet<Integer> search = new ArrayMultiSet<Integer>(check); 
		int pos = search.first(option[n]);
		check.remove(pos);        
		n++;
		return pos*options[positions-n] + getIndex(option, n, check);
	}
    
	public int getIndex( int[] option ){
		Array<Integer> check = new Array<Integer>();
		for( int i=0; i<digits; i++) check.add(i);
		return getIndex(option, 0, check);
    }
	
	// index = 1000
	// check = [0,1,2,3,4,5,6,7,8,9]
	// option = [-,-,-,-]
	// n = 1
	public void getOption( int index, int[] option, int n, Array<Integer> check ){
		n++;
		if( n < option.length ){
			int pos = index / options[positions-n]; // 1000/504 = 1
			index %= options[positions-n]; // 1000%504 = 496
			option[n-1] = check.get(pos);
			check.remove(pos);
			getOption(index, option, n, check);
		}else option[n-1] = check.get(index);
	}

	public int[] getOption( int index ){
		Array<Integer> check = new Array<Integer>();
		for( int i=0; i<digits; i++) check.add(i);
		int[] option = new int[positions];
		try{ getOption(index, option, 0, check); }catch(Exception e){}
		return option;
	}

	public int size(){ return options[positions]; }

	public static int[] compare( int[] one, int[] two ){
		int[] bc = new int[2];
		for( int i=0; i<one.length; i++ ){
			int j=0;
			while(j<two.length && one[i]!=two[j]){ j++; }
			if(j<two.length){
				if( i==j ) bc[0]++;
				else bc[1]++;
			}
		}
		return bc;
	} 
}