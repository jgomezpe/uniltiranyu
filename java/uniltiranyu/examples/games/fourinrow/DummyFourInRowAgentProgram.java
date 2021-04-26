/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.games.fourinrow;

import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;

/**
 *
 * @author Jonatan
 */
public class DummyFourInRowAgentProgram implements AgentProgram {
    protected String color;
    public DummyFourInRowAgentProgram( String color ){
        this.color = color;        
    }
    
    @Override
    public Action compute(Percept p) {        
        long time = (long)(200 * Math.random());
        try{
           Thread.sleep(time);
        }catch(Exception e){}
        if( p.get(FourInRow.TURN).equals(color) ){
        	int n = Integer.parseInt((String)p.get(FourInRow.SIZE));
            int i = (int)(n*Math.random());
            int j = (int)(n*Math.random());
            boolean flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
            while( !flag ){
                i = (int)(n*Math.random());
                j = (int)(n*Math.random());
                flag = (i==n-1) || !p.get((i+1)+":"+j).equals((String)FourInRow.SPACE);
            }
            return new Action( i+":"+j+":"+color );
        }
        return new Action(FourInRow.PASS);
    }

    @Override
    public void init() {
    }
    
}