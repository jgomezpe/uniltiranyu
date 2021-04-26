package uniltiranyu.examples.games.sokoban;

import uniltiranyu.Percept;

public class SokobanPercept extends Percept{  
	public SokobanPercept( int left, int top, int right, int bottom, int value ) {
		set( SokobanUtil.WALL[0], top==SokobanBoard.WALL );
		set( SokobanUtil.WALL[1], right==SokobanBoard.WALL );
		set( SokobanUtil.WALL[2], bottom==SokobanBoard.WALL );
		set( SokobanUtil.WALL[3], left==SokobanBoard.WALL );
		set( SokobanUtil.BLOCK,  (value&SokobanBoard.BLOCK)==SokobanBoard.BLOCK);
		set( SokobanUtil.MARK,  (value&SokobanBoard.MARK)==SokobanBoard.MARK);	
	}
	
	public void rotate(){
		Object f = get(SokobanUtil.WALL[0]);
		for( int i=0; i<3; i++ )
			set( SokobanUtil.WALL[i], get(SokobanUtil.WALL[i+1]));
		
		set( SokobanUtil.WALL[3], f );
	}
}
