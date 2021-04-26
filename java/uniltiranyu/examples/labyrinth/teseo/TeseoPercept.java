package uniltiranyu.examples.labyrinth.teseo;

import uniltiranyu.examples.labyrinth.LabyrinthPercept;
import uniltiranyu.examples.labyrinth.LabyrinthUtil;

public class TeseoPercept extends LabyrinthPercept{
  public TeseoPercept( int value ) {
    super( value );
    int flag = (1<<4);
    set( LabyrinthUtil.TREASURE, (value & flag)==flag );
  }
}
