package uniltiranyu.examples.labyrinth;

import uniltiranyu.Percept;

public class LabyrinthPercept extends Percept{  
  public LabyrinthPercept( int value ) {
      int n = 4;
      for( int i=0; i<n; i++ ){
	  int flag = (1<<i);
	  set( LabyrinthUtil.WALL[i], (value & flag)==flag );
      }
  }
    
  public void rotate(){
    Object f = get(LabyrinthUtil.WALL[0]);
    for( int i=0; i<3; i++ ){
      set( LabyrinthUtil.WALL[i], get(LabyrinthUtil.WALL[i+1]));
    }
    set( LabyrinthUtil.WALL[3], f );
  }
}