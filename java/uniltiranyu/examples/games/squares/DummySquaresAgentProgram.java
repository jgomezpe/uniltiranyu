/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.squares;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;

/**
 *
 * @author Jonatan
 * Implements an AgentProgram
 */
public class DummySquaresAgentProgram implements AgentProgram {
    // Player's color
    protected String color;
    // May use any structure as memory..
    /**
     * Creates a Dummy agent for playing squares
     * @param color Player's color
     */
    public DummySquaresAgentProgram( String color ){
        this.color = color;        
    }
    
    @Override
    /**
     * Computes an action to be carried by the agent according to the perception
     * of the environment
     * @param p Perception received by the agen program     * 
     */
    public Action compute(Percept p) {
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        // Determines if it is the agents turn
        if( p.get(Squares.TURN).equals(color) ){
            // Gets the size of the board
            int size = Integer.parseInt((String)p.get(Squares.SIZE));
            int i = 0;
            int j = 0;
            // Gets an square randomly and try to draw a border if possible
            Array<String> v = new Array<String>();
            while(v.size()==0){
              i = (int)(size*Math.random());
              j = (int)(size*Math.random());
              // p.get(i+":"+j+":"+Squares.LEFT)
              // gets "true" if the left border of square (i,j) is set, "false" otherwise
              if(((String)p.get(i+":"+j+":"+Squares.LEFT)).equals(Squares.FALSE))
                v.add(Squares.LEFT);
              if(((String)p.get(i+":"+j+":"+Squares.TOP)).equals(Squares.FALSE))
                v.add(Squares.TOP);
              if(((String)p.get(i+":"+j+":"+Squares.BOTTOM)).equals(Squares.FALSE))
                v.add(Squares.BOTTOM);
              if(((String)p.get(i+":"+j+":"+Squares.RIGHT)).equals(Squares.FALSE))
                v.add(Squares.RIGHT);
            }
            try{
            	String move = v.get((int)(Math.random()*v.size()));
            	// Action( i+":"+j+":"+Squares.BOTTOM ) 
            	// draws bottom border of square (i,j)
                return new Action( i+":"+j+":"+move);
            }catch(Exception e){}
        }
        return new Action(Squares.PASS);
    }

    @Override
    public void init() {
    }
}