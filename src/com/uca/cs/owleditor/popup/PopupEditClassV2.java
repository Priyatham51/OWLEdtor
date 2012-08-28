/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor.popup;
import com.uca.cs.owleditor.OOMIOwlModel;
import com.uca.cs.owleditor.utils.ui.SpringUtilities;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SpringLayout;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
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
 * @author panise
 */
public class PopupEditClassV2 extends JFrame implements ActionListener{
    
    
    
    JComboBox nameComboBox = new JComboBox();
    
    
    String annotationText="";
    
    JList intersectionClassList, unionClassList, oneOfClassList;
    JList equivalentClassList;
    JList superClassList;

   
    JList subClassList;
    JList disjointClassList;
    JList complimentClassList;
    JList domainList, rangeList;
    
    /*
     * Buttons
     */
    JButton annotationEditBtn,intersectionEditBtn, unionEditBtn,oneOfEditBtn,equivalentEditBtn,
            disjointEditBtn,complimentEditBtn,superClassOfEditBtn,subClassOfEditBtn,domainEditBtn,rangeEditBtn;
    
    OWLClass selectedClass;

    
    
    OWLOntology ontology;
    OWLEntity newEntity;
    OWLDataFactory factory;
    JPanel IDEHandler;
    JPanel selectionPanel, editorPanel;
    
    
    OOMIOwlModel oomiOwlModel;
    OWLEntity lastSelectedParent;
    IRI documentIRI = null;

