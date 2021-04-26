package uniltiranyu.examples.labyrinth.variable;

import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.examples.labyrinth.teseo.simple.RandomReflexTeseo;
import uniltiranyu.simulate.util.SimpleLanguage;

public class TeseoVariableMain {
	  private static SimpleLanguage getLanguage(){
		    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "exit",
		        "afront", "aright", "aback", "aleft"},
		                                   new String[]{"no_op", "die", "advance", "rotate"}
		                                   );
		  }

		  public static void main( String[] argv ){
		    //  InteractiveAgentProgram p = new InteractiveAgentProgram( getLanguage() );
//		    TeseoSimple p = new TeseoSimple();
		    RandomReflexTeseo p = new RandomReflexTeseo();
		    p.setLanguage(getLanguage());
		    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
		    LabyrinthDrawer.CELL_SIZE = 40;
		    Labyrinth.DEFAULT_SIZE = 15;
		    Agent agent = new Agent( p );
		    TeseoVariableMainFrame frame = new TeseoVariableMainFrame( agent, getLanguage(), 0.1 );
		    frame.setVisible(true); 
		  }
}
