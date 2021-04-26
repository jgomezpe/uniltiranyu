package uniltiranyu.examples.sudoku.naive;
import fwew.graph.goal.ConstantCost;
import fwew.graph.uninformed.DepthFirstSearch;
import speco.array.Array;
import uniltiranyu.Action;
import uniltiranyu.AgentProgram;
import uniltiranyu.Percept;
import uniltiranyu.examples.sudoku.*;

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
public class NaiveSudokuAgentProgram implements AgentProgram{
  Array<Action> cmd = new Array<Action>();
  public NaiveSudokuAgentProgram() {
  }

  public void init(){
    cmd.clear();
  }

  public Action compute( Percept percept ){
    if( cmd.size() == 0 ){
      NaiveSudokuBoardState initial_state = new NaiveSudokuBoardState((SudokuPercept)percept);
      int depth = initial_state.board.emptyPlaces();
      DepthFirstSearch<NaiveSudokuBoardState,Action> search = new DepthFirstSearch<NaiveSudokuBoardState,Action>(new NaiveSudokuGoalTest(), depth);
      cmd = search.apply( initial_state, new NaiveSudokuSearchSpace(),
                          new ConstantCost<NaiveSudokuBoardState,Action>() );
      if( cmd == null ){ cmd = new Array<Action>(); }
    }
    if( cmd.size() > 0 ){
      try{
    	Action action = cmd.get(0);
    	cmd.remove(0);
    	return action;
      }catch(Exception e){}
    }
    return null;
  }
}
