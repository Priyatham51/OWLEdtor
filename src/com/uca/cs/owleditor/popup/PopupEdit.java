/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;

import com.uca.cs.owleditor.OOMIOwlModel;
import com.uca.cs.owleditor.utils.ui.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 *
 * @author priyathamanisetty
 */
public class PopupEdit extends JFrame implements ActionListener{

    
    JButton saveBtn, cancelBtn;
    JTextField idFld, uriFld, labelFld;
    
    JComboBox headerBox;
    JComboBox nameBox = new JComboBox();
    JComboBox propType = new JComboBox();
    JComboBox parentBox = new JComboBox(); 
    JComboBox childBox = new JComboBox();
    JComboBox rangeBox = new JComboBox();
    JComboBox domainBox;
    
    
    OWLOntology ontology;
    OWLEntity newEntity;
    OWLDataFactory factory;
    JPanel IDEHandler;
    OOMIOwlModel oomiOwlModel;
    OWLEntity lastSelectedParent;
    IRI documentIRI = null;

    
    
    
    public PopupEdit(JPanel IDEHandler, OOMIOwlModel oomiOwlModel, OWLOntology onto) {
        this.IDEHandler = IDEHandler;
        this.oomiOwlModel = oomiOwlModel;
        this.ontology = onto;
        factory = OWLManager.getOWLDataFactory();
        
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
                headerBox.addItem("OWL Property");
                headerBox.addItem("OWL Individual");
                headerBox.addActionListener(this);
		mainPanel.add(new JLabel(""));
                mainPanel.add(headerBox);
                
              //  headerBox.setSelectedIndex(1);
                
                JLabel propLbl = new JLabel("Select Type:");
		
		propType = new JComboBox();
		propType.addItem("OWL Datatype Property");
		propType.addItem("OWL Object Property");
		propType.addItem("OWL Annotation Property");
		propType.addActionListener(this);
                propType.setEnabled(false);
		propLbl.setLabelFor(propType);
              
                mainPanel.add(propLbl);
                mainPanel.add(propType);
                
                
                nameBox.addActionListener(this);
                fillNameBoxAsClass();
                mainPanel.add(new JLabel("Name :"));
                mainPanel.add(nameBox);
               
                
                
                JLabel uriLbl = new JLabel("Logical URI:");
		uriFld = new JTextField();
		//uriFld.setText("#");
                uriFld.setEditable(false);
		uriLbl.setLabelFor(uriFld);
		mainPanel.add(uriLbl);
		mainPanel.add(uriFld);
                
                
                
                
                
                // add parent box
		parentBox = new JComboBox();
     
		JLabel parentLbl = new JLabel();
		parentLbl.setText("subProperty-of");
                 
                mainPanel.add(parentLbl);
	        mainPanel.add(parentBox);
                
                /**************************************
                 * Super Property Filled
                 **************************************/
                childBox = new JComboBox();
                
                JLabel childLbl = new JLabel();
		childLbl.setText("superProperty-of");
                 
                mainPanel.add(childLbl);
	        mainPanel.add(childBox);
                
		//fillChildBox();
                
                /**************************************
                 * Domain Property Filled
                 **************************************/
                
                // add Domain Box 
                domainBox = new JComboBox();
                domainBox.setEnabled(false);
                JLabel domainLbl = new JLabel();
                domainLbl.setText("Has Domain");
                mainPanel.add(domainLbl);
                mainPanel.add(domainBox);
                
                
                //fillDomainBox(); 
                
                /**************************************
                 * Range Property Filed
                 **************************************/
                
                //add RangeBox 
                rangeBox = new JComboBox();
                rangeBox.setEnabled(false);
                JLabel rangeLbl = new JLabel("Has Range");
                rangeLbl.setText("Has Range");
                mainPanel.add(rangeLbl);
                mainPanel.add(rangeBox);
                
                //fillRangeBox(); 
                
                saveBtn = new JButton("Save");
                saveBtn.addActionListener(this);
                
                
                cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
                
                
                
                JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1,2));
		btnPanel.add(saveBtn);
		btnPanel.add(cancelBtn);
		mainPanel.add(new JLabel(""));
		mainPanel.add(btnPanel);
                
                int rows = 9;
