package uniltiranyu.examples.labyrinth.teseo.simple;

import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.simulate.util.SimpleLanguage;

public class TeseoMain {
  private static SimpleLanguage getLanguage(){
    return  new SimpleLanguage( new String[]{"front", "right", "back", "left", "treasure", "fail",
        "afront", "aright", "aback", "aleft"},
                                   new String[]{"no_op", "die", "advance", "rotate"}
                                   );
  }

  public static void main( String[] argv ){
    //  InteractiveAgentProgram p = new InteractiveAgentProgram( getLanguage() );
    RandomReflexTeseo p = new RandomReflexTeseo();
    p.setLanguage(getLanguage());
    LabyrinthDrawer.DRAW_AREA_SIZE = 600;
    LabyrinthDrawer.CELL_SIZE = 40;
    Labyrinth.DEFAULT_SIZE = 15;
    Agent agent = new Agent( p );
    TeseoMainFrame frame = new TeseoMainFrame( agent, getLanguage() );
    frame.setVisible(true); 
  }
}
