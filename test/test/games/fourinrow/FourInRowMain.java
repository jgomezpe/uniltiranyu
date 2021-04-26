/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test.games.fourinrow;

import uniltiranyu.Agent;
import uniltiranyu.AgentProgram;
import uniltiranyu.examples.games.fourinrow.FourInRowMainFrame;

/**
 *
 * @author Jonatan
 */
public class FourInRowMain {
  public static void main( String[] argv ){
      String color = "black";
      AgentProgram[] b_agent = new AgentProgram[] {
	      new BetterAgentProgram(color), //<-- Posición 0 Laura y Diego
	      new DummyFourInRowAgentProgram(color), // Cesar y Camilo ??
	      new DummyAgentProgram(color), // Juan David, Danny y Tom
	      new FbDummyNew(color), // Alci Rene, alex rodrigo , mateo aguila
	      new MatewThing(color), // Daniel Pallares y Mateo Sebastian Barragan
	      new MinimaxFourInRowAgentProgram(color), // Miguel peña y Juan Pablo Giron
	      new MinMaxFourInRowAgentProgram(color), // felipe, duvan y juan david
	      new MyFourInRowAgentProgram(color) // Posicion 7  verbo sebastián, Juan Pablo y Carlos Daniel
      };
     
      color = "white";
      AgentProgram[] w_agent = new AgentProgram[] {
	      new BetterAgentProgram(color),
	      new DummyFourInRowAgentProgram(color),
	      new DummyAgentProgram(color), // Juan David, Danny y Tom
	      new FbDummyNew(color),
	      new MatewThing(color),
	      new MinimaxFourInRowAgentProgram(color),
	      new MinMaxFourInRowAgentProgram(color),
	      new MyFourInRowAgentProgram(color)
      };

      FourInRowMainFrame frame = new FourInRowMainFrame( 
	      new Agent(w_agent[5]), new Agent(b_agent[2]) );
      
      frame.setVisible(true);
    // Reflection
/*      SwingUtilities.invokeLater(new Runnable() {
          public void run() {
        	  JPanel jPanel1 = new JPanel();
        	  BorderLayout borderLayout2 = new BorderLayout();
        	  JLabel jLabel1 = new JLabel();
        	  JTextField jTextField1 = new JTextField();
        	  JLabel jLabel2 = new JLabel();
        	  JTextField jTextField2 = new JTextField();
        	  JButton jButton1 = new JButton();
        	  JButton jButton2 = new JButton();
        	    jLabel1.setText("Dimension:");
        	    jTextField1.setPreferredSize(new Dimension(37, 20));
        	    jTextField1.setText("8");
        	    jLabel2.setText("Time (sec):");
        	    jTextField2.setPreferredSize(new Dimension(37, 20));
        	    jTextField2.setText("10");
        	    jButton1.setText("Init");
        	    jButton1.addActionListener(new java.awt.event.ActionListener() {
        	      public void actionPerformed(ActionEvent e) {
        	        System.out.println("Hello");
        	      }
        	    });
        	    jPanel1.add(jLabel1, null);
        	    jPanel1.add(jTextField1, null);
        	    jPanel1.add(jLabel2, null);
        	    jPanel1.add(jTextField2, null);
        	    jPanel1.add(jButton1, null);
        	    jPanel1.add(jButton2, null);

        	  JFrame frame = new JFrame("My JFrame Example");
              frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
              frame.setSize(new Dimension(430, 540));
              frame.getContentPane().setLayout(borderLayout2);

              frame.getContentPane().add(jPanel1,  BorderLayout.SOUTH);
              
              frame.setVisible(true);        	  
        	  
          }
      });*/    
  }
    
}
