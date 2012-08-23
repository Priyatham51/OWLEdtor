/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.semanticweb.owl.model.change.OntologyChange;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeVisitor;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.model.OWLSubDataPropertyOfAxiom;
import org.semanticweb.owlapi.model.OWLSubObjectPropertyOfAxiom;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.util.OWLEntityRemover;
import org.semanticweb.owlapi.util.OWLOntologyChangeVisitorAdapter;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 *
 * @author priyathamanisetty
 * 
 * This is the central Access point for the OOMI OWL Editor. The OWLOntologyManger member "manager" 
 * is responsible for the current ontology on which the user is Working. 
 * 
 * One should use the OWLDataFactory in this class to make changes to ontology. 
 */
public class OOMIOwlModel {
    
    
    OWLOntology workingOntology;
    OWLOntologyManager manager;
    OWLDataFactory  dataFactory;
    IRI documentIRI;
    IDEPanel IDEPanelHandler;

    
    
    
   public  OOMIOwlModel(){
       manager = OWLManager.createOWLOntologyManager();
       dataFactory = manager.getOWLDataFactory();
       
      
    }

    public OWLOntologyManager getManager() {
        return manager;
    }

    public void setManager(OWLOntologyManager manager) {
        this.manager = manager;
    }
   
   
    public IDEPanel getIDEPanelHandler() {
        return IDEPanelHandler;
    }

    public void setIDEPanelHandler(IDEPanel IDEPanelHandler) {
        this.IDEPanelHandler = IDEPanelHandler;
    }
   
    public OWLOntology getWorkingOntology() {
        return workingOntology;
    }

    public void setWorkingOntology(OWLOntology workingOntology) {
        this.workingOntology = workingOntology;
    }
    