    public PopupEditClassV2(JPanel IDEHandler, OOMIOwlModel oomiOwlModel,OWLOntology ontology) {
        this.ontology = ontology;
        this.IDEHandler = IDEHandler;
        this.oomiOwlModel = oomiOwlModel;
        
        factory = ontology.getOWLOntologyManager().getOWLDataFactory();  
        selectedClass = factory.getOWLThing();
        
        
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
        
        
        nameComboBox.addActionListener(this);
        mainPanel.add(new JLabel("Select Class"));
        fillClassNames();
       // nameComboBox.setSelectedIndex(1);
        mainPanel.add(nameComboBox);
        mainPanel.add(new JLabel(""));
        
        

        JTextArea annotationsTextArea  = new JTextArea(annotationText);
       // fillAnnotationsText();
        mainPanel.add(new JLabel("Annotations"));
        mainPanel.add(annotationsTextArea);
        annotationEditBtn = new JButton("Modify");
        annotationEditBtn.addActionListener(this);
        mainPanel.add(annotationEditBtn);
        
        
        //Sub-class
        mainPanel.add(new JLabel("SuperClasses"));
        superClassList = new JList(fillSubClassOfList());
        JScrollPane  subScrollPane = new JScrollPane(superClassList);
        subScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(subScrollPane);
        superClassOfEditBtn = new JButton("Modify");
        superClassOfEditBtn.addActionListener(this);
        mainPanel.add(superClassOfEditBtn);
        
        //Super-Class
        mainPanel.add(new JLabel("SubClasses"));
        subClassList = new JList(fillSuperClassOfList());
        JScrollPane   superScrollPane = new JScrollPane(subClassList);
        superScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(superScrollPane);
        subClassOfEditBtn = new JButton("Modify");
        subClassOfEditBtn.addActionListener(this);
        mainPanel.add(subClassOfEditBtn);
        
        /*
         * Intersection-Of / Union-Of / One-of
         * 
         */
        
        //Intersection-Of
        mainPanel.add(new JLabel("Intersection-of"));
        intersectionClassList = new JList();
        JScrollPane intersectionScrollPane = new JScrollPane(intersectionClassList);
        intersectionScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(intersectionScrollPane);
        intersectionEditBtn = new JButton("Modify");
        intersectionEditBtn.addActionListener(this);
        mainPanel.add(intersectionEditBtn);
        
        //Union-Of
        mainPanel.add(new JLabel("Union-of"));
        unionClassList = new JList(fillUnionClassList());
        JScrollPane unionScrollPane = new JScrollPane(unionClassList);
        unionScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(unionScrollPane);
        unionEditBtn = new JButton("Modify");
        unionEditBtn.addActionListener(this);
        mainPanel.add(unionEditBtn);
        
        
        //One-Of
        mainPanel.add(new JLabel("One-of"));
        oneOfClassList = new JList();
        JScrollPane oneOfScrollPane = new JScrollPane(oneOfClassList);
        oneOfScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(oneOfScrollPane);
        oneOfEditBtn = new JButton("Modify");
        oneOfEditBtn.addActionListener(this);
        mainPanel.add(oneOfEditBtn);
        /*
         * Equivalent to  / Disjoint-of / Compliment-Of / Sub-Class / Super-Class 
         * 
         */
        
        //Equivalent 
        mainPanel.add(new JLabel("Equivalent to"));
        equivalentClassList = new JList();
        JScrollPane  equivalentScrollPane = new JScrollPane( equivalentClassList);
         equivalentScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(equivalentScrollPane);
        equivalentEditBtn = new JButton("Modify");
        equivalentEditBtn.addActionListener(this);
        mainPanel.add(equivalentEditBtn);
        
        //Disjoint-Of
        mainPanel.add(new JLabel("Disjoint with"));
        disjointClassList = new JList();
        JScrollPane   disjointScrollPane = new JScrollPane(  disjointClassList);
        disjointScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(disjointScrollPane);
        disjointEditBtn = new JButton("Modify");
        disjointEditBtn.addActionListener(this);
        mainPanel.add(disjointEditBtn);
        
       
        //Compliment 
        mainPanel.add(new JLabel("Compilment Of"));
        complimentClassList = new JList();
        JScrollPane  complimentScrollPane = new JScrollPane( complimentClassList);
        complimentScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(complimentScrollPane);
        complimentEditBtn = new JButton("Modify");
        complimentEditBtn.addActionListener(this);
        mainPanel.add(complimentEditBtn);
 
        
        /*
         * Domain / Range
         */
        
        //Domain
        mainPanel.add(new JLabel("Has Domain"));
        domainList = new JList();
        JScrollPane   domainScrollPane = new JScrollPane(  domainList);
        domainScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(domainScrollPane);
        domainEditBtn = new JButton("Modify");
        domainEditBtn.addActionListener(this);
        mainPanel.add(domainEditBtn);
        
        //Range 
        mainPanel.add(new JLabel("Has Range"));
        rangeList = new JList();
        JScrollPane   rangeScrollPane = new JScrollPane(  rangeList);
        rangeScrollPane.setPreferredSize(new Dimension(400,50));
        mainPanel.add(rangeScrollPane);
        rangeEditBtn = new JButton("Modify");
        rangeEditBtn.addActionListener(this);
        mainPanel.add(rangeEditBtn);
        
        
        
         int rows = 12;

		SpringUtilities.makeCompactGrid(mainPanel,
                rows, 3, //rows, cols
                8, 8,        //initX, initY
                8, 8);       //xPad, yPad
       
                JScrollPane mainScrollPane = new JScrollPane(mainPanel);
                
                
                Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(mainScrollPane, "Center");
		setSize(890,390);
                setResizable(true);
                setTitle("Ontology Editor");
                //headerBox.setVisible(true);
    }
    
    
    
    
    public OWLClass getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(OWLClass selectedClass) {
        this.selectedClass = selectedClass;
    }
    
     public JList getSubClassList() {
        return superClassList;
    }

