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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;



import javax.swing.JButton;
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
//import org.semanticweb.owl.model.change.AddAnnotationInstance;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLClass;

import org.semanticweb.owlapi.model.AddOntologyAnnotation;
import org.semanticweb.owlapi.model.OWLProperty;


import com.uca.cs.owleditor.OOMIOwlModel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;


/**
 *
 * @author priyathamanisetty
 */
public class PopupNew extends JFrame  implements ActionListener,  KeyListener{
    
    String type;
	JButton addBtn, cancelBtn;
	JTextField idFld, uriFld, labelFld;
	JTextArea commentArea;
	JComboBox headerBox, propType, parentBox;
        
        OWLOntology ontology;
	OWLEntity newEntity;
        OWLDataFactory factory;
        JPanel IDEHandler;
        OOMIOwlModel oomiOwlModel;
        OWLEntity lastSelectedParent;
        IRI documentIRI = null;
	//OWLEntity lastSelectedParent;
        
        public PopupNew(JPanel handler,OOMIOwlModel  model, String type, OWLOntology ontology) {
		
		
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
		
		JPanel mainPanel = new JPanel(new SpringLayout());
		
		JLabel headerLbl = new JLabel();
		headerBox = new JComboBox();
		
		
		if (type.equals("Ontology")) {
			headerLbl.setText("Adding OWL Ontology");
			headerBox.addItem("Adding OWL Ontology");
		}
		else if (type.equals("Class")) {
			headerLbl.setText("Adding OWL Class");
			headerBox.addItem("Adding OWL Class");
			headerBox.addItem("Adding OWL Property");
			headerBox.addItem("Adding OWL Individual");
		}
		else if (type.equals("Property")) {
			headerLbl.setText("Adding OWL Property");
			headerBox.addItem("Adding OWL Property");
			headerBox.addItem("Adding OWL Class");
			headerBox.addItem("Adding OWL Individual");
		}
		else if (type.equals("Individual")) {
			headerLbl.setText("Adding OWL Individual");			
			headerBox.addItem("Adding OWL Individual");
			headerBox.addItem("Adding OWL Class");
			headerBox.addItem("Adding OWL Property");
		}
		
                headerBox.addActionListener(this);
		
//		mainPanel.add(new JLabel(""));
//		mainPanel.add(headerBox);
		
//		JLabel propLbl = new JLabel("Property Type:");
//		
//		propType = new JComboBox();
//		propType.addItem("OWL Datatype Property");
//		propType.addItem("OWL Object Property");
//		propType.addItem("OWL Annotation Property");
//		
//		//*****************************************
//		//Added for Econn
//		//*****************************************
//		propType.addItem("OWL Link Property");
//				
//		//****************************************
//		propType.addActionListener(this);
//		
//		propLbl.setLabelFor(propType);
//		if (type.equals("Property")) {
//			mainPanel.add(propLbl);
//			mainPanel.add(propType);
//			propType.setSelectedIndex(1); // DEFAULT PROPERTY TYPE
//		}		
		
		
              JLabel uriLbl = new JLabel("Logical URI:");
		uriFld = new JTextField();
		//uriFld.addKeyListener(this);
		if (!type.equals("Ontology")) uriFld.setText("#");
		else uriFld.setText("");
		uriLbl.setLabelFor(uriFld);
		mainPanel.add(uriLbl);
		mainPanel.add(uriFld);
		
              // add parent box  
		parentBox = new JComboBox();
		JLabel parentLbl = new JLabel();
		
		if (type.equals("Class")) parentLbl.setText("subClass-of");
		else if (type.equals("Property")) parentLbl.setText("subProperty-of");
		else parentLbl.setText("Instance-of");
		if (!type.equals("Ontology")) {
			mainPanel.add(parentLbl);
			mainPanel.add(parentBox);
		}
		fillParentBox();
		
//		JLabel idLbl = new JLabel("ID:");
//		if (type.equals("Ontology")) idLbl.setText("Version-info:");
//		
//		idFld = new JTextField();
//		//idFld.getDocument().addDocumentListener(this);
//		//idFld.addKeyListener(this);
//		idLbl.setLabelFor(idFld);
//		mainPanel.add(idLbl);
//		mainPanel.add(idFld);
		
		
		JLabel lbl = new JLabel("Label:");
		labelFld = new JTextField("");
		//labelFld.addKeyListener(this);
		lbl.setLabelFor(labelFld);
		mainPanel.add(lbl);
		mainPanel.add(labelFld);
		
		JLabel comm = new JLabel("Comment:");
		commentArea = new JTextArea();
		commentArea.setCaretPosition(0);
		commentArea.addKeyListener(this);
		JScrollPane commentPane = new JScrollPane(commentArea);
		mainPanel.add(comm);
		comm.setLabelFor(commentPane);
		mainPanel.add(commentPane);		
		
		
                
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridBagLayout());
              GridBagConstraints gbc = new GridBagConstraints();
       
