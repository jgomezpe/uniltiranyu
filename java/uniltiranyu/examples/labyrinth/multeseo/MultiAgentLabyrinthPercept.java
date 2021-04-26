/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uniltiranyu.examples.labyrinth.multeseo;

import uniltiranyu.examples.labyrinth.LabyrinthPercept;
import uniltiranyu.examples.labyrinth.LabyrinthUtil;

/**
 *
 * @author Jonatan
 */
public class MultiAgentLabyrinthPercept extends LabyrinthPercept{
  public MultiAgentLabyrinthPercept( int value ) {
    super( value );
    int flag = (1<<4);
    set( LabyrinthUtil.TREASURE, (value & flag)==flag );     
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
