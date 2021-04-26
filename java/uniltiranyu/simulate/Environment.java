package uniltiranyu.simulate;

import speco.array.Array;
import uniltiranyu.*;
import uniltiranyu.simulate.gui.*;

/**
 * <p>Title: Environment </p>
 *
 * <p>Description: The environment for the given agents problem </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public abstract class Environment extends Kernel implements AgentArchitecture{
	protected long delay = 0;
	protected Array<EnvironmentView> views = new Array<EnvironmentView>();

	public Environment( Agent agent ) {
		super(agent);
		Array<Agent> newagents = new Array<Agent>();
		for( Agent a: agents ) newagents.add( new SimulatedAgent( this, a.getProgram()) );
		agents = newagents;
	}

	public Environment( Array<Agent> _agents ) {
		super( _agents );
		Array<Agent> newagents = new Array<Agent>();
		for( Agent a: agents ) newagents.add( new SimulatedAgent( this, a.getProgram()) );
		agents = newagents;
	}

	public void setDelay( long _delay ){ delay = _delay; }

	public void registerView( EnvironmentView view ){ 
	    views.add(view); 
	}

	public void updateViews( String message ){ for( EnvironmentView v:views ) v.envChanged( message ); }
  
	public int agentsNumber(){ return agents.size(); }	
}