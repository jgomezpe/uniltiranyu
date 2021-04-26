package uniltiranyu.examples.labyrinth.teseoeater;
import java.awt.*;

import uniltiranyu.examples.labyrinth.LabyrinthUtil;
import uniltiranyu.examples.labyrinth.teseo.TeseoPercept;

public class TeseoEaterPercept extends TeseoPercept{
  public Color[] colors = new Color[]{
      Color.black, Color.blue
  };

  public TeseoEaterPercept( int value ){
    super( value );
    setFood( value );
  }

  protected void setFood( int value ){
      int FOOD_POS = 5;
      int FOOD_PROPERTIES = LabyrinthUtil.RESOURCE.length-1;
      int flag = (1<<FOOD_POS);
      boolean thereis = (value & flag)==flag;
      set(LabyrinthUtil.RESOURCE[0], thereis);
      if( thereis ){
	  for( int bit=1; bit<=FOOD_PROPERTIES; bit++ ){
	      flag = (1 << (bit+FOOD_POS));
	      set(LabyrinthUtil.RESOURCE[bit], (value & flag)==flag);
	  }
      }
  }
}