    public void setWorkingOntology(File workingOntologyFile) {
        try {
           this.workingOntology= manager.loadOntologyFromOntologyDocument(workingOntologyFile);
          IRI iri= workingOntology.getOntologyID().getOntologyIRI();
        } catch (OWLOntologyCreationException ex) {
            Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void addEntity(OWLEntity newEntity, OWLEntity parent){
    
        
        if(parent != null){
            
            if( parent instanceof OWLClass){
                
                if(newEntity instanceof OWLClass){
                     OWLClass parentClass = (OWLClass)parent;
                     OWLClass newClass = (OWLClass)newEntity;
                     
                     OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom(newClass, parentClass);
                     // add the axiom to the ontology.
                    AddAxiom addAxiom = new AddAxiom(this.workingOntology, axiom);
                    // We now use the manager to apply the change
                    manager.applyChange(addAxiom);
                    try {
                            manager.saveOntology(workingOntology);
                        } catch (OWLOntologyStorageException ex) {
                            Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        IDEPanelHandler.updateTextArea();
                }
                
            }
            else 
               if(parent instanceof OWLDataProperty){
                   
                   OWLDataProperty dataProp = (OWLDataProperty)newEntity;
                   OWLDataProperty parentProp = (OWLDataProperty) parent;
                   
                   //OWLAxiom axiom = dataFactory.getowl
               }
            else
               if(parent instanceof OWLObjectProperty){
                   OWLObjectProperty dataProp = (OWLObjectProperty)newEntity;
                   OWLObjectProperty parentProp = (OWLObjectProperty) parent;
                   
                   //OWLAxiom axiom = dataFactory.getOWLObjectPropertyDomainAxiom(parentProp, null, axiom);
               }
        }
        else
        { 
            if(newEntity instanceof OWLClass){
                

                parent = dataFactory.getOWLThing();
                if( parent instanceof OWLClass){

                    if(newEntity instanceof OWLClass){
                         OWLClass parentClass = (OWLClass)parent;
                         OWLClass newClass = (OWLClass)newEntity;

                         OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom(newClass, parentClass);
                         // add the axiom to the ontology.
                        AddAxiom addAxiom = new AddAxiom(this.workingOntology, axiom);
                        // We now use the manager to apply the change
                        Set se = workingOntology.getClassesInSignature();
                        Iterator claSetIter = se.iterator();
                                    while (claSetIter.hasNext()) {
                                            OWLClass cla = (OWLClass) claSetIter.next();
                                            if (!cla.isOWLThing())
                                                System.out.println(cla);
                                    }
                        manager.applyChange(addAxiom);

                        Set sr = workingOntology.getClassesInSignature();
                        claSetIter = sr.iterator();
                                    while (claSetIter.hasNext()) {
                                            OWLClass cla = (OWLClass) claSetIter.next();
                                            if (!cla.isOWLThing())
                                                System.out.println(cla);
                                    }
                        try {
                            manager.saveOntology(workingOntology);
                        } catch (OWLOntologyStorageException ex) {
                            Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        IDEPanelHandler.updateTextArea();
                    }
                }
            }
            
            else 
               if(newEntity instanceof OWLDataProperty){
                   OWLDataProperty newProp = (OWLDataProperty)newEntity;
                   OWLIndividual indi = (OWLIndividual) parent;
                   
                   OWLClass classs = dataFactory.getOWLClass(IRI.create(documentIRI+"#SampleClass"));
                   
                   //OWLObjectPropertyDomainAxiom domainAxiom = dataFactory.getOWLObjectPropertyDomainAxiom(newProp,classs);
                   //dataFactory.getOWLObjectPropertyDomainAxiom(new, classs);
                  // OWLObjectPropertyRangeAxiom   rangeAxiom
                   
                   AddAxiom addAxiom =(AddAxiom)dataFactory.getOWLDataPropertyAssertionAxiom(newProp,indi,true);
                   
                   
               }
            else 
               if(newEntity instanceof OWLObjectProperty){
                   OWLObjectProperty newProp = (OWLObjectProperty)newEntity;
                   OWLIndividual indi = (OWLIndividual) parent;
                   
                   
                   OWLClass classs = dataFactory.getOWLClass(IRI.create(documentIRI+"#SampleClass"));
                   //AddAxiom addAxiom = dataFactory.getOWLObjectPropertyDomainAxiom(newProp, classs);
                   OWLObjectPropertyDomainAxiom domainAxiom = dataFactory.getOWLObjectPropertyDomainAxiom(newProp,classs);
                   manager.addAxiom(workingOntology, domainAxiom);
                   
                  try {
                            manager.saveOntology(workingOntology);
                        } catch (OWLOntologyStorageException ex) {
                            Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        IDEPanelHandler.updateTextArea();
                   
               }
            
        }
        
    }
    
    
   public void addObjectPropertyEntity(OWLEntity newEntity, OWLEntity parent, 
                                       OWLEntity child,OWLEntity domain, OWLEntity range, boolean delete){
    

       
               
               OWLObjectProperty parentProp = (OWLObjectProperty) parent;
               OWLObjectProperty childProp = (OWLObjectProperty) child;
               OWLClass hasDomain = (OWLClass) domain;
               OWLClass hasRange  = (OWLClass) range;
               
               if(delete){
                   
                   Set ontoSet = new TreeSet();
                   ontoSet.add(workingOntology);
                   
                   OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
                   er.visit((OWLObjectProperty)newEntity);
                   List <OWLOntologyChange> changes = er.getChanges();
                   manager.applyChanges(changes);
               }
               
               
               
               
        if(newEntity instanceof OWLObjectProperty){
            
               if(parentProp==null && childProp == null && hasDomain == null && hasRange == null){
               
               
                   OWLDeclarationAxiom axiom2 = dataFactory.getOWLDeclarationAxiom(newEntity);
                   manager.addAxiom(workingOntology, axiom2);
                   
               }
               else{
                   
                   if(parentProp!= null){
                       
                     OWLSubObjectPropertyOfAxiom subPropAxiom =  dataFactory.getOWLSubObjectPropertyOfAxiom((OWLObjectProperty)newEntity,parentProp);
                     manager.addAxiom(workingOntology, subPropAxiom);
                   }
                   
                   if(childProp != null){
                       OWLSubObjectPropertyOfAxiom subPropAxiom =  dataFactory.getOWLSubObjectPropertyOfAxiom(parentProp,(OWLObjectProperty)newEntity);
                       manager.addAxiom(workingOntology, subPropAxiom);        
                   }
                   
                   if(hasDomain !=null){
                       
                       OWLObjectPropertyDomainAxiom  domainAxiom =  dataFactory.getOWLObjectPropertyDomainAxiom((OWLObjectProperty)newEntity,hasDomain); 
                       manager.addAxiom(workingOntology, domainAxiom);
                   
                   }
                   if(hasRange !=null){
                       
                       OWLObjectPropertyRangeAxiom rangeAxiom = dataFactory.getOWLObjectPropertyRangeAxiom((OWLObjectProperty)newEntity, hasRange); 
                      
                       manager.addAxiom(workingOntology, rangeAxiom);
                   }
                
                }               
               
               try {
                    manager.saveOntology(workingOntology);
                } catch (OWLOntologyStorageException ex) {
                    Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                IDEPanelHandler.updateTextArea();
                   
           }
            
      
        
    }
  
   public void addDatatypePropertyEntity(OWLEntity newEntity, OWLEntity parent, 
                                       OWLEntity child,OWLEntity domain, OWL2Datatype range){
    

       
       
               OWLDataProperty parentProp = (OWLDataProperty) parent;
               OWLDataProperty childProp = (OWLDataProperty) child;
               OWLClass hasDomain = (OWLClass) domain;
               OWLDataRange hasRange  = dataFactory.getOWLDatatype(range.getIRI());
               
        if(newEntity instanceof OWLDataProperty){
            
               if(parentProp==null && childProp == null && hasDomain == null && range == null){
               
               
                   OWLDeclarationAxiom axiom2 = dataFactory.getOWLDeclarationAxiom(newEntity);
                   manager.addAxiom(workingOntology, axiom2);
                   
                   
                   
               }
               else{
                   
                   if(parentProp!= null){
                       
                     OWLSubDataPropertyOfAxiom subPropAxiom = dataFactory.getOWLSubDataPropertyOfAxiom((OWLDataProperty)newEntity, parentProp);
                     manager.addAxiom(workingOntology, subPropAxiom);
                   }
                   
                   if(childProp != null){
                       OWLSubDataPropertyOfAxiom subPropAxiom =  dataFactory.getOWLSubDataPropertyOfAxiom(childProp,(OWLDataProperty)newEntity);
                       manager.addAxiom(workingOntology, subPropAxiom);        
                   }
                   
                   if(hasDomain !=null){
                       
                       OWLDataPropertyDomainAxiom  domainAxiom =  dataFactory.getOWLDataPropertyDomainAxiom((OWLDataProperty)newEntity,hasDomain); 
                       manager.addAxiom(workingOntology, domainAxiom);
                   
                   }
                   if(hasRange !=null){
                       
                       OWLDataPropertyRangeAxiom rangeAxiom = dataFactory.getOWLDataPropertyRangeAxiom((OWLDataProperty)newEntity,hasRange); 
                       manager.addAxiom(workingOntology, rangeAxiom);
                   }
                
                }               
               
               try {
                    manager.saveOntology(workingOntology);
                } catch (OWLOntologyStorageException ex) {
                    Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                IDEPanelHandler.updateTextArea();
                   
           }
            
      
        
    }
   
   
   public void addClass(OWLEntity newEntity,OWLEntity parentClassName, OWLEntity childClassName){
       
       
       
       
       
       OWLClass parentClass = (OWLClass) parentClassName;
       OWLClass childClass = (OWLClass) childClassName;
       
//
//       Set ontoSet = new TreeSet();
//       ontoSet.add(workingOntology);
//       
//       //Iterator ontoIter = ontoSet.iterator();
//       
//       
//       OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
//       er.visit((OWLClass)newEntity);
//       List <OWLOntologyChange> changes = er.getChanges();
//       manager.applyChanges(changes);
       
       
       
       
       if(parentClass != null){
           
            OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom((OWLClass)newEntity, parentClass);
            manager.addAxiom(workingOntology, axiom);
            
           
       }
       if(childClass != null){
           
           OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom(childClass, (OWLClass)newEntity);
           manager.addAxiom(workingOntology, axiom);
          
        }
       
       try {
            manager.saveOntology(workingOntology);
           } 
       catch (OWLOntologyStorageException ex) {
                    Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                }

       IDEPanelHandler.updateTextArea();
       
   }
    
    public void addClass(OWLEntity newEntity,List classNames, String type){
       
       
      
       Set classNamesSet = new TreeSet();
       
        Iterator classNamesIterator = classNames.listIterator();
        
        while(classNamesIterator.hasNext()){
            
            IRI classIri = IRI.create((String)classNamesIterator.next());
            classNamesSet.add((OWLClass) dataFactory.getOWLClass(classIri));
        }
  
//       Set ontoSet = new TreeSet();
//       ontoSet.add(workingOntology);   
//       OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
//       er.visit((OWLClass)newEntity);
//       List <OWLOntologyChange> changes = er.getChanges();
//       manager.applyChanges(changes);
//       
//       

       
       if(type.equals("superClass")){
           
            
           Set currentSuperClassList = new TreeSet();
           OWLClass selectedClass = (OWLClass)newEntity;
           
           currentSuperClassList.addAll(selectedClass.getSuperClasses(workingOntology));
           
           Iterator currentSuperClassListIterator = currentSuperClassList.iterator();
          
          while(currentSuperClassListIterator.hasNext()){
            
            OWLAxiom axiom2 = dataFactory.getOWLSubClassOfAxiom((OWLClass)newEntity,(OWLClass)currentSuperClassListIterator.next());
            manager.removeAxiom(workingOntology, axiom2);
            try {
                manager.saveOntology(workingOntology);
            } catch (OWLOntologyStorageException ex) {
                Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
            }
          } 
           
            
            Iterator classIterator = classNamesSet.iterator();
            
            while(classIterator.hasNext()){
                 //OWLObjectIntersectionOf intersectionObject = dataFactory.getOWLObjectIntersectionOf((OWLClass)classIterator.next());
                 
                OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom((OWLClass)newEntity,(OWLClass)classIterator.next());
                manager.addAxiom(workingOntology, axiom);
                
            }
           
       }
       
         if(type.equals("subClass")){
           
           Set currentSubClassList = new TreeSet();
           OWLClass selectedClass = (OWLClass)newEntity;
           
           currentSubClassList.addAll(selectedClass.getSubClasses(workingOntology));
           
           Iterator currentSubClassListIterator = currentSubClassList.iterator();
          
          while(currentSubClassListIterator.hasNext()){
            
            OWLAxiom axiom2 = dataFactory.getOWLSubClassOfAxiom((OWLClass)currentSubClassListIterator.next(),(OWLClass)newEntity);
            manager.removeAxiom(workingOntology, axiom2);
            try {
                manager.saveOntology(workingOntology);
            } catch (OWLOntologyStorageException ex) {
                Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
            }
          } 
          
            Iterator classIterator = classNamesSet.iterator();
            
            while(classIterator.hasNext()){
                //OWLObjectIntersectionOf intersectionObject = dataFactory.getOWLObjectIntersectionOf((OWLClass)classIterator.next());
                
               
                 
                 OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom((OWLClass)classIterator.next(),(OWLClass)newEntity);
                manager.addAxiom(workingOntology, axiom);
                
            }
           
       }
       
      // dataFactory.getowlobjecti
       
       if(type.equals("intersectionOf")){
            Iterator classIterator = classNamesSet.iterator();
            
            while(classIterator.hasNext()){
                
                OWLObjectIntersectionOf intersectionObject = dataFactory.getOWLObjectIntersectionOf((OWLClass)classIterator.next());
                
            }
       }
//       if(childClass != null){
//           
//           OWLAxiom axiom = dataFactory.getOWLSubClassOfAxiom(childClass, (OWLClass)newEntity);
//           manager.addAxiom(workingOntology, axiom);
//          
//        }
//       
       try {
            manager.saveOntology(workingOntology);
           } 
       catch (OWLOntologyStorageException ex) {
                    Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                }

       IDEPanelHandler.updateTextArea();
       
   }
    
    public void remove(OWLEntity newEntity){
        if(newEntity instanceof OWLClass){ 
           Set ontoSet = new TreeSet();
           ontoSet.add(workingOntology);   
           OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
           er.visit((OWLClass)newEntity);
           List <OWLOntologyChange> changes = er.getChanges();
           manager.applyChanges(changes);
        }
        else
            if(newEntity instanceof OWLObjectProperty){ 
           Set ontoSet = new TreeSet();
           ontoSet.add(workingOntology);   
           OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
           er.visit((OWLObjectProperty)newEntity);
           List <OWLOntologyChange> changes = er.getChanges();
           manager.applyChanges(changes);
        }
        else
               if(newEntity instanceof OWLDataProperty){ 
           Set ontoSet = new TreeSet();
           ontoSet.add(workingOntology);   
           OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
           er.visit((OWLDataProperty)newEntity);
           List <OWLOntologyChange> changes = er.getChanges();
           manager.applyChanges(changes);
        }  
        
        else
              if(newEntity instanceof OWLNamedIndividual){ 
           Set ontoSet = new TreeSet();
           ontoSet.add(workingOntology);   
           OWLEntityRemover  er = new OWLEntityRemover(manager,ontoSet);
           er.visit((OWLNamedIndividual)newEntity);
           List <OWLOntologyChange> changes = er.getChanges();
           manager.applyChanges(changes);
        } 
         
        
         try {
                    manager.saveOntology(workingOntology);
                } catch (OWLOntologyStorageException ex) {
                    Logger.getLogger(OOMIOwlModel.class.getName()).log(Level.SEVERE, null, ex);
                }

                IDEPanelHandler.updateTextArea();
                   
        
    }
     
    
}