    public void setSubClassList(JList subClassList) {
        this.superClassList = subClassList;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nameComboBox){
            this.setSelectedClass((OWLClass) nameComboBox.getSelectedItem());
            String nothing[]= new String[1];
            if(nameComboBox.getSelectedIndex()==0){
               
                superClassList.setListData(nothing);
                subClassList.setListData(nothing);
                intersectionClassList.setListData(nothing);
                unionClassList.setListData(nothing); 
                oneOfClassList.setListData(nothing);
                equivalentClassList.setListData(nothing);            
                disjointClassList.setListData(nothing);
                complimentClassList.setListData(nothing);
                domainList.setListData(nothing);
                rangeList.setListData(nothing);
               
            }
            else{
            
            
            superClassList.setListData(fillSubClassOfList());
            subClassList.setListData(fillSuperClassOfList());
            intersectionClassList.setListData(fillIntersectionList());
            fillUnionClassList();
            fillEquivalentList();
            domainList.setListData(fillDomainList());
            //rangeList.setListData(nothing);
            }
        
        }
        if(e.getSource()== annotationEditBtn){
             PopupModify modifyBtn = new PopupModify("annotation",oomiOwlModel,this);
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           modifyBtn.setLocation(620,300);
           modifyBtn.setVisible(true);
        }
        
