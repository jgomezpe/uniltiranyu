package uniltiranyu.examples.labyrinth.multeseo.eater;

import uniltiranyu.examples.labyrinth.LabyrinthUtil;
import uniltiranyu.examples.labyrinth.teseoeater.TeseoEaterPercept;

public class MultiTeseoEaterPercept extends TeseoEaterPercept {

    public MultiTeseoEaterPercept(int value) {
	super(value);	
    }

    @Override
    public void rotate( ){
      super.rotate();
      Object f = get(LabyrinthUtil.AGENT[0]);
      for( int i=0; i<3; i++ ){
        set( LabyrinthUtil.AGENT[i], get(LabyrinthUtil.AGENT[i+1]) );
      }
      set( LabyrinthUtil.AGENT[3], f );
    }   
}