//		if (type.equals("Class") || type.equals("Individual")) rows = 7;
//		if (type.equals("Property")) rows = 8;
		
		SpringUtilities.makeCompactGrid(mainPanel,
                rows, 2, //rows, cols
                8, 8,        //initX, initY
                8, 8);       //xPad, yPad
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(mainPanel, "Center");
		setSize(790,390);
                setResizable(true);
                setTitle("Ontology Editor");
                headerBox.setVisible(true);
                
                
                
                
                
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        
        if (e.getSource()==headerBox && e.getActionCommand().equals("comboBoxChanged")) {
			
	    if(headerBox.getSelectedIndex()==0){
                //fillNameBoxAsClass();
                propType.setEnabled(false);
                nameBox.removeActionListener(this);
                nameBox.removeAllItems();
                
                uriFld.setText("");
                
                parentBox.removeActionListener(this);
                parentBox.removeAllItems();
                
                childBox.removeActionListener(this);
                childBox.removeAllItems();
                
                
                Set propSet = new TreeSet();
                propSet.addAll(ontology.getClassesInSignature());
                Iterator propSetIter = propSet.iterator();
                while (propSetIter.hasNext()) {
                        OWLClass cls = (OWLClass) propSetIter.next();
                        nameBox.addItem(cls);
                }
                nameBox.addActionListener(this);
            }
            if(headerBox.getSelectedIndex()==1){
                propType.setEnabled(true);
                domainBox.setEnabled(true);
                rangeBox.setEnabled(true);
                fillNameBoxAsProp();
                uriFld.setText("");
                
                parentBox.removeActionListener(this);
                parentBox.removeAllItems();
            }
            
        }
        
        if (e.getSource()==nameBox && e.getActionCommand().equals("comboBoxChanged")) {
			
	    //fillNameBoxAsClass();
            if(headerBox.getSelectedIndex()==0){
                
                
                OWLClass selectedClass = (OWLClass) nameBox.getSelectedItem();
                System.out.println(selectedClass.toString());

                fillClassProperties(selectedClass);
            }
            
            if(headerBox.getSelectedIndex()==1){
               if(propType.getSelectedIndex()==1){
                    
                    OWLObjectProperty objProp = (OWLObjectProperty) nameBox.getSelectedItem();
                    System.out.println(objProp.toString());

                    fillObjectProperties(objProp);
                }
            }
        }
        
        if(e.getSource()==propType && e.getActionCommand().equals("comboBoxChanged")){
            nameBox.removeActionListener(this);
            nameBox.removeAllItems();
            
            //Datatype Property
            if(propType.getSelectedIndex()==0){
                
                Set dataPropertySet = new TreeSet();
                dataPropertySet.addAll(ontology.getDataPropertiesInSignature());
                Iterator propSetIter = dataPropertySet.iterator();
                while (propSetIter.hasNext()) {
                        OWLProperty prop = (OWLProperty)propSetIter.next();
                        nameBox.addItem(prop);
                }
                
                
                
            }
            if(propType.getSelectedIndex()==1){
                
                Set dataPropertySet = new TreeSet();
                dataPropertySet.addAll(ontology.getObjectPropertiesInSignature());
                Iterator propSetIter = dataPropertySet.iterator();
                while (propSetIter.hasNext()) {
                        OWLProperty prop = (OWLProperty)propSetIter.next();
                        nameBox.addItem(prop);
                }
            }
            
            
           
        }
        
        if(e.getSource()== saveBtn){
            
            if(headerBox.getSelectedIndex()==0)
                newEntity=(OWLEntity)nameBox.getSelectedItem();
//            else
//                if(headerBox.getSelectedIndex()==1){
//                    if(propType.getSelectedIndex()==1)//Object Property
//                        newEntity = (OWLEntity)nameBox.getSelectedItem();
//                }
            
            createEntity();
           
        }
        
        if(e.getSource() == cancelBtn)
        {
            dispose();
        }
        
        
    }
    
    public void fillNameBoxAsClass(){
        nameBox.removeActionListener(this);
        nameBox.removeAllItems();
        
        Set propSet = new TreeSet();
        propSet.addAll(ontology.getClassesInSignature());
        Iterator propSetIter = propSet.iterator();
        while (propSetIter.hasNext()) {
                OWLClass cls = (OWLClass) propSetIter.next();
                nameBox.addItem(cls);
        }
        
        nameBox.addActionListener(this);
        
        
    }
    
    
   private void fillNameBoxAsProp() {
       
        nameBox.removeActionListener(this);
        nameBox.removeAllItems();
        
        Set propSet = new TreeSet();
        propSet.addAll(ontology.getObjectPropertiesInSignature());
        Iterator propSetIter = propSet.iterator();
        while (propSetIter.hasNext()) {
                OWLObjectProperty objProp = (OWLObjectProperty) propSetIter.next();
                nameBox.addItem(objProp);
        }
        
        nameBox.addActionListener(this);
        
        
    }
    
    
    public void fillClassProperties(OWLClass selectedClass){
        
        
        IRI classIri = selectedClass.getIRI();
        uriFld.setText(classIri.toString());
        
        fillParentBox(selectedClass);
        fillChildBox(selectedClass);
        //fillDomainBox(selectedClass); no need of the domain Box and RangeBox 
    }
    
    
    private void fillObjectProperties(OWLObjectProperty objProp) {
        IRI objPropIri = objProp.getIRI();
        uriFld.setText(objPropIri.toString());
        
        fillParentBox(objProp);
        
    }
    
    
    
    
     public void fillParentBox(OWLClass selectedCls) {
		
		try {
			
			parentBox.removeActionListener(this);
                        parentBox.removeAllItems();			
			// fill values in parentBox
			
                        OWLClass thing = factory.getOWLThing();
                        parentBox.addItem(thing);
                        //Set claSet = new TreeSet(EntityComparator.INSTANCE);
                        Set claSet = new TreeSet();

                        claSet.addAll(ontology.getClassesInSignature());
                        Iterator claSetIter = claSet.iterator();
                        while (claSetIter.hasNext()) {
                                OWLClass cla = (OWLClass) claSetIter.next();
                                if (!cla.isOWLThing())
                                                parentBox.addItem(cla);
                        }
                        
                        parentBox.addActionListener(this);
                        Set supClaSet = new TreeSet();
                        supClaSet = selectedCls.getSuperClasses(ontology);
                        Iterator supClaSetIter = supClaSet.iterator();
                        if(supClaSetIter.hasNext()){
                            parentBox.setSelectedItem((OWLClass)supClaSetIter.next());
                        }
                        else {
                            parentBox.setSelectedIndex(0);
                        }       
                        
                        //System.out.println("From Class :|");
                        
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
        			
    }
        public void fillParentBox(OWLObjectProperty selectedObjProp) {

                        try {

                                parentBox.removeActionListener(this);
                                parentBox.removeAllItems();			
                                // fill values in parentBox


                                parentBox.addItem("None");
                                //Set claSet = new TreeSet(EntityComparator.INSTANCE);
                                Set claSet = new TreeSet();

                                claSet.addAll(ontology.getObjectPropertiesInSignature());
                                Iterator claSetIter = claSet.iterator();
                                while (claSetIter.hasNext()) {
                                        OWLObjectProperty objProp = (OWLObjectProperty) claSetIter.next();
                                        parentBox.addItem(objProp);
                                }

                                parentBox.addActionListener(this);
                                Set supPropSet = new TreeSet();
                                supPropSet = selectedObjProp.getSuperProperties(ontology);
                                Iterator supObjPropSetIter = supPropSet.iterator();
                                if(supObjPropSetIter.hasNext())
                                parentBox.setSelectedItem((OWLObjectProperty)supObjPropSetIter.next());
                                else
                                parentBox.setSelectedIndex(0);

                                //System.out.println("From obj prop");


                        }
                        catch (Exception e) {
                                e.printStackTrace();
                        }

            }



        public void fillChildBox(OWLClass selectedCls){
            
            try {
			
			childBox.removeActionListener(this);
                        childBox.removeAllItems();			
			// fill values in parentBox
			
                        OWLClass nothing = factory.getOWLNothing();
                        childBox.addItem(nothing);
                        //Set claSet = new TreeSet(EntityComparator.INSTANCE);
                        Set claSet = new TreeSet();

                        claSet.addAll(ontology.getClassesInSignature());
                        Iterator claSetIter = claSet.iterator();
                        while (claSetIter.hasNext()) {
                                OWLClass cla = (OWLClass) claSetIter.next();
                                if (!cla.isOWLThing())
                                                childBox.addItem(cla);
                        }
                        
                        //childBox.addActionListener(this);
                        Set supClaSet = new TreeSet();
                        supClaSet = selectedCls.getSubClasses(ontology);
                        Iterator supClaSetIter = supClaSet.iterator();
                        if(supClaSetIter.hasNext())
                        childBox.setSelectedItem((OWLClass)supClaSetIter.next());
			else
                        childBox.setSelectedIndex(0);
                        //System.out.println("From Class :|");
                        
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
            
            

        }
    
     public void fillDomainBox(OWLClass selectedCls){
            
            try {
			
			domainBox.removeActionListener(this);
                        domainBox.removeAllItems();			
			// fill values in parentBox
			
                        
                        domainBox.addItem("None");
                        //Set claSet = new TreeSet(EntityComparator.INSTANCE);
                        Set claSet = new TreeSet();

                        claSet.addAll(ontology.getClassesInSignature());
                        Iterator claSetIter = claSet.iterator();
                        while (claSetIter.hasNext()) {
                                OWLClass cla = (OWLClass) claSetIter.next();
                                if (!cla.isOWLThing())
                                                domainBox.addItem(cla);
                        }
                        
                        //childBox.addActionListener(this);
                        Set supClaSet = new TreeSet();
                        supClaSet = selectedCls.getReferencingAxioms(ontology);
                        Iterator supClaSetIter = supClaSet.iterator();
                        while(supClaSetIter.hasNext()){
                            
                            OWLAxiom testAxiom = (OWLAxiom) supClaSetIter.next();
                            
                            if(testAxiom instanceof OWLDataPropertyDomainAxiom){
                                domainBox.setSelectedItem((OWLClass)testAxiom);
                            }
                           
                            
                            System.out.println(testAxiom.getAxiomType().getName());
                          
                            
                        }
                        
		}
		catch (Exception e) {
			e.printStackTrace();
		}
      
     
     }
     
    
     
     
     private void createEntity() {
        
        
        OWLEntity parentOf = null;
        OWLEntity childOf = null;
        OWLEntity hasDomain = null;                       
        OWLEntity hasRange = null;
        
        
         
             
             if (newEntity!=null) {
                 
                 
                 if(headerBox.getSelectedIndex()==0){

                        if(childBox.getSelectedIndex()!=0) childOf = (OWLEntity) childBox.getSelectedItem();
			if (parentBox.getSelectedIndex()!=0) parentOf = (OWLEntity) parentBox.getSelectedItem();
			//this.lastSelectedParent = parent;
			oomiOwlModel.addClass(newEntity, parentOf, childOf);
                        //redrawUI();
		
                 }
         
                 else
                     if(headerBox.getSelectedIndex()==1){

                         if(propType.getSelectedIndex()==0){
                                    OWL2Datatype hasRangeDataType = null;
                                    if (rangeBox.getSelectedIndex()!=0) hasRangeDataType = (OWL2Datatype)rangeBox.getSelectedItem();
                                      oomiOwlModel.addDatatypePropertyEntity(newEntity,parentOf,childOf, hasDomain, hasRangeDataType);
                          }
                         else
                            //Object Property 
                           if(propType.getSelectedIndex()==1){
                                 if (rangeBox.getSelectedIndex()!=0) hasRange = (OWLEntity) rangeBox.getSelectedItem(); 
                                            oomiOwlModel.addObjectPropertyEntity(newEntity,parentOf,childOf, hasDomain, hasRange,true);
                            }
                         else 
                               //For Individual Type
                               if(propType.getSelectedIndex()==2){
                                  
                               }


                     }
         
         }
         
         
         
		
	}
            
     private void redrawUI() {
		
		getContentPane().removeAll();
		getContentPane().repaint();			
		try {
			setupUI();
			show();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    
    
    
    
    
    
    
    
    
}
