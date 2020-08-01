package nsgl.agents.examples.labyrinth.variable;
import nsgl.agents.Action;
import nsgl.agents.AgentProgram;
import nsgl.agents.Percept;
import nsgl.random.raw.UsesRawGenerator;

public class WallDaemon implements UsesRawGenerator, AgentProgram{
	protected double probability;
	public WallDaemon( double p ){ probability = p;	}
	
	@Override
	public Action compute(Percept p){
		if( getRaw().bool(probability) ){ return new Action("change_walls"); }
		else return new Action("no_op");
	}

	@Override
	public void init(){}
}