package uniltiranyu.examples.labyrinth.variable;

import hazarda.Hazarda;
import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.examples.labyrinth.LabyrinthPercept;
import uniltiranyu.examples.labyrinth.teseo.TeseoPercept;

public class TeseoVariableLabyrinth  extends Labyrinth {
	protected int x = -1;
	protected int y;
	protected boolean flag = false;

	  protected static Array<Agent> add( Agent agent ){
		  Array<Agent> v = new Array<Agent>();
		  v.add(agent);
		  return v;
	  }
	  
	  protected static Array<Agent> add( double p, Array<Agent> v ){
		  Agent a = new Agent( new WallDaemon(p) );
		  v.add( a );
		  return v;
	  }
	  
	  protected LabyrinthPercept getPercept( int x, int y ){
	    return new TeseoPercept( structure[x][y] );
	  }

	  public TeseoVariableLabyrinth( Array<Agent> _agents, int[][] _structure, double p ) {
	    super( add(p, _agents), _structure );
	    int n = agentsNumber();
	    setAgentPosition(n-1, -1, -1, 0);
	  }

	  public TeseoVariableLabyrinth( Agent agent, int[][] _structure,  double p ){
	    this( add(agent), _structure, p );
	  }

	  public Labyrinth copy(){
		  double p = 0.0;
		  int i=0;
		  while( i<agents.size() && !(agent(i).getProgram() instanceof WallDaemon) ){
			  i++;
		  }
		  if( i<agents.size() ){
			  WallDaemon daemon = (WallDaemon)agent(i).getProgram();
			  p = daemon.probability;
		  }
	    return new TeseoVariableLabyrinth( agents, structure.clone(), p );
	  }

	  public boolean edit( int X, int Y ){
	    boolean flag = super.edit(X,Y);
	    if( !flag ){
	      X -= LabyrinthDrawer.MARGIN;
	      Y -= LabyrinthDrawer.MARGIN;
	      int x = X/LabyrinthDrawer.CELL_SIZE;
	      int y = Y/LabyrinthDrawer.CELL_SIZE;
	      if( 0<=x && x<LabyrinthDrawer.DIMENSION && 0<=y && y<LabyrinthDrawer.DIMENSION ){
	        structure[x][y] ^= (1 << 4);
	        flag = true;
	      }else{
	        flag = false;
	      }
	    }
	    return flag;
	  }

	  public boolean act(Agent agent, Action action){
		if( agent.getProgram() instanceof WallDaemon ){
		  if( action.getCode()=="change_walls"){
			int COLUMNS = getColumnsNumber();
			int ROWS = getRowsNumber();
			if( x >= 0 ){
	  		  if( flag ){
			    if( x < COLUMNS ) structure[x][y] ^= L;
		        if( x > 0 )  structure[x-1][y] ^= R;
			  }else{
	            if( y < ROWS ) structure[x][y] ^= F;
	            if( y > 0 )  structure[x][y-1] ^= B;				
		      }
			}
			x = Hazarda.uniform(COLUMNS);
		    y = Hazarda.uniform(ROWS);
			flag = Hazarda.bool();
			if( flag ){
			  if( x < COLUMNS ) structure[x][y] ^= L;
		      if( x > 0 )  structure[x-1][y] ^= R;
		    }else{
	          if( y < ROWS ) structure[x][y] ^= F;
		      if( y > 0 )  structure[x][y-1] ^= B;				
			}
		  }	
	  	  return true;
		}else{
		  return super.act(agent, action);
		}  	  
	  }
}