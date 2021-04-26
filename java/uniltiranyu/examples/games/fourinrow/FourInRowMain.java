/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.fourinrow;

import uniltiranyu.Agent;

/**
 *
 * @author Jonatan
 */
public class FourInRowMain {
  public static void main( String[] argv ){
	  Agent w_agent = new Agent( new DummyFourInRowAgentProgram("white") );
	  Agent b_agent = new Agent( new DummyFourInRowAgentProgram("black") );
	  FourInRowMainFrame frame = new FourInRowMainFrame( w_agent, b_agent );
	  frame.setVisible(true);   
  }
    
}
