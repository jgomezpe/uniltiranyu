package uniltiranyu.examples.bullsandcows;

import hazarda.Hazarda;
import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;

/**
 *
 * @author Jonatan
 */
public class SimpleBCPlayer implements AgentProgram{
	protected NumberIndex ni;
	protected Array<Integer> options = new Array<Integer>();
	public SimpleBCPlayer( NumberIndex _ni ){
		ni = _ni;
		init();
	}

	public int[] next(){
		try{ return ni.getOption( options.get(Hazarda.uniform(options.size())) ); }catch( Exception e){ return null; }
	}

	// 3269
	// 2456 bc=[1,0]
	// 1528 bc = [2,0]
	// 1348 bc = [1,0] <-- se elimina
	// 2456 bc = [0,4] <-- se elimina
	// 2469 bc = [1, 2] <-- se elimina
	// 3269 bc = [2, 0] <-- se queda 
	
	public boolean check( int[] option, int[] bc ){
		try{
			int i=0;
			while(  i<options.size() ){
				int[] opt = ni.getOption(options.get(i));            
				int[] bc2 = NumberIndex.compare(option, opt);
				if( bc2[0] != bc[0] || bc2[1] != bc[1] ) options.remove(i);
				else i++;
			}
		}catch(Exception e){}	
		return options.size()>0;
	}
	
	protected int[] last;
	
	public String get() {
		StringBuilder sb = new StringBuilder();
		for( int i=0; i<last.length; i++ ) sb.append((char)(48+last[i]));
		return sb.toString();
	}

	@Override
	public Action compute(Percept p) {
		int b = (Integer)p.get("B");
		int c = (Integer)p.get("C");
		if( b==4 ) return new Action("As expected, I break your number (and your legs jajajaja)");
		if( b==-1 || c==-1 ) {
			last = next();
			return new Action(get());
		}else {
			if(!check(last, new int[] {b,c})) {
				return new Action("You loser. You can't lie to me");
			}else {
				last = next();
				return new Action(get());
			}
		}
	}

	@Override
	public void init() {
		int s = ni.size();
		for( int i=0; i<s; i++ ) options.add(i);
	}
}