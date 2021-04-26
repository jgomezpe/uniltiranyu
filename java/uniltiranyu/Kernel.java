package uniltiranyu;

import speco.array.Array;

/**
 * <p>Title: Kernel </p>
 *
 * <p>Description: A multi agent problem solution kernel</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class Kernel implements Runnable{
	/**
	 * Stop/Go checking flag
	 */
	public boolean flag = true;
	/**
	 * Collection of agents in the problem
	 */
	protected Array<Agent> agents;

	public Kernel( Agent agent ){
		agents = new Array<Agent>();
		agents.add(agent);
	}

	public Kernel( Array<Agent> _agents ) { agents = _agents; }

	public void stop(){ 
		flag = false;
		for( Agent a:agents) a.die();
	}

	public void run(){
		flag = true;
		for( Agent a:agents ){
			a.live();
			Thread t = new Thread(a);
			a.setThread(t);
			t.start();
			if(!flag) return;
		}
	}
	
	public Agent agent() { return agents.get(0); }

	public Agent agent(int index) { return agents.get(index); }
}