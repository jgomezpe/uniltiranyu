package uniltiranyu.examples.sudoku.naive;

import fwew.graph.goal.GoalTest;

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
public class NaiveSudokuGoalTest implements GoalTest<NaiveSudokuBoardState>{
	public NaiveSudokuGoalTest() {}
  
	@Override
	public boolean check(NaiveSudokuBoardState state) {
		return state.board.solved();
	}
}