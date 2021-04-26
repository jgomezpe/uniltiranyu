/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.squares;

import test.games.squares.Cuadrito;
import test.games.squares.CuadritoAgentProgram;
import test.games.squares.DeepBlue;
import test.games.squares.InteligSquaresAgent;
import test.games.squares.cajita;
import test.games.squares.squaresAgentHomework;
import uniltiranyu.Agent;

/**
 *
 * @author Jonatan
 */
public class SquaresMain  {
  public static void main( String[] argv ){
    // Reflection
    Agent[] agentAr = new Agent[8];
    agentAr[0] = new Agent( new CuadritoAgentProgram(Squares.WHITE));
    agentAr[1] = new Agent( new cajita(Squares.WHITE));
    agentAr[2] = new Agent( new squaresAgentHomework(Squares.WHITE));
    agentAr[3] = new Agent( new InteligSquaresAgent(Squares.WHITE));
    agentAr[4] = new Agent( new Cuadrito(Squares.WHITE));
    agentAr[5] = new Agent( new DeepBlue(Squares.WHITE));
    
/* 
    agentAr[0] = new Agent( new FatalitySquaresAgentProgram(Squares.WHITE));
    agentAr[1] = new Agent( new JavanaticosAgent(Squares.WHITE));
    agentAr[2] = new Agent( new KillerSquares(Squares.WHITE));
    agentAr[3] = new Agent( new Linked5(Squares.WHITE));
    agentAr[4] = new Agent( new SquaresPlayer(Squares.WHITE));
    agentAr[5] = new Agent( new TuringMachineAgentProgram(Squares.WHITE));
    agentAr[6] = new Agent( new YeisonAgent(Squares.WHITE));
    agentAr[7] = new Agent( new SquareHardAgentProgram(Squares.WHITE));
*/    
    Agent[] agentAd = new Agent[8];
    agentAd[0] = new Agent( new CuadritoAgentProgram(Squares.BLACK));
    agentAd[1] = new Agent( new cajita(Squares.BLACK));
    agentAd[2] = new Agent( new squaresAgentHomework(Squares.BLACK));
    agentAd[3] = new Agent( new InteligSquaresAgent(Squares.BLACK));
    agentAd[4] = new Agent( new Cuadrito(Squares.BLACK));
    agentAd[5] = new Agent( new DeepBlue(Squares.BLACK));
    
/*    
    agentAd[3] = new Agent( new Linked5(Squares.BLACK));
    agentAd[4] = new Agent( new SquaresPlayer(Squares.BLACK));
    agentAd[5] = new Agent( new TuringMachineAgentProgram(Squares.BLACK));
    agentAd[6] = new Agent( new YeisonAgent(Squares.BLACK));
    agentAd[7] = new Agent( new SquareHardAgentProgram(Squares.BLACK));
    Agent w_agent =  new Agent( new DummySquaresAgentProgram(Squares.WHITE));
    Agent b_agent =  new Agent( new DummySquaresAgentProgram(Squares.BLACK));
*/
    Agent w_agent =  agentAr[5];
    Agent b_agent =  agentAd[4];
    SquaresMainFrame frame = new SquaresMainFrame( w_agent, b_agent );
    frame.setVisible(true);
  }
}