       if(e.getSource()==superClassOfEditBtn){
           
           PopupModify modifyBtn = new PopupModify("superClass",oomiOwlModel,this);
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           modifyBtn.setLocation(620,300);
           modifyBtn.setVisible(true);
           
        }
       if(e.getSource()==subClassOfEditBtn){
           
           PopupModify modifyBtn = new PopupModify("subClass",oomiOwlModel,this);
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           modifyBtn.setLocation(620,300);
           modifyBtn.setVisible(true);
           
        }
         
            
    }

    private void fillClassNames() {
        nameComboBox.removeActionListener(this);
        nameComboBox.removeAllItems();
        
        Set propSet = new TreeSet();
        propSet.addAll(ontology.getClassesInSignature());
        Iterator propSetIter = propSet.iterator();
        while (propSetIter.hasNext()) {
                OWLClass cls = (OWLClass) propSetIter.next();
                nameComboBox.addItem(cls);
        }
        
        nameComboBox.addActionListener(this);
        
    }

    private void fillAnnotationsText() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
  
    private String[] fillIntersectionList(){
        
        String classNames[] = new String[ontology.getClassesInSignature().size()];
        
        
        Set disjointNames = new TreeSet();
        
        disjointNames.addAll(selectedClass.getNestedClassExpressions());
        Iterator disjointNamesIter = disjointNames.iterator();
                 
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClass cls = (OWLClass) disjointNamesIter.next();
             classNames[i] = cls.toStringID();
             
         }
        
         
        return classNames;
    }
    
    private String[] fillUnionClassList(){
        
        String classNames[] = new String[ontology.getClassesInSignature().size()];
        
        
        Set disjointNames = new TreeSet();
        
        disjointNames.addAll(ontology.getClassesInSignature());
        Iterator disjointNamesIter = disjointNames.iterator();
                 
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClass cls = (OWLClass) disjointNamesIter.next();
             classNames[i] = cls.toStringID();
             
         }
        
         
        return classNames;
    }
    
    
    private String[] fillEquivalentList(){
        
        
        String classNames[] = new String[ontology.getClassesInSignature().size()];
        
         Set equivalentNames = new TreeSet();
        
        //disjointNames.addAll(ontology.getClassesInSignature());
        equivalentNames.addAll(selectedClass.getEquivalentClasses(ontology));
         
        Iterator disjointNamesIter = equivalentNames.iterator();
        
        
                 
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClassExpression cls = (OWLClassExpression) disjointNamesIter.next();
             classNames[i] = cls.toString();
             
         }
         
          return classNames;
    }
    
    
    public OWLClass[] fillSubClassOfList(){
     
        
        Set subClassNames = new TreeSet();
        subClassNames.addAll(selectedClass.getSuperClasses(ontology));
         
        OWLClass classNames[] = new OWLClass[subClassNames.size()];
        Iterator subClassNamesIter = subClassNames.iterator();
              
        for(int i=0;subClassNamesIter.hasNext();i++){
            classNames[i]=(OWLClass)subClassNamesIter.next();
            
        }
         
          return classNames;
    }
    
    public String[] fillSuperClassOfList(){
        
        
        Set superClassNames = new TreeSet();
        superClassNames.addAll(selectedClass.getSubClasses(ontology));
         
        String classNames[] = new String[superClassNames.size()];
        Iterator superClassNamesIter = superClassNames.iterator();
              
        for(int i=0;superClassNamesIter.hasNext();i++){
            classNames[i]= superClassNamesIter.next().toString();
            
        }
          return classNames;
    }
    
    private String[] filloneOfClassList(){
        
        String classNames[] = new String[ontology.getClassesInSignature().size()];
        
         Set oneOfClassNames = new TreeSet();
        
        //disjointNames.addAll(ontology.getClassesInSignature());
        oneOfClassNames.addAll(selectedClass.getEquivalentClasses(ontology));
         
        Iterator disjointNamesIter = oneOfClassNames.iterator();
                  
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClass cls = (OWLClass) disjointNamesIter.next();
             classNames[i] = cls.getComplementNNF().toString();
             
         }
         
          return classNames;
    }
    
    private String[] fillDisjointClassList(){
         String classNames[] = new String[ontology.getClassesInSignature().size()];
        
         Set disjontClassNames = new TreeSet();
        
        
        disjontClassNames.addAll(selectedClass.getDisjointClasses(ontology));
         
        Iterator disjointNamesIter = disjontClassNames.iterator();
                  
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClass cls = (OWLClass) disjointNamesIter.next();
             classNames[i] = cls.getComplementNNF().toString();
             
         }
         
          return classNames;
        
    }
    
    private String[] fillComplimentClassList(){
        
        String classNames[] = new String[ontology.getClassesInSignature().size()];
        
         Set complimentClassNames = new TreeSet();
        
        
        complimentClassNames.addAll(selectedClass.getEquivalentClasses(ontology));
         
        Iterator disjointNamesIter = complimentClassNames.iterator();
                  
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClass cls = (OWLClass) disjointNamesIter.next();
             classNames[i] = cls.getComplementNNF().toString();
             
         }
         
          return classNames;
    }
    
    private String[] fillDomainList(){
         
        int count=0;
        
        
//        Set referencingAxiom = new TreeSet();
//        
//        referencingAxiom.addAll(selectedClass.getReferencingAxioms(ontology));      
       
        Set domainClassNames = new TreeSet();
        domainClassNames.addAll(selectedClass.getReferencingAxioms(ontology));
        Iterator disjointNamesIter = domainClassNames.iterator();
       
        while(disjointNamesIter.hasNext()){
           OWLAxiom testAxiom = (OWLAxiom) disjointNamesIter.next();
            
            if(testAxiom instanceof OWLDataPropertyDomainAxiom){
                
//                classNames[count] = testAxiom.toString();
                count++;
            }
        }
        String classNames[] = new String[count];
         count=0;
        
        disjointNamesIter = domainClassNames.iterator();
                  
       while(disjointNamesIter.hasNext()){
           
            OWLAxiom testAxiom2 = (OWLAxiom) disjointNamesIter.next();
            if(testAxiom2 instanceof OWLDataPropertyDomainAxiom){
                
                classNames[count] = testAxiom2.toString();
                count++;
            }
              
             
         }
         
          return classNames;
    }
    
    
    private String[] fillRangeList(){
         
        String classNames[] = new String[ontology.getClassesInSignature().size()];
        
         Set domainClassNames = new TreeSet();
        
        
        domainClassNames.addAll(selectedClass.getEquivalentClasses(ontology));
         
        Iterator disjointNamesIter = domainClassNames.iterator();
                  
         for(int i=0;disjointNamesIter.hasNext();i++){
            OWLClass cls = (OWLClass) disjointNamesIter.next();
             classNames[i] = cls.getComplementNNF().toString();
             
         }
         
          return classNames;
    }
    
    
}


