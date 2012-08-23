/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor;

/**
 *
 * @author priyathamanisetty
 */

import com.uca.cs.owleditor.OOMIOwlModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.JDesktopPane;

import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;


import org.semanticweb.owlapi.model.OWLOntology;


public class IDEPanel extends JPanel {
    
    //string to hold the path. Used when updating the file
    String FILE_PATH;
    
    
    
    OOMIOwlModel oomiOwlModel;
    OWLOntology workingOntology;
    //Panels
    
    JPanel ontologyPropertiesPanel = null;
    JButton addClassBtn, addPropBtn, removeBtn;
    
    RSyntaxTextArea ontologyDisplay;
    
    
    JTabbedPane tabbedPanel;
    // Holding reference to the Parent Frame. In this case it is IDE itself
    protected OwlEditorFrame parentFrame = null;
    
    
    protected JSplitPane tabSplitScreen = new JSplitPane();
    
    protected JSplitPane leftSideVerticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    
        
        
    //The left Side of the Screen 
    protected JPanel editorLeftPanel = new JPanel();
       protected JScrollPane leftScrollPane = new JScrollPane();
       protected JScrollPane leftDisplayContentPane = null;
        
    //The Right Side of the Screen 
    protected JPanel editorRightPanel = new JPanel();
        protected JScrollPane  rightScrollPane = new JScrollPane();
        protected JComponent rightDisplayContentPane = null;
    
           
        IDEPanel(JFrame frame,OOMIOwlModel model){
            
            oomiOwlModel = model;
            //workingOntology = ontology;
            generateSplitScreen();
            
            
        }
    
        protected void generateSplitScreen(){
           
            this.setLayout(new BorderLayout());
            this.setBackground(Color.LIGHT_GRAY);
            
           editorLeftPanel.setLayout(new BorderLayout());
           editorRightPanel.setLayout(new BorderLayout());
           
           
           editorLeftPanel.add(leftScrollPane, BorderLayout.CENTER);
           editorRightPanel.add(rightScrollPane, BorderLayout.CENTER);
           
           leftSideVerticalSplit.setDividerLocation(250);
           leftSideVerticalSplit.setTopComponent(leftScrollPane);
           
                    
           leftSideVerticalSplit.setBottomComponent(new OntologyProperties(oomiOwlModel));
           leftSideVerticalSplit.setBorder(BorderFactory.createLineBorder(Color.black));
           leftSideVerticalSplit.setContinuousLayout(true);
           
           
           tabSplitScreen.setDividerLocation(300);
           tabSplitScreen.setBorder(BorderFactory.createLineBorder(Color.black));
           tabSplitScreen.setContinuousLayout(true);
          
           tabSplitScreen.setLeftComponent(leftSideVerticalSplit);
           tabSplitScreen.setRightComponent(setRightSidePanelContent());
           
           
           this.add(tabSplitScreen, BorderLayout.CENTER);
           
        
        }
        
        
        protected JPanel setRightSidePanelContent(){
            JPanel rightSidePanel = new JPanel();
            rightSidePanel.setLayout(new BorderLayout());
            
            
            //rightSidePanel.add(new JLabel("I am inside Right Panel"));
            
            
            ontologyDisplay = new RSyntaxTextArea(45,90);
            //ontologyDisplay.setPreferredSize(new Dimension(650, 600));
            ontologyDisplay.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
            ontologyDisplay.setCodeFoldingEnabled(true);
            ontologyDisplay.setAntiAliasingEnabled(true);
            ontologyDisplay.setEditable(false);
            
            //ontologyDisplay.setLineWrap(true);
            
//            JScrollPane scrollPane = new JScrollPane(ontologyDisplay, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            
            RTextScrollPane sp = new RTextScrollPane(ontologyDisplay);
            sp.setFoldIndicatorEnabled(true);
            rightSidePanel.add(sp);
            //rightSidePanel.setLayout(new GridLayout());
            
            return rightSidePanel;
        }
        
        
        protected JPanel setLeftSideBottomPanel(){
        
            JPanel leftSideBottomPanel = new JPanel();
            
          
            return leftSideBottomPanel;
        }
    
        
      protected JPanel setupPropertiesGUI(){
                 
        ontologyPropertiesPanel = new JPanel();
        
        addClassBtn = new JButton("Add Class");
        addClassBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        addClassBtn.setEnabled(true);
        //addClassBtn.setSize(10, 10);
        //addClassBtn.setPreferredSize(new Dimension(10,5));
        addPropBtn = new JButton("Add Property");
	addPropBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        removeBtn = new JButton("Remove");
        removeBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,2));
        buttonsPanel.add(addClassBtn);
        buttonsPanel.add(addPropBtn);
        buttonsPanel.add(removeBtn);
          
        JPanel treePanel = new JPanel();
        JTree ontologyTree = new JTree();
        JScrollPane treeScrollPane = new JScrollPane(ontologyTree);
        treePanel.add(treeScrollPane);
       
        ontologyPropertiesPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = .25;
        gbc.anchor = GridBagConstraints.LINE_START;
        ontologyPropertiesPanel.add(buttonsPanel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty =1;
        gbc.fill = GridBagConstraints.BOTH;
        //gbc.anchor = GridBagConstraints.CENTER;
        ontologyPropertiesPanel.add(treeScrollPane,gbc);
        
        
        return ontologyPropertiesPanel;
    }
     
   
   public void updateTextArea(){
    try {

        ontologyDisplay.setText(readFileAsString(FILE_PATH));
    } catch (IOException ex) {
        Logger.getLogger(IDEPanel.class.getName()).log(Level.SEVERE, null, ex);
    }

}
      
   public void updateTextArea(String filePath){
        try {
            FILE_PATH = filePath;
            ontologyDisplay.setText(readFileAsString(filePath));
        } catch (IOException ex) {
            Logger.getLogger(IDEPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   }
   
   
   private static String readFileAsString(String filePath) throws java.io.IOException{
    byte[] buffer = new byte[(int) new File(filePath).length()];
    BufferedInputStream f = null;
    try {
        f = new BufferedInputStream(new FileInputStream(filePath));
        f.read(buffer);
        if (f != null) try { f.close(); } catch (IOException ignored) { }
    } catch (IOException ignored) { System.out.println("File not found or invalid path.");}
    return new String(buffer);
}

   
  
   
   
}