              addBtn = new JButton("Add");
		addBtn.addActionListener(this);
               gbc.gridx =0;
               gbc.gridy =0;
               gbc.ipadx =200;
               btnPanel.add(addBtn,gbc);

               cancelBtn = new JButton("Cancel");
               cancelBtn.addActionListener(this);
               gbc.insets= new Insets(0,15,0,0);
               gbc.gridx = 1;
               gbc.gridy = 0;
               btnPanel.add(cancelBtn,gbc);
		
               
//               btnPanel.add(addBtn);
//		btnPanel.add(cancelBtn);
		mainPanel.add(new JLabel(""));
		mainPanel.add(btnPanel);
		
		int rows = 5;
//		if (type.equals("Class") || type.equals("Individual")) rows = 7;
//		if (type.equals("Property")) rows = 8;
		
		SpringUtilities.makeCompactGrid(mainPanel,
                rows, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
		
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(mainPanel, "Center");
		
		if (type.equals("Ontology")) {
			setSize(350, 230);
			setResizable(false);
			setTitle("New OWL Ontology");
			headerBox.setVisible(false);
		}
		else {
			if (type.equals("Class")) {
				setSize(650, 300);
			}
			else if (type.equals("Property")){
				setSize(650, 300);
			}
			else if (type.equals("Individual")){
				setSize(650, 300);
			}
			setResizable(true);
			setTitle("New Entity");
			headerBox.setVisible(true);			
		}
                
                
		
	}
        
    
        
        public void fillParentBox() {
		
		try {
			
			parentBox.removeActionListener(this);
                        parentBox.removeAllItems();			
			// fill values in parentBox
			if (type.equals("Class") || (type.equals("Individual"))) {
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
			}
                        
			else if (type.equals("Property")) {
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
				OWLDataFactory df = null;
				
				if (type.equals("Ontology")) {
										
				}					
				else {
					if (type.equals("Class")) {
                                                IRI iriClass = IRI.create(documentIRI+uriStr);
						newEntity = factory.getOWLClass(iriClass);
                                                       
					}
					else if (type.equals("Property")) {
                                            
						//df = ontology.getOWLDataFactory();
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
							//**************************************
							//Added for Econnections
							//***************************************
							if (propType.getSelectedIndex()==2) {
//								PopupAddForeignOntology popup = new PopupAddForeignOntology(swoopModel);
//								popup.setLocation(200, 200);
//								popup.show();
//								
//								((OWLObjectProperty)newEntity).setLinkTarget(popup.ontologyURI);
//								if (!(swoopModel.getOntologiesMap().containsKey(popup.ontologyURI)))
//								   swoopModel.addOntology(popup.ontologyURI);
							}
							//*************************************
							
						}				
					}
					else if (type.equals("Individual")) {
						// new individual
//						df = ontology.getOWLDataFactory();
//						newEntity = df.getOWLIndividual(uri);					
					}
					
                                        createEntity();
					// add rdfs:label and comment if any
                                       addAnnotations(factory);
					
					
					
				}

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
			OWLEntity parent = null;
			if (parentBox.getSelectedIndex()!=0) parent = (OWLEntity) parentBox.getSelectedItem();
			this.lastSelectedParent = parent;
			oomiOwlModel.addEntity(newEntity, parent);
                        
		}
	}

	private void addAnnotations(OWLDataFactory df) {
		
		try {
			String lbl = "", comment = "", version = "";
			if (labelFld.getText()!=null) lbl = labelFld.getText().trim();
			if (commentArea.getText()!=null) {
                            comment = commentArea.getText().trim();
                        }
			//if (idFld.getText()!=null) version = idFld.getText().trim();
			
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
					OWLAnnotation lblAnno = df.getOWLAnnotation(df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI()), 
                                                                    df.getOWLLiteral(lbl, "en"));
                                        OWLClass cls= (OWLClass)newEntity;
                                        
                                        OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(cls.getIRI(),lblAnno);
                                        
                                        oomiOwlModel.getManager().applyChange(new AddAxiom(ontology,ax));
                                        oomiOwlModel.getManager().saveOntology(ontology);
                                        oomiOwlModel.getIDEPanelHandler().updateTextArea();
				}
				
				// add comment
				if(comment.length() > 0) {				
					OWLAnnotation commentAnno = df.getOWLAnnotation(df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_COMMENT.getIRI()), 
                                                                    df.getOWLLiteral(comment, "en"));
                                        OWLClass cls= (OWLClass)newEntity;
                                        
                                        OWLAxiom ax = df.getOWLAnnotationAssertionAxiom(cls.getIRI(),commentAnno);
                                        
                                        oomiOwlModel.getManager().applyChange(new AddAxiom(ontology,ax));
                                        oomiOwlModel.getManager().saveOntology(ontology);
                                        oomiOwlModel.getIDEPanelHandler().updateTextArea();
                                        
                                        
				}
			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
                redrawUI();
	}
	
	public void changedUpdate(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		
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

    @Override
    public void keyTyped(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

 
}
