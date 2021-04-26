package uniltiranyu.examples.labyrinth.teseo.simple;

import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.simulate.util.SimpleLanguage;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: Universidad Nacional de Colombia</p>
 *
 * @author Jonatan Gómez
 * @version 1.0
 */
public abstract class SimpleTeseoAgentProgram  implements AgentProgram{
  protected SimpleLanguage language;
  protected Array<String> cmd = new Array<String>();
  public SimpleTeseoAgentProgram( ) {
  }

  public SimpleTeseoAgentProgram(   SimpleLanguage _language  ) {
	  language = _language;
  }

  public void setLanguage(  SimpleLanguage _language ){
    language = _language;
  }

  public void init(){
    cmd.clear();
  }

  public abstract int accion( boolean PF, boolean PD, boolean PA, boolean PI, boolean MT, boolean FAIL );

  /**
   * execute
   *
   * @param perception Perception
   * @return Action[]
   */
  public Action compute(Percept p){
    if( cmd.size() == 0 ){

      boolean PF = ( (Boolean) p.get(language.getPercept(0))).
          booleanValue();
      boolean PD = ( (Boolean) p.get(language.getPercept(1))).
          booleanValue();
      boolean PA = ( (Boolean) p.get(language.getPercept(2))).
          booleanValue();
      boolean PI = ( (Boolean) p.get(language.getPercept(3))).
          booleanValue();
      boolean MT = ( (Boolean) p.get(language.getPercept(4))).
          booleanValue();
      boolean FAIL = ( (Boolean) p.get(language.getPercept(5))).
              booleanValue();

      int d = accion(PF, PD, PA, PI, MT, FAIL);
      if (0 <= d && d < 4) {
        for (int i = 1; i <= d; i++) {
          cmd.add(language.getAction(3)); //rotate
        }
        cmd.add(language.getAction(2)); // advance
      }
      else {
        cmd.add(language.getAction(0)); // die
      }
    }
    try{
    	String x = cmd.get(0);
        cmd.remove(0);
        return new Action(x);
    }catch(Exception e){}
    return null;
  }

  /**
   * goalAchieved
   *
   * @param perception Perception
   * @return boolean
   */
  public boolean goalAchieved( Percept p ){
    return (((Boolean)p.get(language.getPercept(4))).booleanValue());
  }
}
