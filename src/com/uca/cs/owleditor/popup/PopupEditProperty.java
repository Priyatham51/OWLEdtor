/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;

import com.uca.cs.owleditor.OOMIOwlModel;
import com.uca.cs.owleditor.utils.ui.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import uk.ac.manchester.cs.owl.owlapi.OWLDatatypeImpl;

/**
 *
 * @author priyathamanisetty
 */
public class PopupEditProperty extends JFrame implements ActionListener{
    
    
     
    OOMIOwlModel oomiOwlModel;
    OWLEntity selectedProperty;
    
    
    JComboBox  typeBox = new JComboBox();
    JComboBox  nameBox = new JComboBox();
    JComboBox  domainBox = new JComboBox();
    JComboBox rangeBox= new JComboBox();
    
    JComboBox superPropBox = new JComboBox();
    JComboBox subPropBox = new JComboBox();
    
    JButton saveBtn,cancelBtn;
    JButton annotationEditBtn,intersectionEditBtn, unionEditBtn,oneOfEditBtn,equivalentEditBtn,
            disjointEditBtn,complimentEditBtn,superPropertyOfEditBtn,subProprOfEditBtn,domainEditBtn,rangeEditBtn;
    
    JList domainList, rangeList, superPropertyList, subPropertyList;
    String type;
    
    

