/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;

import com.uca.cs.owleditor.OOMIOwlModel;
import com.uca.cs.owleditor.utils.ui.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 *
 * @author panise
 */
public class PopupRemover extends JFrame implements ActionListener{

    
    String type;
     JButton removeBtn, cancelBtn;
     
     JComboBox headerBox = new JComboBox();
     JComboBox nameBox = new JComboBox();
    
    
    OWLOntology ontology;
    OWLEntity newEntity;
    OWLDataFactory factory;
    JPanel IDEHandler;
    OOMIOwlModel oomiOwlModel;
    OWLEntity lastSelectedParent;
    IRI documentIRI = null;

    public PopupRemover(OWLOntology ontology, JPanel IDEHandler, OOMIOwlModel oomiOwlModel) {
        this.type ="";
        this.ontology = ontology;
        this.IDEHandler = IDEHandler;
        this.oomiOwlModel = oomiOwlModel;
        
        try {
			setupUI();			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    
    
    private void setupUI() {
       
         this.ontology = oomiOwlModel.getWorkingOntology();
        JPanel mainPanel = new JPanel(new SpringLayout());
		
	JLabel headerLbl = new JLabel();
        
        headerBox = new JComboBox();
		
                
                headerLbl.setText("Adding OWL Property");
                
                headerBox.addItem("OWL Class");
                headerBox.addItem("OWL Object Property");
                headerBox.addItem("OWL Data Property");
                headerBox.addItem("OWL Individual");
                headerBox.addActionListener(this);     
                headerBox.setSelectedIndex(0);
                
               mainPanel.add(new JLabel("Choose Type"));
               mainPanel.add(headerBox);
               
               mainPanel.add(new JLabel("Name"));
               mainPanel.add(nameBox);
               
               
               JPanel btnPanel = new JPanel();
	       btnPanel.setLayout(new GridBagLayout());
               GridBagConstraints gbc = new GridBagConstraints();
       
               removeBtn = new JButton("Remove");
		removeBtn.addActionListener(this);
               gbc.gridx =0;
               gbc.gridy =0;
               gbc.ipadx =200;
               btnPanel.add(removeBtn,gbc);

               cancelBtn = new JButton("Cancel");
               cancelBtn.addActionListener(this);
               gbc.insets= new Insets(0,15,0,0);
               gbc.gridx = 1;
               gbc.gridy = 0;
               btnPanel.add(cancelBtn,gbc);
		
              	mainPanel.add(new JLabel(""));
		mainPanel.add(btnPanel);
                
                
                int rows = 3;

		SpringUtilities.makeCompactGrid(mainPanel,
                rows, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
       
                JScrollPane mainScrollPane = new JScrollPane(mainPanel);
                
                
                Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(mainScrollPane, "Center");
		setSize(790,190);
                setResizable(true);
               
               
                
                
    }
    
    
     
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == headerBox){
            if(headerBox.getSelectedIndex()==0){
                //fillNameBox("Class");
                type="Class";
            }
            else
                if(headerBox.getSelectedIndex()==1){
                    //fillNameBox("Object Property");
                    type="Object Property";
                }
              else
                    if(headerBox.getSelectedIndex()==2){
                        //fillNameBox("Data Property");
                        type="Data Property";   
                    }
                    else
                  
                         if(headerBox.getSelectedIndex()==3){
                         //fillNameBox("Individual");
                         type="Individual";
                        }
        
              fillNameBox(type);
        }
        
        
        if(e.getSource()== removeBtn){
            
            OWLEntity newEntity = (OWLEntity) nameBox.getSelectedItem(); 
            oomiOwlModel.remove(newEntity);
            //oomiOwlModel.
            this.dispose();
        }
    }
    

    private void fillNameBox(String type) {
        nameBox.removeActionListener(this);
        nameBox.removeAllItems();
        if(type.equals("Class")){
            
            Set classSet = new TreeSet();
            classSet.addAll(oomiOwlModel.getWorkingOntology().getClassesInSignature());
            
            Iterator  classIterator = classSet.iterator();
            
            while(classIterator.hasNext()){
                OWLClass cls = (OWLClass) classIterator.next();
                nameBox.addItem(cls);
            }
        }
        
        if(type.equals("Object Property")){
            
            Set objPropSet = new TreeSet();
            objPropSet.addAll(oomiOwlModel.getWorkingOntology().getObjectPropertiesInSignature());
            
            Iterator  objPropIterator = objPropSet.iterator();
            
            while(objPropIterator.hasNext()){
                OWLObjectProperty cls = (OWLObjectProperty) objPropIterator.next();
                nameBox.addItem(cls);
            }           
        }
         if(type.equals("Data Property")){
            
            Set dataPropSet = new TreeSet();
            dataPropSet.addAll(oomiOwlModel.getWorkingOntology().getDataPropertiesInSignature());
            
            Iterator  dataPropIterator = dataPropSet.iterator();
            
            while(dataPropIterator.hasNext()){
                OWLDataProperty cls = (OWLDataProperty) dataPropIterator.next();
                nameBox.addItem(cls);
            }           
        }
        
          if(type.equals("Individual")){
            
            Set objPropSet = new TreeSet();
            objPropSet.addAll(oomiOwlModel.getWorkingOntology().getIndividualsInSignature());
            
            Iterator  objPropIterator = objPropSet.iterator();
            
            while(objPropIterator.hasNext()){
                OWLIndividual cls = (OWLIndividual) objPropIterator.next();
                nameBox.addItem(cls);
            }           
        }
    }

    
    
    
    
    
}
