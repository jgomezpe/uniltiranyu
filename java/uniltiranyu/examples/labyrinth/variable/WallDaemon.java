package uniltiranyu.examples.labyrinth.variable;

import hazarda.Hazarda;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;

public class WallDaemon implements AgentProgram{
	protected double probability;
	public WallDaemon( double p ){ probability = p;	}
	
	@Override
	public Action compute(Percept p){
		if( Hazarda.bool(probability) ){ return new Action("change_walls"); }
		else return new Action("no_op");
	}

	@Override
	public void init(){}
}