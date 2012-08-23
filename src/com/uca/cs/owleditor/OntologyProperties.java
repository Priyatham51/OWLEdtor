/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor;

/**
 *
 * @author priyathamanisetty
 */

import com.uca.cs.owleditor.popup.PopupEdit;
import com.uca.cs.owleditor.popup.PopupNewObjectProperty;
import com.uca.cs.owleditor.popup.PopupRemover;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import javax.swing.JDesktopPane;

import com.uca.cs.owleditor.popup.PopupNew;
import com.uca.cs.owleditor.OOMIOwlModel;
import com.uca.cs.owleditor.popup.PopupEditV2;


import java.util.logging.Level;
import java.util.logging.Logger;



import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;



public class OntologyProperties extends JPanel {
    
    OWLOntology onto; 
    
    OOMIOwlModel oomiOwlModel;
    
    JPanel ontologyPropertiesPanel = null;
    
    
    JButton addClassBtn, addPropBtn, removeBtn, editBtn;
    
    
    public  OntologyProperties(OOMIOwlModel model){
       
        oomiOwlModel = model;
        onto = model.getWorkingOntology();
        setupGUI();
       
        
    }
    
    
    public void setupGUI(){
        
         ontologyPropertiesPanel = new JPanel();
        
        addClassBtn = new JButton("Add Class");
        addClassBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        addClassBtn.addActionListener(new ActionListener(){
        public void actionPerformed( ActionEvent e ) {
                addClassActionPerformed(e);
            }
        });
        
        addPropBtn = new JButton("Add Property");
	addPropBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        addPropBtn.addActionListener(new ActionListener(){
          public void actionPerformed( ActionEvent e ) {
                addPropActionPerformed(e);
            }     
        });
        removeBtn = new JButton("Remove");
        removeBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        removeBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
          removeActionPerfomed(e);
            
        }
    });
        
        editBtn = new JButton("Edit");
        editBtn.setHorizontalTextPosition(AbstractButton.LEFT);
        editBtn.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            
            editActionPerfomed(e);
            
        }
    });
        
        
        
        
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2,2));
        buttonsPanel.add(addClassBtn);
        buttonsPanel.add(addPropBtn);
        buttonsPanel.add(removeBtn);
        buttonsPanel.add(editBtn);
        
        

        
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
        
        
        
        
        this.add(ontologyPropertiesPanel);
        
        
    }
    
    
    
   public void addClassActionPerformed(ActionEvent e){
       System.out.println("Add class button pressed.");
       
       PopupNew popupPanel = new PopupNew(this,oomiOwlModel,"Class",oomiOwlModel.getWorkingOntology());
     
       popupPanel.setLocation(620,300);
       popupPanel.setVisible( true );
   }
    
  public void addPropActionPerformed(ActionEvent e){
       System.out.println("Add Property button pressed.");
       
       PopupNewObjectProperty popupPanel = new PopupNewObjectProperty(this,oomiOwlModel,"Property",oomiOwlModel.getWorkingOntology());
       
       popupPanel.setLocation(620,300);
       popupPanel.setVisible( true );
   }
  
  
  private void editActionPerfomed(ActionEvent e) {
                
      System.out.println("Edit Button Pressed.");
      PopupEditV2 popEdit = new PopupEditV2(this,oomiOwlModel,oomiOwlModel.getWorkingOntology());
      popEdit.setTitle("Class Editor");
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      popEdit.setLocation(620,300);
      popEdit.setVisible(true);
  
  
  }
  
  private void removeActionPerfomed(ActionEvent e) {
                
      System.out.println("Edit Button Pressed.");
      PopupRemover popEdit = new PopupRemover(oomiOwlModel.getWorkingOntology(),this,oomiOwlModel);
      popEdit.setTitle("Remover");
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      popEdit.setLocation(620,300);
      popEdit.pack();
      popEdit.setVisible(true);
  
  
  }
   
   
    
    
}
