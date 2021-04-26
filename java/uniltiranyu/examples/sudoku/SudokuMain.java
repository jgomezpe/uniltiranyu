package uniltiranyu.examples.sudoku;
import uniltiranyu.Agent;
import uniltiranyu.examples.sudoku.naive.*;
import uniltiranyu.simulate.util.Language;

public class SudokuMain {
  private static Language getLanguage(){
    return  new SudokuLanguage();
  }

  public static void main( String[] argv ){
    //    Agent agent = new Agent( new InteractiveAgentProgram( getLanguage() ) );
    Agent agent = new Agent( new NaiveSudokuAgentProgram() );
    SudokuMainFrame frame = new SudokuMainFrame( agent, getLanguage() );
    frame.setVisible(true);
  }
}
