package uniltiranyu.examples.labyrinth.teseo.simple;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import aplikigo.gui.awt.LogOutputStream;
import uniltiranyu.Agent;
import uniltiranyu.examples.labyrinth.Labyrinth;
import uniltiranyu.examples.labyrinth.LabyrinthDrawer;
import uniltiranyu.examples.labyrinth.LabyrinthMainFrame;
import uniltiranyu.examples.labyrinth.teseo.TeseoLabyrinth;
import uniltiranyu.examples.labyrinth.teseo.TeseoPerceptDrawer;
import uniltiranyu.simulate.util.SimpleLanguage;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */



public class TeseoMainFrame extends LabyrinthMainFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -644722327792383799L;

	protected SimpleTeseoJavaProgrammingFrame ide = new SimpleTeseoJavaProgrammingFrame();

    protected JMenuItem jMenuEditAgentProgram = new JMenuItem();
    @SuppressWarnings("resource")
	public TeseoMainFrame( Agent _agent, SimpleLanguage _language ) {
        super( "Teseo", _agent, _language );
        this.setSize(new Dimension(660, 740));
        this.jMenuEditAgentProgram.setText("Editar");
        //this.jMenuAgentProgram.add(this.jMenuEditAgentProgram);
        this.jMenuEditAgentProgram.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ide.setVisible(true);
        }
        });

        new LogOutputStream(ide.getLogPanel().getOutArea(), true);
        //System.setOut(new PrintStream(os));
    }
  public Labyrinth newLabyrinthInstance(){
    labyrinth = new TeseoLabyrinth( agent, new int[Labyrinth.DEFAULT_SIZE][Labyrinth.DEFAULT_SIZE] );
    return labyrinth;
  }

  public void initLabyrinth(){
      if( drawArea.getDrawer()==null ) drawArea.setDrawer(new LabyrinthDrawer(new TeseoPerceptDrawer()));
      super.initLabyrinth();
  }
  
  protected void jMenuLoadAgentProgram_actionPerformed(ActionEvent e) {
	  /**
      Loader ccl = new Loader();
      ccl.set("lib", "classes", "resources", "classes" );
// Load the main class through our CCL
      String progClass = "TheAgentProgram";
      try{
        Class clas = ccl.loadClass( progClass );

        AgentProgram agent_program = (AgentProgram)clas.newInstance();

        Class mainArgType[] = {(new SimpleLanguage(null,null)).getClass()};
        java.lang.reflect.Method main = clas.getMethod("setLanguage", mainArgType);
        Object argsArray[] = {language};
        main.invoke(agent_program, argsArray);

        Agent agent = labyrinth.getAgent();
        agent.setProgram(agent_program);
        labyrinth.init(agent);
        JOptionPane.showMessageDialog(this,"Felicitaciones!!! Su programa ha sido cargado");
      }catch( Exception ex ){
        JOptionPane.showMessageDialog(this,"Problemas!!! Se presentaron errores al cargar su programa");
      }
      */
    }
    
}