    public PopupEditProperty(OOMIOwlModel owlModel) {
        this.oomiOwlModel = owlModel;
        
        
       try {
			setupUI();			
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
         
    }
    
    
    private void setupUI(){
        
        
        JPanel mainPanel = new JPanel(new SpringLayout());
        
        typeBox = new JComboBox();
        typeBox.addItem("ObjectProperty");
        typeBox.addItem("Datatype Property");
        typeBox.addActionListener(this);
        
        typeBox.setSelectedIndex(0);
        
        
        mainPanel.add(new JLabel("Type"));
        mainPanel.add(typeBox);
        typeBox.addActionListener(this);
        mainPanel.add(new JLabel(""));
        
        mainPanel.add(new JLabel("Name"));
        mainPanel.add(nameBox);
        nameBox.addActionListener(this);
        mainPanel.add(new JLabel(""));
        
         /*
         * Domain / Range
         */
        
        //Domain
        mainPanel.add(new JLabel("Has Domain"));
        mainPanel.add(domainBox);
        domainBox.addActionListener(this);
        mainPanel.add(new JLabel(""));
        
        //Range 
        mainPanel.add(new JLabel("Has Range"));
        mainPanel.add(rangeBox);
        rangeBox.addActionListener(this);
        mainPanel.add(new JLabel(""));
       
        mainPanel.add(new JLabel("have SubProperty"));
        subPropertyList = new JList();
        JScrollPane  subScrollPane = new JScrollPane(subPropertyList);
        subScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(subScrollPane);
        subProprOfEditBtn = new JButton("Modify");
        subProprOfEditBtn.addActionListener(this);
        mainPanel.add(subProprOfEditBtn);
        
        //Super-Class
        mainPanel.add(new JLabel("have SuperProperty"));
        superPropertyList = new JList();
        JScrollPane   superScrollPane = new JScrollPane(superPropertyList);
        superScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(superScrollPane);
        superPropertyOfEditBtn = new JButton("Modify");
        superPropertyOfEditBtn.addActionListener(this);
        mainPanel.add(superPropertyOfEditBtn);
        
        
        
         JPanel btnPanel = new JPanel();
	       btnPanel.setLayout(new GridBagLayout());
               GridBagConstraints gbc = new GridBagConstraints();
       
               saveBtn = new JButton("Save");
               saveBtn.addActionListener(this);
               gbc.gridx =0;
               gbc.gridy =0;
               gbc.ipadx =200;
               btnPanel.add(saveBtn,gbc);

               cancelBtn = new JButton("Cancel");
               cancelBtn.addActionListener(this);
               gbc.insets= new Insets(0,15,0,0);
               gbc.gridx = 1;
               gbc.gridy = 0;
               btnPanel.add(cancelBtn,gbc);
		
              	mainPanel.add(new JLabel(""));
		mainPanel.add(btnPanel);
                mainPanel.add(new JLabel(""));
                
        
                
                int rows = 7;

		SpringUtilities.makeCompactGrid(mainPanel,
                rows, 3, //rows, cols
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
        
        if(e.getSource()==typeBox){
            
            nameBox.removeActionListener(this);
            nameBox.removeAllItems();
            domainBox.removeActionListener(this);
            domainBox.removeAllItems();
            rangeBox.removeActionListener(this);
            rangeBox.removeAllItems();
            if(typeBox.getSelectedIndex()==0){//OBject Property
                
                //Type Selected is Object Property
                
                type="Object";
                
                
                
                
            }
            else
                if(typeBox.getSelectedIndex()==1){
                    //Type selected is DataProperty
                    
                    type= "Data";
                    
                }                
       
           fillNameBox();
        
        }
        
        
        if(e.getSource()==nameBox){
            
            
            if(nameBox.getSelectedIndex()==0){
                String nothing[]= new String[1];
                superPropertyList.setListData(nothing);
                subPropertyList.setListData(nothing);
//                domainList.setListData(nothing);
//                rangeList.setListData(nothing);
                
                
            }
            else{
                
               if(nameBox.getSelectedItem() instanceof OWLObjectProperty){
                                   
                selectedProperty = (OWLObjectProperty)nameBox.getSelectedItem();
                fillDomainBox("Object");
                fillRangeBox("Object");
                superPropertyList.setListData(fillSuperPropertyList());
                subPropertyList.setListData(fillSubPropertyList());
                }
                else
                    if(nameBox.getSelectedItem()instanceof OWLDataProperty){
                        
                        selectedProperty = (OWLDataProperty) nameBox.getSelectedItem();
                        fillDomainBox("Data");
                        fillRangeBox("Data");
                        superPropertyList.setListData(fillSuperPropertyList());
                        subPropertyList.setListData(fillSubPropertyList());
                    }              
               }         
        }
        
    }

    private void fillNameBox() {
        
        String rangeType="";
        String domainType="";
        nameBox.removeActionListener(this);
        nameBox.removeAllItems();
        nameBox.addItem("");
        
        if(type.equalsIgnoreCase("Object")){
           
            Set objSet = new TreeSet();
            objSet.addAll(oomiOwlModel.getWorkingOntology().getObjectPropertiesInSignature());
            
            
            Iterator objIterator = objSet.iterator();
            
            while(objIterator.hasNext()){
                OWLObjectProperty objProp =(OWLObjectProperty) objIterator.next();
                
                nameBox.addItem(objProp);
            }
            
            rangeType = "Object";
            domainType ="Object";
            
        }
        
        if(type.equalsIgnoreCase("Data")){
            
            
            Set dataSet = new TreeSet();
            dataSet.addAll(oomiOwlModel.getWorkingOntology().getDataPropertiesInSignature());
            
            
            Iterator dataIterator = dataSet.iterator();
            
            while(dataIterator.hasNext()){
                OWLDataProperty dataProp =(OWLDataProperty) dataIterator.next();
                
              nameBox.addItem(dataProp);
              
            }     
            
            rangeType = "Data";
            domainType ="Data";
        }
        
        nameBox.addActionListener(this);
        
//        fillDomainBox(domainType);
//        fillRangeBox(rangeType);
//        
    }
    
    
    private void fillDomainBox(String domainType){
        
        domainBox.removeActionListener(this);
        domainBox.removeAllItems();
        domainBox.addItem("Null");
        
        
        Set clsSet = new TreeSet();
        
        clsSet.addAll(oomiOwlModel.getWorkingOntology().getClassesInSignature());
        
        Iterator clsIterator = clsSet.iterator();
        
        while(clsIterator.hasNext()){
            OWLClass cls = (OWLClass) clsIterator.next();
            domainBox.addItem(cls);
        }
        
        
        if(domainType.equals("Data")){
            OWLDataProperty selectedDataProp = (OWLDataProperty)nameBox.getSelectedItem();
            Set domainClass = new TreeSet();
            domainClass.addAll(selectedDataProp.getDomains(oomiOwlModel.getWorkingOntology()));
            domainBox.setSelectedItem((OWLClass)domainClass.iterator().next());  
        }
        else
            if(domainType.equals("Object")){
                OWLObjectProperty seletedObjectProperty = (OWLObjectProperty)nameBox.getSelectedItem();
               
                Set domainClass = new TreeSet();
                domainClass.addAll(seletedObjectProperty.getDomains(oomiOwlModel.getWorkingOntology()));
                domainBox.setSelectedItem(domainClass.iterator().next());
            }
        
       
        
        
        
    }
    
    
    private void fillRangeBox(String rangeType){
        
        
        rangeBox.removeActionListener(this);
        rangeBox.removeAllItems();
        rangeBox.addItem("None");
        
        
        if(rangeType.equalsIgnoreCase("Data")){
            
            OWL2Datatype[] rangeSet = OWL2Datatype.values();
            
            OWL2Datatype type;
            
           
            for(int i=0; i<rangeSet.length; i++){

                rangeBox.addItem((OWL2Datatype)rangeSet[i]);
            }
            
            
            OWLDataProperty selectedProp = (OWLDataProperty)selectedProperty;
            
            Set selectedRangeSet = new TreeSet();
            
            selectedRangeSet.addAll(selectedProp.getRanges(oomiOwlModel.getWorkingOntology()));
            
            OWLDatatypeImpl selectedType = (OWLDatatypeImpl)selectedRangeSet.iterator().next();
            
            OWL2Datatype sel = selectedType.getBuiltInDatatype();

            rangeBox.setSelectedItem(sel);  
        }
        else
            if(rangeType.equalsIgnoreCase("Object")){
                
                 Set clsSet = new TreeSet();
        
                    clsSet.addAll(oomiOwlModel.getWorkingOntology().getClassesInSignature());

                    Iterator clsIterator = clsSet.iterator();

                    while(clsIterator.hasNext()){
                        OWLClass cls = (OWLClass) clsIterator.next();
                        rangeBox.addItem(cls);
                    }

                    
                    OWLObjectProperty selectedProp = (OWLObjectProperty)selectedProperty;
                    Set selectedRange = new TreeSet();
                    selectedRange.addAll(selectedProp.getRanges(oomiOwlModel.getWorkingOntology()));
                    
                    
                    rangeBox.setSelectedItem(selectedRange.iterator().next());      
            }
        
       
    }
    
    
     public String[] fillSubPropertyList(){
          
         
         
         String propNames[]= null;
         
          
         
     if(selectedProperty instanceof OWLObjectProperty){
         
         
             OWLObjectProperty objProperty = (OWLObjectProperty) selectedProperty;

             Set subPropertyNames = new TreeSet();
             subPropertyNames.addAll(objProperty.getSubProperties(oomiOwlModel.getWorkingOntology()));

             Iterator subPropertyIterator = subPropertyNames.iterator();

             propNames = new String[subPropertyNames.size()];

            for(int i=0; subPropertyIterator.hasNext();i++){
                propNames[i] =  subPropertyIterator.next().toString();
            }    
     }
     
     else
         if(selectedProperty instanceof OWLDataProperty){
             
                 OWLDataProperty dataProperty = (OWLDataProperty) selectedProperty;

                 Set subPropertyNames = new TreeSet();
                 subPropertyNames.addAll(dataProperty.getSubProperties(oomiOwlModel.getWorkingOntology()));

                 Iterator subPropertyIterator = subPropertyNames.iterator();

                 propNames = new String[subPropertyNames.size()];

                for(int i=0; subPropertyIterator.hasNext();i++){
                    propNames[i] =  subPropertyIterator.next().toString();
                }     
                
             }
        
     
     
       return propNames;
     
         
         
    }
    
    public String[] fillSuperPropertyList(){
        
        
         
         String propNames[]= null;
         
          
         
     if(selectedProperty instanceof OWLObjectProperty){
         
         
             OWLObjectProperty objProperty = (OWLObjectProperty) selectedProperty;

             Set subPropertyNames = new TreeSet();
             subPropertyNames.addAll(objProperty.getSuperProperties(oomiOwlModel.getWorkingOntology()));

             Iterator subPropertyIterator = subPropertyNames.iterator();

             propNames = new String[subPropertyNames.size()];

            for(int i=0; subPropertyIterator.hasNext();i++){
                propNames[i] =  subPropertyIterator.next().toString();
            }    
     }
     
     else
         if(selectedProperty instanceof OWLDataProperty){
             
                 OWLDataProperty dataProperty = (OWLDataProperty) selectedProperty;

                 Set subPropertyNames = new TreeSet();
                 subPropertyNames.addAll(dataProperty.getSuperProperties(oomiOwlModel.getWorkingOntology()));

                 Iterator subPropertyIterator = subPropertyNames.iterator();

                 propNames = new String[subPropertyNames.size()];

                for(int i=0; subPropertyIterator.hasNext();i++){
                    propNames[i] =  subPropertyIterator.next().toString();
                }     
                
             }
        
     
     
       return propNames;
      
    }
        
    
    
    
    
    
    
    
    
    
}
