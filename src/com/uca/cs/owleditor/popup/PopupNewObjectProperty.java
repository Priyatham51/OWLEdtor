/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;

import com.uca.cs.owleditor.OwlEditorFrame;
import com.uca.cs.owleditor.utils.ui.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;



import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.semanticweb.owl.model.change.AddAnnotationInstance;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLClass;

import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLProperty;


import com.uca.cs.owleditor.OOMIOwlModel;
import java.awt.event.ItemEvent;
import org.semanticweb.owlapi.vocab.OWL2Datatype;


/**
 *
 * @author priyathamanisetty
 */
public class PopupNewObjectProperty extends JFrame  implements ActionListener, ItemListener {
    
    String type;
	JButton addBtn, cancelBtn;
	JTextField idFld, uriFld, labelFld;
	JTextArea commentArea;
	JComboBox headerBox = new JComboBox();
        JComboBox propType = new JComboBox();
        JComboBox parentBox = new JComboBox(); 
        JComboBox childBox = new JComboBox();
        JComboBox rangeBox = new JComboBox();
        JComboBox domainBox;
        JCheckBox subProp, superProp, domain, range;
        
        OWLOntology ontology;
	OWLEntity newEntity;
        OWLDataFactory factory;
        JPanel IDEHandler;
        OOMIOwlModel oomiOwlModel;
        OWLEntity lastSelectedParent;
        IRI documentIRI = null;
	//OWLEntity lastSelectedParent;
        
