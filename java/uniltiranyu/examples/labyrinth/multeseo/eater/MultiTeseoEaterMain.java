package uniltiranyu.examples.labyrinth.multeseo.eater;

import speco.array.Array;
import uniltiranyu.Agent;
import uniltiranyu.AgentProgram;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.examples.labyrinth.teseo.simple.RandomReflexTeseo;
import uniltiranyu.simulate.util.SimpleLanguage;

public class MultiTeseoEaterMain {
    private static SimpleLanguage getLanguage(){
	    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "treasure", "fail",
	        "afront", "aright", "aback", "aleft", "resource", "resource-color", "resource-shape", "resource-size", "resource-weight"},
	                                   new String[]{"no_op", "die", "advance", "rotate", "eat"}
	                                   );
	  }

	  public static void main( String[] argv ){
	     AgentProgram[] teseo = new AgentProgram[12];
	    int index1 = 0;
	    int index2 = 1;
	    teseo[index1] = new RandomReflexTeseo( getLanguage() );
	    teseo[index2] = new RandomReflexTeseo( getLanguage() );
	    
	    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
	    LabyrinthDrawer.CELL_SIZE = 40;
	    Labyrinth.DEFAULT_SIZE = 15;
	    
	    Agent agent1 = new Agent(teseo[index1]);    
	    Agent agent2 = new Agent(teseo[index2]);
	    
	    //Agent agent3 = new Agent(p3);
	    Array<Agent> agent = new Array<Agent>();
	    agent.add(agent1);
	    agent.add(agent2);
//	    Agent agent = new Agent( new RandomReflexTeseoAgentProgram( getLanguage() ) );
	    MultiTeseoEaterMainFrame frame = new MultiTeseoEaterMainFrame( agent, getLanguage() );
	    frame.setVisible(true); 
	  }
}
