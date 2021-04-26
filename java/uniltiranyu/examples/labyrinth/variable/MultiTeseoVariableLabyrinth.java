package uniltiranyu.examples.labyrinth.variable;

import speco.array.Array;
import uniltiranyu.Agent;
import uniltiranyu.Percept;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.examples.labyrinth.LabyrinthPercept;
import uniltiranyu.examples.labyrinth.multeseo.MultiAgentLabyrinthPercept;
import uniltiranyu.simulate.SimulatedAgent;

public class MultiTeseoVariableLabyrinth  extends TeseoVariableLabyrinth {
	  public static final int AGENT = 0;
	  public static final int TREASURE = 1;
	  public static final int RESOURCE = 2;

	  protected static String K = "key";

	  protected int option = AGENT;
	  protected int tx = 0;
	  protected int ty = 0;
	  protected int agent_id = 0;

	  @Override
	  protected LabyrinthPercept getPercept( int x, int y ){
        if( x >= 0 && x<structure.length && y >=0 && y<structure[0].length )
	      return new MultiAgentLabyrinthPercept( structure[x][y] );
        return new MultiAgentLabyrinthPercept( 0 );     
	  }

	  public Percept sense(Agent agent){
	    SimulatedAgent anAgent = (SimulatedAgent)agent;
	    int direction = ((Integer)anAgent.getAttribute(D)).intValue();
	    int x = ((Integer)anAgent.getAttribute(X)).intValue();
	    int y = ((Integer)anAgent.getAttribute(Y)).intValue();
	    LabyrinthPercept p = getPercept( x, y );
	    p.set("afront", false);
	    p.set("aleft", false);
	    p.set("aright", false);
	    p.set("aback", false);
	    for( Agent ag:agents ){
	        if( ag != agent ){
	            SimulatedAgent a = (SimulatedAgent)ag;               
	            int ax = ((Integer) a.getAttribute(X)).intValue();
	            int ay = ((Integer) a.getAttribute(Y)).intValue();
	            //System.out.println("("+x+","+y+") : ("+ax+","+ay+")");
	            if( !((Boolean)p.get("front")).booleanValue() && 
	                 y-1 == ay && x==ax  ){
	                p.set("afront", true);
	            }
	            if( !((Boolean)p.get("right")).booleanValue() && 
	                 x+1 == ax && y==ay ){
	                p.set("aright", true);
	            }
	            if( !((Boolean)p.get("back")).booleanValue() && 
	                 y+1 == ay && x==ax ){
	                p.set("aback", true);
	            }
	            if( !((Boolean)p.get("left")).booleanValue() && 
	                 x-1 == ax && y==ay ){
	                p.set("aleft", true);
	            }
	        }    
	    }
	    for( int i=0; i<direction; i++ ){ p.rotate(); }
	    int i=0;
	    while( i<failAgents.size() && failAgent(i) != agent ){ i++; }
	    p.set("fail", i<failAgents.size());
	    return p;
	  }

	  public MultiTeseoVariableLabyrinth( Array<Agent> _agents, int[][] _structure, double p ) {
	    super( _agents, _structure, p );
	  }


	  public void setOption( int _option ){ option = _option; }

	  @Override
	  public boolean edit( int X, int Y ){
	    boolean flag = super.edit(X,Y);
	    if( !flag ){
	      X -= LabyrinthDrawer.MARGIN;
	      Y -= LabyrinthDrawer.MARGIN;
	      int x = X/LabyrinthDrawer.CELL_SIZE;
	      int y = Y/LabyrinthDrawer.CELL_SIZE;
	      switch( option ){
	        case AGENT:
	          setAgentPosition( agent_id, x, y, 0 );
	        break;
	        case TREASURE:
	          if( (structure[tx][ty] & (1<<4)) == (1<<4) )
	            structure[tx][ty] ^= (1<<4);
	          structure[x][y] ^= (1<<4);
	          tx = x;
	          ty = y;
	        break;
	      }
	    }
	    return flag;
	  }
}