        public PopupNewObjectProperty(JPanel handler,OOMIOwlModel  model, String type, OWLOntology ontology) {
		
		
                factory = OWLManager.getOWLDataFactory();
                oomiOwlModel = model;
                documentIRI = ontology.getOntologyID().getOntologyIRI();
                
                this.IDEHandler = handler;
		//this.swoopModel = swoopModel;
		this.type = type;
		if (!type.equals("Ontology")) this.ontology = oomiOwlModel.getWorkingOntology();
		//this.lastSelectedParent = swoopModel.getSelectedEntity();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setModal(true);
		try {
			setupUI();			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
        
        
        private void setupUI() throws OWLException {
	     
            //**********************************************************
            //*********************************************************
            // Added here to update the propList and Class List every time this method is called 
               
                this.ontology = oomiOwlModel.getWorkingOntology();
            
            //**********************************************************
            //*********************************************************
            
                if (!type.equals("Ontology")) this.ontology = oomiOwlModel.getWorkingOntology();
		JPanel mainPanel = new JPanel(new SpringLayout());
		
		JLabel headerLbl = new JLabel();
		headerBox = new JComboBox();
		
                if (type.equals("Property")) {
			headerLbl.setText("Adding OWL Property");
			headerBox.addItem("Adding OWL Property");
//			headerBox.addItem("Adding OWL Class");
//			headerBox.addItem("Adding OWL Individual");
		}
		
                headerBox.addActionListener(this);
		
//		mainPanel.add(new JLabel(""));
//		mainPanel.add(headerBox);
		
                JLabel uriLbl = new JLabel("Logical URI:");
		uriFld = new JTextField();
		if (!type.equals("Ontology")) uriFld.setText("#");
		uriLbl.setLabelFor(uriFld);
		mainPanel.add(uriLbl);
		mainPanel.add(uriFld);
                
                
		JLabel propLbl = new JLabel("Property Type:");
		
		propType = new JComboBox();
		propType.addItem("OWL Datatype Property");
		propType.addItem("OWL Object Property");
		propType.addItem("OWL Annotation Property");
		propType.addActionListener(this);
		
                
		propLbl.setLabelFor(propType);
              
                mainPanel.add(propLbl);
                mainPanel.add(propType);
                propType.setSelectedIndex(0); // DEFAULT PROPERTY TYPE
		
		
		// add parent box
		parentBox = new JComboBox();
     
		JLabel parentLbl = new JLabel();
		parentLbl.setText("subProperty-of");
                 
                mainPanel.add(parentLbl);
	        mainPanel.add(parentBox);
                
		fillParentBox();
                
                
                /**************************************
                 * Super Property Filled
                 **************************************/
                childBox = new JComboBox();
                
                JLabel childLbl = new JLabel();
		childLbl.setText("superProperty-of");
                 
                mainPanel.add(childLbl);
	        mainPanel.add(childBox);
                
		fillChildBox();
                
                /**************************************
                 * Domain Property Filled
                 **************************************/
                
                // add Domain Box 
                domainBox = new JComboBox();
                
                JLabel domainLbl = new JLabel();
                domainLbl.setText("Has Domain");
                mainPanel.add(domainLbl);
                mainPanel.add(domainBox);
                
                fillDomainBox(); 
                
                /**************************************
                 * Range Property Filed
                 **************************************/
                
                //add RangeBox 
                rangeBox = new JComboBox();
                
                JLabel rangeLbl = new JLabel("Has Range");
                rangeLbl.setText("Has Range");
                mainPanel.add(rangeLbl);
                mainPanel.add(rangeBox);
                
                fillRangeBox(); 
                
//                        
//                JPanel subPropPanel = new JPanel();
//                subPropPanel.setLayout(new GridLayout(1,4));
//                subProp = new JCheckBox("Sub Property");
//                subPropPanel.add(subProp);
//                subProp.addItemListener(this);
//               
//                superProp = new JCheckBox("Sup Prop");
//                subPropPanel.add(superProp);
//                
//                domain = new JCheckBox("Has Domain");
//                subPropPanel.add(domain);
//                
//                range = new JCheckBox("Has Range");
//                subPropPanel.add(range);
//                
//                mainPanel.add(new JLabel(""));
//                mainPanel.add(subPropPanel);
//                //mainPanel.add(subPropPanel);             
//                
//           	
//		
//		JLabel lbl = new JLabel("Label:");
//		labelFld = new JTextField("");
//		lbl.setLabelFor(labelFld);
//		mainPanel.add(lbl);
//		mainPanel.add(labelFld);
		
		JLabel comm = new JLabel("Comment:");
		commentArea = new JTextArea();
		commentArea.setCaretPosition(0);
                
		JScrollPane commentPane = new JScrollPane(commentArea);
		mainPanel.add(comm);
		comm.setLabelFor(commentPane);
		mainPanel.add(commentPane);		
		
		addBtn = new JButton("Add");
		addBtn.addActionListener(this);
                
                
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		
                
                JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(1,2));
		btnPanel.add(addBtn);
		btnPanel.add(cancelBtn);
		mainPanel.add(new JLabel(""));
		mainPanel.add(btnPanel);
		
                
                
		int rows = 6;
		if (type.equals("Class") || type.equals("Individual")) rows = 7;
		if (type.equals("Property")) rows = 8;
		
		SpringUtilities.makeCompactGrid(mainPanel,
                rows, 2, //rows, cols
                8, 8,        //initX, initY
                8, 8);       //xPad, yPad
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(mainPanel, "Center");
		setSize(790,390);
                setResizable(true);
                setTitle("New Property");
                headerBox.setVisible(true);			  
		
	}
        
    
        
        public void fillParentBox() {
		
		try {
			
			parentBox.removeActionListener(this);
                        parentBox.removeAllItems();			
			// fill values in parentBox
			 if (type.equals("Property")) {
				parentBox.addItem("None");
				Set propSet = new TreeSet();
				propSet.addAll(ontology.getDataPropertiesInSignature());
				propSet.addAll(ontology.getObjectPropertiesInSignature());
				Iterator propSetIter = propSet.iterator();
                                while (propSetIter.hasNext()) {
					OWLProperty prop = (OWLProperty) propSetIter.next();
					parentBox.addItem(prop);
				}							
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
        			
  }
        
        
       public void fillChildBox() {
		
		try {
			
			childBox.removeActionListener(this);
                        childBox.removeAllItems();			
			// fill values in childBox
			 if (type.equals("Property")) {
				childBox.addItem("None");
				Set propSet = new TreeSet();
				propSet.addAll(ontology.getDataPropertiesInSignature());
				propSet.addAll(ontology.getObjectPropertiesInSignature());
				Iterator propSetIter = propSet.iterator();
                                while (propSetIter.hasNext()) {
					OWLProperty prop = (OWLProperty) propSetIter.next();
					childBox.addItem(prop);
				}							
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
        			
  }
       
         public void fillDomainBox(){
        
		try {
			
			domainBox.removeActionListener(this);
                        domainBox.removeAllItems();			
			// fill values in domainBox
		        domainBox.addItem("None"); 
                        
//                        OWLClass thing = factory.getOWLThing();
//                        domainBox.addItem(thing);
              
                        Set domainSet = new TreeSet();

                        domainSet.addAll(ontology.getClassesInSignature());
                        Iterator claSetIter = domainSet.iterator();
                        while (claSetIter.hasNext()) {
                                OWLClass cla = (OWLClass) claSetIter.next();
                                //if (!cla.isOWLThing())
                                domainBox.addItem(cla);
                        }					
		     }
		catch (Exception e) {
			e.printStackTrace();
		}
        
    }
       public void fillRangeBox() {
		
		try {
			
			rangeBox.removeActionListener(this);
                        rangeBox.removeAllItems();			
			// fill values in parentBox
			 if (type.equals("Property")) {
				rangeBox.addItem("None");
				Set propSet = new TreeSet();
				propSet.addAll(ontology.getClassesInSignature());
				Iterator propSetIter = propSet.iterator();
                                while (propSetIter.hasNext()) {
					OWLClass cls = (OWLClass) propSetIter.next();
					rangeBox.addItem(cls);
				}							
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
        			
  }
        
  
	

    public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==parentBox && e.getActionCommand().equals("comboBoxChanged")) {
			
			if (parentBox.getSelectedItem() instanceof OWLDataProperty) {
				propType.setSelectedIndex(0);				
			}
			else if (parentBox.getSelectedItem() instanceof OWLObjectProperty) {
				//******************************************
					propType.setSelectedIndex(1);
				//************************************************				
			}			
		}
		
		if (e.getSource()==propType && e.getActionCommand().equals("comboBoxChanged")) {
			if (propType.getSelectedIndex()==2) {
				// annotation property selected, disable parentBox
				if (parentBox!=null) parentBox.setEnabled(false);
			}
			else if (parentBox!=null) parentBox.setEnabled(true);
                        
                        if(propType.getSelectedIndex()==1){
                           
                            //Setting the Range Box
                            rangeBox.removeActionListener(this);
                            rangeBox.removeAllItems();                    
                            rangeBox.addItem("None");
				Set propSet = new TreeSet();
				propSet.addAll(ontology.getClassesInSignature());
				Iterator propSetIter = propSet.iterator();
                                while (propSetIter.hasNext()) {
					OWLClass cls = (OWLClass) propSetIter.next();
					rangeBox.addItem(cls);
				}
                           
                            
                        }
                        
                        if(propType.getSelectedIndex()==0){
                            
                            
                            
                            //Setting the Sub property box (parentBox);
                            parentBox.removeActionListener(this);
                            parentBox.removeAllItems();
                            
                            parentBox.addItem("None");
                            
                            Set propSet = new TreeSet();
				//propSet.addAll(ontology.getObjectPropertiesInSignature());
                                propSet.addAll(ontology.getDataPropertiesInSignature());
				Iterator propSetIter = propSet.iterator();
                                while (propSetIter.hasNext()) {
					OWLDataProperty cls = (OWLDataProperty) propSetIter.next();
					parentBox.addItem(cls);
                                }
                            //setting the Super-Property box (ChildBox);
                            
                            childBox.removeActionListener(this);
                            childBox.removeAllItems();
                            
                            childBox.addItem("None");
                            Set propSet1 = new TreeSet();
				//propSet1.addAll(ontology.getObjectPropertiesInSignature());
                                propSet1.addAll(ontology.getDataPropertiesInSignature());
				Iterator propSetIter1 = propSet.iterator();
                                while (propSetIter1.hasNext()) {
					OWLDataProperty cls1 = (OWLDataProperty) propSetIter1.next();
					childBox.addItem(cls1);
                                }
                            
                            
                            //Setting the Range-Box
                            rangeBox.removeActionListener(this);
                            rangeBox.removeAllItems();
                            rangeBox.addItem("None");
                            // fill values in parentBox
                              OWL2Datatype[] rangeSet = OWL2Datatype.values();
                                    rangeBox.addItem("None");
                                    for(int i=0; i<rangeSet.length; i++){

                                        rangeBox.addItem(rangeSet[i]);
                                    }


                            }
                  
            }
		
		if (e.getSource()==headerBox && e.getActionCommand().equals("comboBoxChanged")) {
			// if header box selection is not same as current type
			if ((!type.equals("Ontology")) && (headerBox.getSelectedItem().toString().indexOf(type)==-1)) {
				// switch type 
				String newType = headerBox.getSelectedItem().toString();
				if (newType.indexOf("Class")>=0) {
					type = "Class"; 
					//((TermsDisplay) SwoopHandler).termTabPane.setSelectedIndex(0);
				}
				else if (newType.indexOf("Property")>=0){
					type = "Property";
					//((TermsDisplay) SwoopHandler).termTabPane.setSelectedIndex(1);
				}
				else if (newType.indexOf("Individual")>=0){
					type = "Individual";
					//((TermsDisplay) SwoopHandler).termTabPane.setSelectedIndex(2);
				}
				
				//this.redrawUI();
			}
		}
		
		if (e.getSource()==addBtn) {
			try {
				
				String uriStr = uriFld.getText();
				
				// check for invalid uri
				if (uriStr.trim().equals("") || !(isURI(uriStr))) {
					JOptionPane.showMessageDialog(null, "A Valid Logical URI needs to be specified for the new OWL Ontology", "Creation Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
				URI uri = new URI(uriStr);
                                IRI iri = IRI.create(documentIRI+uriStr);
                                
                                 if (type.equals("Property")) {


                                        if (propType.getSelectedIndex()==0) {
                                                // new datatype property
                                                newEntity = factory.getOWLDataProperty(iri);
                                        }
                                        else if (propType.getSelectedIndex()==2) {
                                                // new annotation property
                                                newEntity = factory.getOWLAnnotationProperty(iri);
                                        }
                                        else {
                                                // new object property
                                                newEntity = factory.getOWLObjectProperty(iri);
                                             }				

                                         
					// add rdfs:label and comment if any
					//addAnnotations(factory);
					
				}
				
                                createEntity();
                                
				//labelFld.setText("");
				commentArea.setText("");
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
		if (e.getSource()==cancelBtn) {
			dispose();
		}
                      
		
	}
    
    private boolean isURI(String str) {		
		try {
			URI url = new URI(str);
			return true;
		}
		catch (Exception ex) {}
		return false;
	}

private void createEntity() {
		if (newEntity!=null) {
			OWLEntity parentOf = null;
                        OWLEntity childOf = null;
                        OWLEntity hasDomain = null;                       
                        OWLEntity hasRange = null;
                        //OWL2Datatype hasRangeDatatype = null;
                        
                        
			if (parentBox.getSelectedIndex()!=0) parentOf = (OWLEntity) parentBox.getSelectedItem();
			if (childBox.getSelectedIndex()!=0) childOf = (OWLEntity)   childBox.getSelectedItem();
                        if (domainBox.getSelectedIndex()!=0) hasDomain = (OWLEntity) domainBox.getSelectedItem();
                        
                        //if (parentBox.getSelectedIndex()!=0) parentClass = (OWLEntity) parentBox.getSelectedItem();
                        
                        
                        
          
                        this.lastSelectedParent = parentOf;
                        //if Data Type
                        if(propType.getSelectedIndex()==0){
                            OWL2Datatype hasRangeDataType = null;
                            if (rangeBox.getSelectedIndex()!=0) hasRangeDataType = (OWL2Datatype)rangeBox.getSelectedItem();
                              oomiOwlModel.addDatatypePropertyEntity(newEntity,parentOf,childOf, hasDomain, hasRangeDataType);
                            
                            
                        }
                        else//Object Type
                            if(propType.getSelectedIndex()==1){
                               if (rangeBox.getSelectedIndex()!=0) hasRange = (OWLEntity) rangeBox.getSelectedItem(); 
                                oomiOwlModel.addObjectPropertyEntity(newEntity,parentOf,childOf, hasDomain, hasRange,false);

                            }
                            else //Individual Type
                                if(propType.getSelectedIndex()==2){

                                }
			
                        redrawUI();
		}
	}

	private void addAnnotations(OWLDataFactory df) {
		
		try {
			String lbl = "", comment = "", version = "";
			if (labelFld.getText()!=null) lbl = labelFld.getText().trim();
			if (commentArea.getText()!=null) comment = commentArea.getText().trim();
			if (idFld.getText()!=null) version = idFld.getText().trim();
			
			// get annotation properties
			URI lblURI = new URI("http://www.w3.org/2000/01/rdf-schema#label");
                        IRI iriii = IRI.create(lblURI);
			OWLAnnotationProperty lblProp = df.getOWLAnnotationProperty(iriii);
                        
			URI commentURI = new URI("http://www.w3.org/2000/01/rdf-schema#comment");
                        iriii = IRI.create(commentURI);
                        
			OWLAnnotationProperty commentProp = df.getOWLAnnotationProperty(iriii);
                        
			URI versionInfoURI = new URI("http://www.w3.org/2002/07/owl#versionInfo");
                        iriii = IRI.create(versionInfoURI);
                        
			OWLAnnotationProperty versionInfoProp = df.getOWLAnnotationProperty(iriii);			
			
			
				AddOntologyAnnotation annot;
				
				// add label
				if(lbl.length() > 0) {				
//					annot = new AddOntologyAnnotation(ontology,)
//					annot.accept((ChangeVisitor) ontology);
				}
				
				// add comment
				if(comment.length() > 0) {				
//					annot = new AddAnnotationInstance(ontology, newEntity, commentProp, comment, null);
//					annot.accept((ChangeVisitor) ontology);
				}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
	}
        
        
        private void redrawUI() {
		
		getContentPane().removeAll();
		getContentPane().repaint();			
		try {
			setupUI();
			//show();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    @Override
    public void itemStateChanged(ItemEvent ie) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

 
}
