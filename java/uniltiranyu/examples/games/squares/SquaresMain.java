/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.squares;

import uniltiranyu.Agent;
import uniltiranyu.AgentProgram;

/**
 *
 * @author Jonatan
 */
public class SquaresMain  {
    public static int[] permuta( int n ) {
	int[] indices = new int[n];
	for( int i=0; i<n; i++) indices[i] = i;
	for( int i=0; i<2*n; i++) {
	    int j = (int)(Math.random()*n);
	    int k = (int)(Math.random()*n);
	    int t = indices[j];
	    indices[j] = indices[k];
	    indices[k] = t;
	}
	return indices;
    }
    
  public static void main( String[] argv ){
    // Reflection
    AgentProgram[] w_agents = new AgentProgram[]{
	    new DummySquaresAgentProgram(Squares.WHITE)
    };  
    AgentProgram[] b_agents = new AgentProgram[]{
	    new DummySquaresAgentProgram(Squares.BLACK)	    
    }; 
    
    Agent w_agent =  new Agent( w_agents[3] ); // azul
    Agent b_agent =  new Agent( b_agents[1] ); // rojo
      
    /*int[] idx = permuta(13);
    for( int i=0; i<idx.length; i++ ) {
	System.out.print(idx[i]+",");
    }*/
    SquaresMainFrame frame = new SquaresMainFrame( w_agent, b_agent );
    frame.setVisible(true);
  }
}