package test.labyrinth.teseo;
import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.*;
import uniltiranyu.examples.labyrinth.teseo.simple.RandomReflexTeseo;
import uniltiranyu.examples.labyrinth.teseo.simple.SimpleTeseoAgentProgram;
import uniltiranyu.examples.labyrinth.teseo.simple.TeseoSimple;
import uniltiranyu.simulate.util.*;

public class TeseoMain {
  private static SimpleLanguage getLanguage(){
    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "treasure", "fail",
        "afront", "aright", "aback", "aleft"},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   );
  }

  public static void main( String[] argv ){
    //  InteractiveAgentProgram p = new InteractiveAgentProgram( getLanguage() );
    //TeseoSimple p = new TeseoSimple();
    //RandomReflexTeseo p = new RandomReflexTeseo();
	//SimpleTeseoAgentProgram p = new SmartSimple();
	//SimpleTeseoAgentProgram p = new TeseoDFS();
	//SimpleTeseoAgentProgram p = new TeseoTeso();
	//SimpleTeseoAgentProgram p = new TeseoSimple();
	//SimpleTeseoAgentProgram p = new MegaAgent();
	//SimpleTeseoAgentProgram p = new InteligLabyrinth();  
	SimpleTeseoAgentProgram p = new TeseoTest();
    p.setLanguage(getLanguage());
    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
    LabyrinthDrawer.CELL_SIZE = 40;
    Labyrinth.DEFAULT_SIZE = 15;
    Agent agent = new Agent( p );
    TeseoMainFrame frame = new TeseoMainFrame( agent, getLanguage() );
    frame.setVisible(true); 
  }
}
