/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.uca.cs.owleditor;



import org.semanticweb.owlapi.reasoner.OWLReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.OWL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLDataFactory;



import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;

/**
 *
 * @author priyathamanisetty
 */

public class TreeRender {

    
    
    OWLOntology ontology;
    OWLDataFactory factory;
    OOMIOwlModel owlModel;
    
    OWLReasoner reasoner ;
    
    
    public TreeRender(OOMIOwlModel owlModel) {
    
        this.owlModel = owlModel;
        factory = owlModel.dataFactory;
        this.ontology = owlModel.getWorkingOntology();
        
        
        
         
      
    }
    
    
    
    
    
    public JTree getJTree(){
        JTree tree = new JTree();
        
        Set clsSet = ontology.getClassesInSignature();
        
        Iterator clsIterator = clsSet.iterator();
        
        while(clsIterator.hasNext()){
            
            reasoner =  PelletReasonerFactory.getInstance().createReasoner(ontology);
            NodeSet<OWLClass> clsNode = reasoner.getSubClasses((OWLClass)clsIterator.next(), true);
            
            
            
            
            
        }
              
      
         
        
        
        
        
        return tree;
    }
    
    
   
    
    
    
    
    

}
