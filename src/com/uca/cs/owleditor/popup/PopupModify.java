/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;

import com.uca.cs.owleditor.OOMIOwlModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 *
 * @author panise
 */
public class PopupModify extends JFrame implements ActionListener{

    
    String names[];
    String type;
    List<JCheckBox> checkBoxes; 
     
    OWLOntology ontology;
    OWLEntity newEntity;
    OWLDataFactory factory;
    OOMIOwlModel oomiOwlModel;
    PopupEditV2 popupEditor;
    JButton saveBtn,cancelBtn;
    
    
    JPanel mainPanel, mainCheckBoxPanel;
    JPanel mainBtnPanel;

    public PopupModify(String type, OOMIOwlModel oomiOwlModel,PopupEditV2 popupEdit) {
        
        
       checkBoxes = new ArrayList<JCheckBox>();
        this.type = type;
        this.oomiOwlModel = oomiOwlModel;
        this.ontology = oomiOwlModel.getWorkingOntology();
        this.popupEditor = popupEdit;
        
        try{
            setupUI();
            JScrollPane mainScrollPane = new JScrollPane(mainCheckBoxPanel);
            mainScrollPane.setPreferredSize(new Dimension(300,50));
            Container content = getContentPane();
            content.setLayout(new BorderLayout());
            content.add(mainScrollPane, BorderLayout.CENTER);
            content.add(mainBtnPanel,BorderLayout.PAGE_END);
            setSize(890,390);
            setResizable(true);
            setTitle("Modify "+type);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
    }

    private void setupUI() {
       mainCheckBoxPanel = new JPanel();
       mainCheckBoxPanel.setLayout(new BoxLayout(mainCheckBoxPanel,BoxLayout.Y_AXIS));
       
       mainBtnPanel = new JPanel();
       mainBtnPanel.setLayout(new GridBagLayout());
       
       GridBagConstraints gbc = new GridBagConstraints();
       
       saveBtn = new JButton("Save");
       saveBtn.addActionListener(this);
       gbc.gridx =0;
       gbc.gridy =0;
       gbc.ipadx =20;
       mainBtnPanel.add(saveBtn,gbc);
       
       cancelBtn = new JButton("Cancel");
       cancelBtn.addActionListener(this);
       gbc.insets= new Insets(0,15,0,0);
       gbc.gridx = 1;
       gbc.gridy = 0;
       mainBtnPanel.add(cancelBtn,gbc);
         
        if(type.equals("superClass"))
        {
            Set superClassSet = new TreeSet();
            superClassSet.addAll(ontology.getClassesInSignature());
           Iterator superIterator = superClassSet.iterator();
           while(superIterator.hasNext()){
             OWLClass cls = (OWLClass)superIterator.next();
             JCheckBox jcb = new JCheckBox(cls.toStringID());
             
              Set currentClassList =new TreeSet();
              currentClassList.addAll(popupEditor.getSelectedClass().getSuperClasses(ontology));
              Iterator currentClassIterator = currentClassList.iterator();
              while(currentClassIterator.hasNext()){
                  OWLClass clsCheck = (OWLClass) currentClassIterator.next();
                  if(cls.toStringID().equals(clsCheck.toStringID()))
                  {
                      jcb.setSelected(true);
                  }
              }
             checkBoxes.add(jcb);
                 
           }
           
          
           Iterator checkBoxIterator = checkBoxes.listIterator();
           while(checkBoxIterator.hasNext()){
               mainCheckBoxPanel.add((JCheckBox)checkBoxIterator.next());
           }
           
          
        }
        
        if(type.equals("subClass"))
        {
            Set superClassSet = new TreeSet();
            superClassSet.addAll(ontology.getClassesInSignature());
           Iterator superIterator = superClassSet.iterator();
           while(superIterator.hasNext()){
             OWLClass cls = (OWLClass)superIterator.next();
              Set currentClassList =new TreeSet();
              JCheckBox jcb = new JCheckBox(cls.toStringID());
              currentClassList.addAll(popupEditor.getSelectedClass().getSubClasses(ontology));
              Iterator currentClassIterator = currentClassList.iterator();
              while(currentClassIterator.hasNext()){
                  OWLClass clsCheck = (OWLClass) currentClassIterator.next();
                  if(cls.toStringID().equals(clsCheck.toStringID()))
                  {
                      jcb.setSelected(true);
                  }
              }
             checkBoxes.add(jcb);
                 
           }
           
           
           Iterator checkBoxIterator = checkBoxes.listIterator();
           while(checkBoxIterator.hasNext()){
               mainCheckBoxPanel.add((JCheckBox)checkBoxIterator.next());
           }
           
        }
        if(type.equals("annotation"))
        {
            
            
           
            Set annotationsSet = new TreeSet();
            annotationsSet.addAll(popupEditor.getSelectedClass().getAnnotationAssertionAxioms(ontology));
            Iterator superIterator = annotationsSet.iterator();
            System.out.println(superIterator.next().toString());
            
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()== saveBtn){
            
            List<String> names = new ArrayList<String>();
            
            Iterator checkBoxIterator = checkBoxes.listIterator();
       
            while(checkBoxIterator.hasNext())
            {
                JCheckBox jcb = (JCheckBox)checkBoxIterator.next();
                if(jcb.isSelected()){
                    names.add(jcb.getText());
                    
                }
                
            }
            
           oomiOwlModel.addClass(popupEditor.getSelectedClass(), names,type);
          if(type.equals("superClass")){
              popupEditor.superClassList.setListData(popupEditor.fillSubClassOfList());
          }
          if(type.equals("subClass")){
              popupEditor.subClassList.setListData(popupEditor.fillSuperClassOfList());
          }
           this.dispose();
        }
        
        if(e.getSource()== cancelBtn){
          this.dispose();
        }
        
    }
    
    
    
    
    
    
    
   
    
    
}




